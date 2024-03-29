package me.biocomp.hubitat_ci.validation

import me.biocomp.hubitat_ci.util.AddValidationAfterEachMethodCompilationCustomizer
import me.biocomp.hubitat_ci.util.DoNotCallMeBinding
import me.biocomp.hubitat_ci.util.LoggingCompilationCustomizer
import me.biocomp.hubitat_ci.util.RemovePrivateFromScriptCompilationCustomizer
import me.biocomp.hubitat_ci.util.SandboxClassLoader
import me.biocomp.hubitat_ci.util.integration.TimeKeeper
import groovy.json.JsonBuilder
import groovy.time.TimeCategory
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.CompilationCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.control.customizers.SourceAwareCustomizer
import sun.util.calendar.ZoneInfo

import java.text.DecimalFormat
import java.text.SimpleDateFormat

@TypeChecked
class ValidatorBase {
    private final static Set<String> originalForbiddenExpressions =
            ["execute",
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
            ] as HashSet

    private static HashSet<Class> classOriginalWhiteList = [java.lang.Object,
                                                            java.lang.Exception,
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
                                                            TimeKeeper,
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
                                                            // java.util.RandomAccessSubList,
                                                            //org.apache.commons.codec.binary.Base64,
                                                            //org.apache.xerces.dom.DocumentImpl,
                                                            //org.apache.xerces.dom.ElementImpl,
                                                            org.codehaus.groovy.runtime.EncodingGroovyMethods,
                                                            //org.json.JSONArray,
                                                            //org.json.JSONException,
                                                            //org.json.JSONObject,
                                                            //org.json.JSONObject.Null,
                                                            me.biocomp.hubitat_ci.api.Protocol,
                                                            me.biocomp.hubitat_ci.api.common_api.HubAction,
                                                            me.biocomp.hubitat_ci.api.common_api.HubResponse,
                                                            me.biocomp.hubitat_ci.api.common_api.Location

    ] as HashSet<Class>

    private final HashSet<String> classNameWhiteList
    private final HashSet<String> forbiddenExpressions

    static private HashSet<String> initClassNames(List<Class> extraAllowedClasses) {
        return (classOriginalWhiteList + (extraAllowedClasses as HashSet)).collect { it.name } as HashSet;
    }

    static private HashSet<String> initForbiddenExpressions(List<String> extraAllowedExpressions) {
        return (originalForbiddenExpressions - (extraAllowedExpressions as HashSet)) as HashSet;
    }

    ValidatorBase(
            EnumSet<Flags> setOfFlags,
            List<Class> extraAllowedClasses,
            List<String> extraAllowedExpressions)
    {
        this.flags = setOfFlags
        this.classNameWhiteList = initClassNames(extraAllowedClasses)
        this.forbiddenExpressions = initForbiddenExpressions(extraAllowedExpressions)
    }

    ValidatorBase(
            List<Flags> listOfFlags, List<Class> extraAllowedClasses, List<String> extraAllowedExpressions)
    {
        this(flagsListToSet(listOfFlags), extraAllowedClasses, extraAllowedExpressions)
    }

    static private EnumSet<Flags> flagsListToSet(List<Flags> listOfFlags)
    {
        def setOfFlags = EnumSet.noneOf(Flags)
        listOfFlags?.each { setOfFlags.add(it) }
        return setOfFlags
    }

    boolean hasFlag(Flags flag) {
        return flags.contains(flag)
    }

    EnumSet<Flags> getFlags()
    {
        return flags
    }

    private final EnumSet<Flags> flags

    private void restrictScript(CompilerConfiguration options) {
        if(hasFlag(Flags.DontRestrictGroovy)) {
            return
        }

        def scz = new SecureASTCustomizer()

        def privateForbiddenExpressions = this.forbiddenExpressions
        def privateClassNameWhiteList = this.classNameWhiteList

        def checkerCapture = { expr ->
            if (expr instanceof MethodCallExpression) {
                return !privateForbiddenExpressions.contains(expr.methodAsString) && isClassAllowed(privateClassNameWhiteList,
                        expr.getObjectExpression().getType())
            }

            if (expr instanceof PropertyExpression) {
                return !privateForbiddenExpressions.contains(expr.propertyAsString) && isClassAllowed(privateClassNameWhiteList,
                        expr.getObjectExpression().getType())
            }

            if (expr instanceof AttributeExpression) {
                return !privateForbiddenExpressions.contains(expr.propertyAsString) && isClassAllowed(privateClassNameWhiteList,
                        expr.getObjectExpression().getType())
            }

            if (expr instanceof VariableExpression) {
                return !privateForbiddenExpressions.contains(expr.name)
            }

            if (expr instanceof StaticMethodCallExpression) {
                return !privateForbiddenExpressions.contains(expr.methodAsString) && isClassAllowed(privateClassNameWhiteList, expr.getOwnerType())
            }

            return true;
        }

        checkerCapture.resolveStrategy = Closure.DELEGATE_ONLY
        checkerCapture.delegate = this as ValidatorBase

        def checker = checkerCapture as SecureASTCustomizer.ExpressionChecker

        scz.addExpressionCheckers(checker)

        def sac = new SourceAwareCustomizer(scz)

        sac.sourceUnitValidator = {
            return true
        }

        sac.classValidator = { ClassNode cn ->
            if (!cn.scriptBody) {
                throw new SecurityException("Can't define classes in the script, but you defined '${cn}'")
            }

            return true
        }

        options.addCompilationCustomizers(sac)
    }

    private static boolean isClassAllowed(HashSet<String> classNameWhiteList, ClassNode classNode) {
        if (classNameWhiteList.contains(classNode.name)) {
            return true
        }

        if (classNode.name.startsWith('me.biocomp.hubitat_ci.api.device_api.zwave') ||
            classNode.name.startsWith('me.biocomp.hubitat_ci.api.device_api.zigbee'))
        {
            return true
        }

        return classNode.isScript()
    }

    private static void makePrivatePublic(CompilerConfiguration options) {
        options.addCompilationCustomizers(new RemovePrivateFromScriptCompilationCustomizer())
    }

    private static void validateAfterEachMethod(CompilerConfiguration options) {
        options.addCompilationCustomizers(new AddValidationAfterEachMethodCompilationCustomizer())

        // debug - print out resulting script:
        // options.addCompilationCustomizers(new LoggingCompilationCustomizer())
    }

    @TypeChecked(TypeCheckingMode.SKIP)
    private static void addExtraCustomizers(CompilerConfiguration cc, List<CompilationCustomizer> extraCompilationCustomizers)
    {
        if (extraCompilationCustomizers) {
            cc.addCompilationCustomizers(*extraCompilationCustomizers);
        }
    }

    @CompileStatic
    protected GroovyShell constructParser(Class c, List<CompilationCustomizer> extraCompilationCustomizers = []) {
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = c.name

        restrictScript(compilerConfiguration)
        makePrivatePublic(compilerConfiguration)
        validateAfterEachMethod(compilerConfiguration)
        addExtraCustomizers(compilerConfiguration, extraCompilationCustomizers)

        return new GroovyShell(
                new SandboxClassLoader(c.classLoader),
                new DoNotCallMeBinding(),
                compilerConfiguration);
    }

    boolean hasAnyOfFlags(Set<Flags> flags) {
        return this.flags.intersect(flags)
    }

    String patchScriptText(String scriptText) {
        scriptText = scriptText.replaceAll("new\\s*Date\\s*\\(\\s*\\)", "me.biocomp.hubitat_ci.util.integration.TimeKeeper.now()")
        return scriptText
    }
}