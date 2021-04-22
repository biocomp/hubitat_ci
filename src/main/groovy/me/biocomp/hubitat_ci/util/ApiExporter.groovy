package me.biocomp.hubitat_ci.util

import groovy.io.FileType
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.validation.Flags

class PreprocessedClasses {
    Map classDefinitions
    Map nestedClasses

    PreprocessedClasses(Map classDefinitions, Map nestedClasses) {
        this.classDefinitions = classDefinitions
        this.nestedClasses = nestedClasses
    }
}

/**
 * Helper methods to export classes from Hubitat controller, to (re-)generate classes from that export,
 * and to compare them with old ones.
 */
class ApiExporter {
    @CompileStatic
    private static def loadAppExporterScript() {
        final def api = [log: new CapturingLog()] as AppExecutor

        final def script = new HubitatAppSandbox(
                new File("Scripts/hubitat_api_exporter_app.groovy")).run(
                validationFlags: Flags.DontRunScript, api: api)

        return script
    }

    private static final def appExporterScript = loadAppExporterScript()

    static private def mergeMaps(Map rhs, Map lhs) {
        return rhs.inject(lhs.clone()) { map, entry ->
            if (map[entry.key] instanceof Map && entry.value instanceof Map) {
                map[entry.key] = mergeMaps(map[entry.key], entry.value)
            } else if (map[entry.key] instanceof Collection && entry.value instanceof Collection) {
                map[entry.key] += entry.value
            } else {
                map[entry.key] = entry.value
            }

            return map
        }
    }

    @CompileStatic
    static private def readClassesFromJson(String generatedSource = "src/test/groovy/hubitat_api.json", String manualOverrides = "src/test/groovy/hubitat_api_overrides.json") {
        JsonSlurper jsonSlurper = new JsonSlurper()
        Map generatedResult = jsonSlurper.parse(new File(generatedSource)) as Map

        Map overrides = [:]

        try {
            overrides = (jsonSlurper.parse(new File(manualOverrides)) as List).collectEntries { [readClassName(it as Map), it] }
        } catch (Exception e) {}

        def result = []
        generatedResult["classes"].each{
            final String className = readClassName(it as Map)
            if (className in overrides) {
                result << mergeMaps(it as Map, overrides[className] as Map)
            } else {
                result << it
            }
        }

        return result
    }

    static private def readMethodsFromClass(def cls, def castType = {it.name}) {
        appExporterScript.readClassMethods(cls, castType)
    }

    @CompileStatic
    static String renameHubitatClass(String hubitatClassName, String basePackageName, boolean replaceDollarSigns = true) {
        String res = SandboxClassLoader.mapClassName(hubitatClassName, basePackageName)
        return replaceDollarSigns ? res.replace('$', '.') : res
    }

    static List<Class> findAllClasses(String folder, String rootPackageName) {
        def dir = new File(folder)
        assert dir.exists()

        List<Class> results = []

        dir.eachFileRecurse(FileType.FILES) { file ->
            if (file.name.endsWith(".groovy")) {
                List subPackages = dir.relativePath(file).split("/").dropRight(1) as List
                subPackages.add(0, rootPackageName)
                String packageName = subPackages.join(".")
                println "Checking package: ${packageName}, file ${file.absolutePath}... "

                file.eachLine{
                    def matches = it =~ /(class|interface|trait|enum) ([A-Z][a-zA-Z0-9]+)/
                    if (matches.size() > 0) {
                        final String className = packageName + "." + matches[0][2]
                        println "Match! ${matches[0][2]}"
                        println "Loading ${className}..."

                        try {
                            results.add(Class.forName(className))
                        } catch (ClassNotFoundException)  {
                            println "Not found class ${className}"
                        }
                    }
                }
            }
        }

        return results
    }

    @CompileStatic
    static List<Class> findExportedClasses(String folder, String rootPackageName) {
        findAllClasses(folder, rootPackageName).findAll{
            c ->
                def m = c.declaredMethods.find{it.name == "_is_hubitat_ci_private"}
                if (m != null) {
                    return false
                }

                return true
        }
    }

    @CompileStatic
    private static String fixType(String rawTypeString, String basePackageName) {
        int arrayDimensions = 0
        while (rawTypeString[arrayDimensions] == '[') {
            ++arrayDimensions
        }

        String baseType = null
        switch (rawTypeString.substring(arrayDimensions)) {
            case "B":
                baseType = byte.class.name
                break
            case "C":
                baseType = char.class.name
                break
            case "D":
                baseType = double.class.name
                break
            case "F":
                baseType = float.class.name
                break
            case "I":
                baseType = int.class.name
                break
            case "J":
                baseType = long.class.name
                break
            case "S":
                baseType = short.class.name
                break
            case "Z":
                baseType = boolean.class.name
                break
            case ~/L.*;/:
                baseType = renameHubitatClass(rawTypeString[arrayDimensions + 1..-2], basePackageName)
                break
            default:
                assert arrayDimensions == 0: "It's not a properly formatted fields it seems: ${rawTypeString}"
                return renameHubitatClass(rawTypeString, basePackageName)
        }

        return baseType + "[]" * arrayDimensions
    }

    @CompileStatic
    private static HashMap<String, Set<String>> findNestedClasses(List allClasses) {
        HashMap<String, Set<String>> result = [:]

        allClasses.each {
            String className = it["class_name"]
            if (!className) {
                className = it["enum_name"]
                assert className: "Entry ${it} is neither class nor enum"
            }

            // Note: suboptimal algorithm - could be handled with indexes and substrings resulting in probably less copying
            String[] classNames = className.split(/\$/)
            String parent = null
            classNames.each {
                if (parent) {
                    String nestedClass = parent + '$' + it

                    if (result[parent] == null) {
                        result[parent] = [] as HashSet<String>
                    }

                    result[parent].add(nestedClass)
                    parent = nestedClass
                } else {
                    parent = it
                }
            }
        }

        return result
    }

    @CompileStatic
    private static String getSubPackageName(String fullClassName) {
        final String nameWithNoMainPackage = renameHubitatClass(fullClassName, "")
        int indexOfDot = nameWithNoMainPackage.lastIndexOf('.')
        if (indexOfDot == -1) {
            indexOfDot = 0
        }

        nameWithNoMainPackage.substring(0, indexOfDot)
    }

    final static String c_tab = "    "

    @CompileStatic
    private static String keepPartAfter(String str, Character separator) {
        int index = str.findLastIndexOf { it == separator }
        if (index != -1) {
            return str.substring(index + 1)
        } else {
            return str
        }
    }

    @CompileStatic
    private static String cleanupClassName(String str) {
        return keepPartAfter(keepPartAfter(str, '$' as Character), '.' as Character)
    }

    private static void generateEnumBody(StringBuilder result, int indentation, Map definition) {
        final String indent = c_tab * indentation
        final String memberIndent = indent + c_tab

        assert definition.enum_name: "This method should only be called for enum, but called for: ${definition}"
        result.append(indent).append("enum ${cleanupClassName(definition.enum_name)} {\n")

        definition.enum_values?.each {
            result.append(memberIndent).append(it).append(",\n")
        }

        result.append(indent).append('}').append('\n')
    }

    @CompileStatic
    private static HashMap<String, Object> convertMethodToLocalTypes(Map originalMethod, String basePackageName) {
        HashMap<String, Object> result = new HashMap(originalMethod)

        // Convert static methods returning class to non-static returning object this is easier to mock.
        if (originalMethod.returnType == 'java.lang.Class' && ((List)originalMethod.modifiers)?.contains('static')) {
            result.returnType = Object.class.name
        }

        // Just allow any method to be static or non-static, they can be called the same way, but mocking static
        // methods is harder.
        ((List)result.modifiers)?.remove('static')

        result.returnType = fixType((String) result.returnType, basePackageName)
        result.parameters = result.parameters.collect{ fixType((String)it, basePackageName) }

        return result
    }

    @CompileStatic
    private static void printMethodDeclaration(boolean makeMethodAbstract, StringBuilder result, Map method, String basePackageName) {
        method = convertMethodToLocalTypes(method, basePackageName)

        // static methods are converted to non-static, for easier mocking.

        if (makeMethodAbstract) {
            result.append('abstract ')
        }

        result.append((String) method.returnType).append(" ")
        result.append((String) method.name).append("(")

        boolean firstParam = true
        String parameterName = "a"

        method.parameters.each {
            p ->
                if (!firstParam) {
                    result.append(", ")
                } else {
                    firstParam = false;
                }

                result.append((String) p).append(' ').append(parameterName)
                ++parameterName
        }
        result.append(")")
    }

    @CompileStatic
    private static String printMethodDeclaration(boolean makeMethodAbstract, Map method, String basePackageName) {
        StringBuilder b = new StringBuilder()
        printMethodDeclaration(makeMethodAbstract, b, method, basePackageName)
        return b.toString()
    }

    @CompileStatic
    private static int compareMethodTypes(def first, def second) {
        // Object always matches anything, except for void
        if ((first == Object.class.name && second != void.class.name) ||
                (second == Object.class.name && first != void.class.name)) {
            return 0
        }

        def normalizePrimitiveTypes = {
            ((String)it)
            .replace(Byte.class.name, byte.class.name)
            .replace(Character.class.name, char.class.name)
            .replace(Double.class.name, double.class.name)
            .replace(Float.class.name, float.class.name)
            .replace(Integer.class.name, int.class.name)
            .replace(Long.class.name, long.class.name)
            .replace(Short.class.name, short.class.name)
            .replace(Boolean.class.name, boolean.class.name)
        }

        first = normalizePrimitiveTypes(first)
        second = normalizePrimitiveTypes(second)


        return first <=> second
    }
    /**
     * Loosely compare two method definitions
     * @param first
     * @param second
     */
    private static int compareMethods(def first, def second) {
        int result = first.parameters.size() <=> second.parameters.size()

        if (result == 0) {
            def firstModifiers = first.modifiers ? first.modifiers : []
            def secondModifiers = second.modifiers ? second.modifiers : []

            result = firstModifiers.size() <=> secondModifiers.size()
            if (result == 0) {
                List valuesAndComparators = [
                        [first.name, second.name, { a, b -> a <=> b }],
                        [first.returnType, second.returnType, { a, b -> compareMethodTypes(a, b) }],
                ]

                for (int p = 0; p != second.parameters.size(); ++p) {
                    valuesAndComparators << [first.parameters[p], second.parameters[p], { a, b -> compareMethodTypes(a, b) }]
                }

                for (int m = 0; m != secondModifiers.size(); ++m) {
                    valuesAndComparators << [firstModifiers[m], secondModifiers[m], { a, b -> a <=> b }]
                }

                for (int i = 0; i != valuesAndComparators.size(); ++i) {
                    final int cmpResult = valuesAndComparators[i][2](valuesAndComparators[i][0], valuesAndComparators[i][1])
                    if (cmpResult == 0) {
                        continue
                    }

                    return cmpResult
                }

                return 0
            }
        }

        return result
    }

    private static void generateClassBody(StringBuilder result, int indentation, Map definition, PreprocessedClasses definitions, Set<String> alreadyGenerated, String basePackageName) {
        final String indent = c_tab*indentation
        final String memberIndent = indent + c_tab

        assert definition.class_name: "This method should only be called for a class, but called for: ${definition}"
        result.append(indent)

        final boolean printClassAndNonAbstractMethods = definition.options ? "generate_class" in (definition.options as Set) : false

        result.append(printClassAndNonAbstractMethods ? "class " : "trait ").append("${cleanupClassName(definition.class_name)} {\n")

        definitions.nestedClasses[(String)definition.class_name]?.each{
            generateClass(result, indentation + 1, null, definitions.classDefinitions[it], definitions, alreadyGenerated)
        }


        definition.methods?.each{
            if (!("constructor" in it.modifiers)) {
                result.append(memberIndent)

                printMethodDeclaration(!printClassAndNonAbstractMethods, result, it, basePackageName)

                if (printClassAndNonAbstractMethods) {
                    result.append(" { null }")
                }

                result.append('\n')
            }
        }
        result.append(indent).append('}').append('\n')
    }

    @CompileStatic
    private static boolean generateClass(StringBuilder result, int indentation, String basePackageName, Map definition, PreprocessedClasses definitions, Set<String> alreadyGenerated) {
        final String name = readClassName(definition)

        if (name in alreadyGenerated) {
            return false
        }

        if (indentation == 0) {
            result.append("package ").append(basePackageName)
            final String subPackage = getSubPackageName(name)
            if (subPackage) {
                result.append('.').append(subPackage)
            }

            result.append("\n\n")
        }

        // A class
        if (definition.class_name) {
            generateClassBody(result, indentation, definition, definitions, alreadyGenerated, basePackageName)
        } else {
            generateEnumBody(result, indentation, definition)
        }

        alreadyGenerated.add(readClassName(definition))
        return true
    }

    @CompileStatic
    private static String readClassName(Map classOrEnumDefinition) {
        classOrEnumDefinition.class_name ? (String)classOrEnumDefinition.class_name : (String)classOrEnumDefinition.enum_name
    }

    @CompileStatic
    private static Tuple2<String, String> classNameToFilePath(String className) {
        final String[] classParts = renameHubitatClass(className, "").split(/\./)
        String fileName = classParts.last() + ".groovy"
        return new Tuple2(classParts.dropRight(1).join("/"), fileName)
    }

    @CompileStatic
    private static def generateClasses(
            String generatedSource,
            String manualOverrides,
            String targetDirectory,
            String basePackageName,
            boolean generateFilesWithDifferentNameWhenExist = false) {
        def classes = (List)readClassesFromJson(generatedSource, manualOverrides)
        HashSet<String> alreadyGenerated = [] as HashSet

        PreprocessedClasses preprocessed = new PreprocessedClasses(
                classes.collectEntries{[readClassName(it as Map), it]},
                findNestedClasses(classes as List)
        )

        classes.each {
            StringBuilder result = new StringBuilder()
            Map itAsMap = it as Map

            if (generateClass(result, 0, basePackageName, itAsMap, preprocessed, alreadyGenerated)) {
                Tuple2<String, String> folderAndFileName = classNameToFilePath(readClassName(itAsMap))
                String folder = targetDirectory + "/" + folderAndFileName[0]
                new File(folder).mkdirs()

                File file = new File(folder + "/" + folderAndFileName[1])
                if (file.exists()) {
                    if (generateFilesWithDifferentNameWhenExist) {
                        file = new File((folder + "/" + folderAndFileName[1]).replace(".groovy", "-generated.groovy.txt"))
                        if (file.exists()) {
                            file.delete()
                        }
                    } else {
                        file = null
                    }
                }

                if (file) {
                    println "Generating file ${file} with class ${readClassName(itAsMap)}"
                    file.write(result.toString())
                } else {
                    println "File ${file} skipped - existing, and generateFilesWithDifferentNameWhenExist = ${generateFilesWithDifferentNameWhenExist}"
                }
            }
        }
    }
}
