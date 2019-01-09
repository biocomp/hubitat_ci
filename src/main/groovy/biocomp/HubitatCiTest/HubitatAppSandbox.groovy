package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.AppPreferencesReader
import biocomp.hubitatCiTest.apppreferences.Preferences

import biocomp.hubitatCiTest.emulation.appApi.AppExecutor as AppExecutorInterface
import biocomp.hubitatCiTest.emulation.appApi.PreferencesSource as PreferencesSourceInterface
import biocomp.hubitatCiTest.emulation.appApi.DefinitionReader as DefinitionReaderInterface
import groovy.json.JsonBuilder
import groovy.time.TimeCategory
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


class HubitatAppSandbox {
    HubitatAppSandbox(File file) {
        this.file = file
        assert file
    }

    HubitatAppSandbox(String scriptText) {
        this.text = scriptText
        assert scriptText
        assert !scriptText.empty
    }

    HubitatAppScript setupScript(
            boolean runScript = true,
            AppExecutorInterface api = new NoopAppExecutor(),
            Closure<AppExecutorInterface> addPreferencesReader = {
                delegate, script -> new AppPreferencesReader(script, delegate) as AppExecutorInterface
            },
            Closure<AppExecutorInterface> addDefinitionsReader = {
                delegate -> new AppDefinitionValidator(delegate) as AppExecutorInterface
            })
    {
        return setupScriptWithPrefsAndSources(runScript, api, addPreferencesReader, addDefinitionsReader).get(
                0) as HubitatAppScript
    }

    Tuple3<HubitatAppScript, PreferencesSourceInterface, DefinitionReaderInterface> setupScriptWithPrefsAndSources(
            boolean runScript = true,
            AppExecutorInterface api = new NoopAppExecutor(),
            Closure<AppExecutorInterface> addPreferencesReader = {
                delegate, script -> new AppPreferencesReader(script, delegate) as AppExecutorInterface
            })
    {
        return setupScriptWithPrefsAndSources(
                runScript,
                api,
                addPreferencesReader,
                { delegate -> new AppDefinitionValidator(delegate) as AppExecutorInterface
        })
    }

    @TypeChecked
    Tuple3<HubitatAppScript, PreferencesSourceInterface, DefinitionReaderInterface> setupScriptWithPrefsAndSources(
            boolean runScript,
            AppExecutorInterface api,
            Closure<AppExecutorInterface> addPreferencesReader,
            Closure<AppExecutorInterface> addDefinitionsReader)
    {

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

        AppExecutorInterface preferencesReader = addPreferencesReader(api, script)
        AppExecutorInterface definitionsReader = addDefinitionsReader(preferencesReader)

        script.api = definitionsReader
        script.run()
        return new Tuple3(script, preferencesReader, definitionsReader)
    }

    Preferences readPreferences() {
        AppPreferencesReader preferencesReader = null
        def wrapPreferencesReader = { AppExecutorInterface delegate, HubitatAppScript script ->
            preferencesReader = new AppPreferencesReader(script, delegate)
            return preferencesReader
        }

        setupScript(true, new NoopAppExecutor(), wrapPreferencesReader, { it })
        return preferencesReader.getProducedPreferences()
    }

    void mandatoryConfigIsSet() {
        setupScript(true,
                new NoopAppExecutor(),
                { def script, def api -> api },
                { def api -> new AppDefinitionValidator(api) })
    }

    void runBasicValidations() {
        setupScript(true)
    }

    @TypeChecked
    private static void restricScript(CompilerConfiguration options)
    {
        def scz = new SecureASTCustomizer()
        scz.with{
            receiversClassesWhiteList = [
                    java.lang.Object,
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
                if (expr.methodAsString == "println")
                {
                    println "Returning false!"
                    return false;
                }
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