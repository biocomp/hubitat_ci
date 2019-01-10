package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.AppPreferencesReader
import biocomp.hubitatCiTest.apppreferences.Preferences
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import com.sun.scenario.Settings
import groovy.json.JsonBuilder
import groovy.time.TimeCategory
import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SourceAwareCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import sun.util.calendar.ZoneInfo

import java.text.DecimalFormat
import java.text.SimpleDateFormat

@TupleConstructor
class SandboxResult
{
    HubitatAppScript script
    Preferences preferences
    Map<String, Object> definitions
}

class HubitatAppSandbox {
    HubitatAppSandbox(File file) {
        this.file = file
        assert file
    }

    HubitatAppSandbox(String scriptText) {
        this.text = scriptText
    }

    @TypeChecked
    HubitatAppScript compile(SettingsCheckingMode settingsMode = SettingsCheckingMode.Strict)
    {
        return setupImpl(false, false, false, SettingsCheckingMode.None, null).script
    }

    @TypeChecked
    HubitatAppScript setupAndValidate(AppExecutor api = null, SettingsCheckingMode settingsMode = SettingsCheckingMode.Strict)
    {
        return setupImpl(true, true, true, settingsMode, api).script
    }

    @TypeChecked
    SandboxResult setupWithPreferencesAndDefinitions(AppExecutor api, SettingsCheckingMode settingsMode = SettingsCheckingMode.Strict)
    {
        return setupImpl(true, true, true, settingsMode, api)
    }

    @TypeChecked
    HubitatAppScript setupNoValidation(AppExecutor api)
    {
        return setupImpl(true, false, false, SettingsCheckingMode.None, api).script
    }

    @TypeChecked
    private SandboxResult setupImpl(
            boolean run = true,
            boolean readAndValidatePropertiesOnRun = true,
            boolean readAndValidateDefinitionsOnRun = true,
            SettingsCheckingMode settingsMode,
            AppExecutor api)
    {
        if (readAndValidatePropertiesOnRun || readAndValidateDefinitionsOnRun)
        {
            // Need to run the script too to be able to get preferences and definitions
            assert run : "You can't read properties or definitions without running the script"
        }

        if (settingsMode == SettingsCheckingMode.Strict)
        {
            assert readAndValidatePropertiesOnRun : "properties() need to be read and validated if you also want to validate settings - we need to match input names"
        }

        // Use custom HubitatAppScript.
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = HubitatAppScript.class.name
        restricScript(compilerConfiguration)

        def shell = new GroovyShell(new SandboxClassLoader(this.class.classLoader),
                new DoNotCallMeBinding(),
                compilerConfiguration)

        HubitatAppScript script = null
        if (file) {
            script = shell.parse(file) as HubitatAppScript
        } else {
            script = shell.parse(text) as HubitatAppScript
        }

        AppPreferencesReader preferencesReader = null
        if (readAndValidatePropertiesOnRun)
        {
            preferencesReader = new AppPreferencesReader(script, api)
            api = preferencesReader;
        }

        AppDefinitionReader definitionReader = null
        if (readAndValidateDefinitionsOnRun)
        {
            definitionReader = new AppDefinitionReader(api)
            api = definitionReader
        }

        def settings = new SettingsProvider(api, settingsMode)
        api = settings

        script.setApi(api)
        script.setSettingsMap(settings)

        if (run) {
            script.run()
        }

        if (preferencesReader) {
            settings.setPreferences(preferencesReader.producedPreferences)
        }

        //return new SandboxResult(script, preferencesReader?.producedPreferences, null)
        return new SandboxResult(script, preferencesReader?.producedPreferences, null)
    }

    @TypeChecked
    Preferences readPreferences() {
        setupImpl(true, true, false, SettingsCheckingMode.None, null).preferences
    }

    static final Set<String> forbiddenExpressions = [
            "execute",
            "getClass",
            "getMetaClass",
            "setMetaClass",
            "propertyMissing",
            "methodMissing",
            "invokeMethod",
            "print",
            "println",
            "printf",
            "sleep"
    ] as Set

    @TypeChecked
    private static void restricScript(CompilerConfiguration options)
    {

        
        def scz = new SecureASTCustomizer()
        scz.with{
            receiversClassesWhiteList = [
                    java.lang.Object,
                    groovy.lang.GString,
                    org.codehaus.groovy.runtime.InvokerHelper,
                    ArrayList,
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
            ] as List<Class>
        }


        def checker = { expr ->
            if (expr instanceof MethodCallExpression)
            {
                return !forbiddenExpressions.contains(expr.methodAsString)
            }

            return true;
        } as SecureASTCustomizer.ExpressionChecker

        scz.addExpressionCheckers(checker)

        def sac = new SourceAwareCustomizer(scz)

        sac.sourceUnitValidator = {
            println "SourceUnit: ${it}"
            return true
        }

        sac.classValidator = {
            ClassNode cn ->
                println "ClassNode: ${cn}. scriptBody = ${cn.scriptBody}"

                if (!cn.scriptBody)
                {
                    throw new SecurityException("Can't define classes in the script, but you defined '${cn}'")
                }

                return true
        }

        options.addCompilationCustomizers(sac)
    }

    final private File file = null
    final private String text = null
}