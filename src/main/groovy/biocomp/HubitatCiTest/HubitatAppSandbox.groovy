package biocomp.hubitatCiTest


import biocomp.hubitatCiTest.apppreferences.Preferences
import biocomp.hubitatCiTest.apppreferences.ValidationFlags
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import groovy.json.JsonBuilder
import groovy.time.TimeCategory
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.AttributeExpression
import org.codehaus.groovy.ast.expr.ClassExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SourceAwareCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import sun.util.calendar.ZoneInfo

import java.beans.Expression
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@TypeChecked
class HubitatAppSandbox {
    HubitatAppSandbox(File file) {
        this.file = file
        assert file
    }

    HubitatAppSandbox(String scriptText) {
        this.text = scriptText
    }

    HubitatAppScript compile(Map options = [:]) {
        addFlags(options, [ValidationFlags.DontRunScript])
        return setupImpl(options)
    }

    HubitatAppScript run(Map options = [:]) {
        return setupImpl(options)
    }

    /**
     * Calls run() with ValidationFlags.DontValidateDefinition and returns juts preferences, not script.
     * @param options
     * @return
     */
    Preferences readPreferences(Map options = [:]) {
        addFlags(options, [ValidationFlags.DontValidateDefinition])
        setupImpl(options).getProducedPreferences()
    }

    private HubitatAppScript setupImpl(Map options) {
        validateAndUpdateSandboxOptions(options)

        // Use custom HubitatAppScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = HubitatAppScript.class.name
        restrictScript(compilerConfiguration)
        makePrivatePublic(compilerConfiguration)

        def shell = new GroovyShell(new SandboxClassLoader(this.class.classLoader),
                new DoNotCallMeBinding(),
                compilerConfiguration)

        HubitatAppScript script = null
        if (file) {
            script = shell.parse(file) as HubitatAppScript
        } else {
            script = shell.parse(text) as HubitatAppScript
        }

        def validationFlags = readFlags(options)
        script.initialize(options.api as AppExecutor, readFlags(options), readUserSettingValues(options))
        customizeScript(options, script)

        if (!validationFlags.contains(ValidationFlags.DontRunScript)) {
            script.run()
        }

        return script
    }

    private static void customizeScript(Map options, HubitatAppScript script) {
        (options.customizeScriptBeforeRun as Closure)?.call(script)
    }

    @TypeChecked(TypeCheckingMode.SKIP)
    private static EnumSet<ValidationFlags> readFlags(Map options) {
        def flags = EnumSet.noneOf(ValidationFlags)
        options.validationFlags?.each { flags.add(it) }
        return flags
    }

    private static Map readUserSettingValues(Map options) {
        return options.userSettingValues ? options.userSettingValues as Map : [:]
    }

    private static void validateAndUpdateSandboxOptions(Map options) {
        def allKeys = new HashSet<String>(options.keySet());

        if (options.containsKey('api')) {
            allKeys.remove('api')

            assert options['api'] == null || options[
                    'api'] instanceof AppExecutor: "'app' value must be null or implement AppExecutor interface"
        }

        if (options.containsKey('userSettingValues')) {
            allKeys.remove('userSettingValues')

            assert options['userSettingValues'] != null
            assert (options[
                    'userSettingValues'] as Map<String, Object>): "'userSettingValues' must be a map of String->Object options"
        }

        if (options.containsKey('customizeScriptBeforeRun')) {
            allKeys.remove('customizeScriptBeforeRun')

            assert options['customizeScriptBeforeRun'] != null
            assert options[
                    'customizeScriptBeforeRun'] instanceof Closure: "'customizeScriptBeforeRun' should be a closure that takes HubitatAppScript as a single parameter"
        }

        if (options.containsKey('validationFlags')) {
            allKeys.remove('validationFlags')

            assert options['validationFlags'] != null
            assert options[
                    'validationFlags'] as List<ValidationFlags>: "'validationFlags' should be a list of validation flags"
        }

        if (options.containsKey('noValidation')) {
            allKeys.remove('noValidation')

            assert options['noValidation'] != null

            if (options.noValidation) {
                addFlags(options, [ValidationFlags.DontValidateDefinition, ValidationFlags.DontValidatePreferences])
            }
        }

        assert allKeys.isEmpty(): "These options are not supported: ${allKeys}"
    }

    static private void addFlags(Map options, List<ValidationFlags> flags) {
        options.putIfAbsent('validationFlags', [])
        (options.validationFlags as List<ValidationFlags>).addAll(flags)
    }


    static final Set<String> forbiddenExpressions = ["execute",
                                                     "getClass",
                                                     "getMetaClass",
                                                     "setMetaClass",
                                                     "propertyMissing",
                                                     "methodMissing",
                                                     "invokeMethod",
                                                     "print",
                                                     "println",
                                                     "printf",
                                                     "sleep",
                                                     "getProducedPreferences", // Script's test-only use method
                                                     "producedPreferences", // Script's test-only use property
                                                     "getProducedDefinition", // Script's test-only use method
                                                     "producedDefinition" // Script's test-only use property
    ] as Set

    private static List<Class> classWhiteList = [java.lang.Object,
                                                 groovy.lang.GString,
                                                 org.codehaus.groovy.runtime.InvokerHelper,
                                                 ArrayList,
                                                 int,
                                                 boolean,
                                                 byte,
                                                 char,
                                                 short,
                                                 long,
                                                 float,
                                                 double,
                                                 BigDecimal,
                                                 BigInteger,
                                                 Boolean,
                                                 Byte,
                                                 ByteArrayInputStream,
                                                 ByteArrayOutputStream,
                                                 Calendar,
                                                 Closure,
                                                 Collection,
                                                 Collections,
                                                 Date,
                                                 DecimalFormat,
                                                 Double,
                                                 Float,
                                                 GregorianCalendar,
                                                 HashMap,
                                                 //HashMap.Entry,
                                                 //                    HashMap,
                                                 //                    HashMap.KeySet,
                                                 //                    HashMap.Values,
                                                 HashSet,
                                                 Integer,
                                                 JsonBuilder,
                                                 LinkedHashMap,
                                                 //LinkedHashMap.Entry,
                                                 LinkedHashSet,
                                                 LinkedList,
                                                 List,
                                                 Long,
                                                 Map,
                                                 MarkupBuilder,
                                                 Math,
                                                 Random,
                                                 Set,
                                                 Short,
                                                 SimpleDateFormat,
                                                 String,
                                                 StringBuilder,
                                                 StringReader,
                                                 StringWriter,
                                                 //SubList,
                                                 TimeCategory,
                                                 TimeZone,
                                                 TreeMap,
                                                 //                    TreeMap.Entry,
                                                 //                    TreeMap.KeySet,
                                                 //                    TreeMap.Values,
                                                 TreeSet,
                                                 URLDecoder,
                                                 URLEncoder,
                                                 UUID,
                                                 ZoneInfo,
                                                 //com.amazonaws.services.s3.model.S3Object,
                                                 //com.amazonaws.services.s3.model.S3ObjectInputStream,
                                                 com.sun.org.apache.xerces.internal.dom.DocumentImpl,
                                                 com.sun.org.apache.xerces.internal.dom.ElementImpl,
                                                 groovy.json.JsonOutput,
                                                 groovy.json.JsonSlurper,
                                                 groovy.util.Node,
                                                 groovy.util.NodeList,
                                                 groovy.util.XmlParser,
                                                 groovy.util.XmlSlurper,
                                                 groovy.xml.XmlUtil,
                                                 java.net.URI,
                                                 java.util.RandomAccessSubList,
                                                 //org.apache.commons.codec.binary.Base64,
                                                 //org.apache.xerces.dom.DocumentImpl,
                                                 //org.apache.xerces.dom.ElementImpl,
                                                 org.codehaus.groovy.runtime.EncodingGroovyMethods,
                                                 //org.json.JSONArray,
                                                 //org.json.JSONException,
                                                 //org.json.JSONObject,
                                                 //org.json.JSONObject.Null,
                                                 biocomp.hubitatCiTest.emulation.Protocol,
                                                 biocomp.hubitatCiTest.emulation.commonApi.HubAction,
                                                 biocomp.hubitatCiTest.emulation.commonApi.HubResponse

    ] as List<Class>


    static private HashSet<String> classNameWhiteList = new HashSet<String>(
            classWhiteList.collect { it.name } as List<String>)

    private static boolean isClassAllowed(ClassNode classNode) {
        if (classNameWhiteList.contains(classNode.name)) {
            return true;
        }

        return classNode.isScript()
    }

    private static void restrictScript(CompilerConfiguration options) {
        def scz = new SecureASTCustomizer()

        def checker = { expr ->
            if (expr instanceof MethodCallExpression) {
                return !forbiddenExpressions.contains(expr.methodAsString) && isClassAllowed(
                        expr.getObjectExpression().getType())
            }

            if (expr instanceof PropertyExpression) {
                return !forbiddenExpressions.contains(expr.propertyAsString) && isClassAllowed(
                        expr.getObjectExpression().getType())
            }

            if (expr instanceof AttributeExpression) {
                return !forbiddenExpressions.contains(expr.propertyAsString) && isClassAllowed(
                        expr.getObjectExpression().getType())
            }

            if (expr instanceof VariableExpression) {
                return !forbiddenExpressions.contains(expr.name)
            }

            if (expr instanceof StaticMethodCallExpression) {
                return !forbiddenExpressions.contains(expr.methodAsString) && isClassAllowed(expr.getOwnerType())
            }

            return true;
        } as SecureASTCustomizer.ExpressionChecker

        scz.addExpressionCheckers(checker)

        def sac = new SourceAwareCustomizer(scz)

        sac.sourceUnitValidator = {
            println "SourceUnit: ${it}"
            return true
        }

        sac.classValidator = { ClassNode cn ->
            println "ClassNode: ${cn}. scriptBody = ${cn.scriptBody}"

            if (!cn.scriptBody) {
                throw new SecurityException("Can't define classes in the script, but you defined '${cn}'")
            }

            return true
        }

        options.addCompilationCustomizers(sac)
    }

    private static void makePrivatePublic(CompilerConfiguration options) {
        options.addCompilationCustomizers(new RemovePrivateFromScriptCompilationCustomizer())
    }

    final private File file = null
    final private String text = null
}