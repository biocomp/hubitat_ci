package biocomp.hubitatCi.validation

import biocomp.hubitatCi.AppDefinitionReader
import biocomp.hubitatCi.AppMappingsReader
import biocomp.hubitatCi.DoNotCallMeBinding
import biocomp.hubitatCi.HubitatAppScript
import biocomp.hubitatCi.RemovePrivateFromScriptCompilationCustomizer
import biocomp.hubitatCi.SandboxClassLoader

import biocomp.hubitatCi.apppreferences.AppPreferencesReader
import biocomp.hubitatCi.apppreferences.HRef
import biocomp.hubitatCi.apppreferences.Page
import biocomp.hubitatCi.apppreferences.Preferences
import biocomp.hubitatCi.apppreferences.Input
import biocomp.hubitatCi.capabilities.Capabilities
import groovy.json.JsonBuilder
import groovy.time.TimeCategory
import groovy.transform.TypeChecked
import groovy.xml.MarkupBuilder

import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.AttributeExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.control.customizers.SourceAwareCustomizer
import sun.util.calendar.ZoneInfo

import java.text.DecimalFormat
import java.text.SimpleDateFormat


@TypeChecked
class AppValidator extends ValidatorBase{

    private static final NamedParametersValidator preferencesValidatorWithOauth = NamedParametersValidator.make {
        stringParameter(name: "oauthPage", required: true)
    }

    private static final NamedParametersValidator preferencesValidatorNoOauth = NamedParametersValidator.make {
    }

    private static final NamedParametersValidator inputOptionsValidator = NamedParametersValidator.make {
        boolParameter(name: "capitalization")
        objParameter(name: "defaultValue")
        stringParameter(name: "name", required: true)
        stringParameter(name: "title")
        stringParameter(name: "description")
        boolParameter(name: "multiple")
        numericRangeParameter(name: "range")
        boolParameter(name: "required")
        boolParameter(name: "submitOnChange")
        listOfStringsParameter(name: "options")
        stringParameter(name: "type", required: true)
        boolParameter(name: "hideWhenEmpty")
    }

    private static final HashSet<String> validStaticInputTypes = [
        "bool",
        //"bolean",
        "decimal",
        "email",
        "enum",
        "hub",
        "icon",
        "number",
        "password",
        "phone",
        "time",
        "text"
    ] as HashSet

    AppValidator(
            EnumSet<Flags> setOfFlags = EnumSet.noneOf(Flags), List<Class> extraAllowedClasses = [],
            List<String> extraAllowedExpressions = [])
    {
        super(setOfFlags, extraAllowedClasses, extraAllowedExpressions)
    }

    AppValidator(
            List<Flags> listOfFlags, List<Class> extraAllowedClasses = [], List<String> extraAllowedExpressions = [])
    {
        super(listOfFlags, extraAllowedClasses, extraAllowedExpressions)
    }

    HubitatAppScript parseScript(File scriptFile) {
        return constructParser(HubitatAppScript).parse(scriptFile) as HubitatAppScript
    }

    HubitatAppScript parseScript(String scriptText) {
        return constructParser(HubitatAppScript).parse(scriptText) as HubitatAppScript
    }

    void validateAfterRun(AppDefinitionReader definitionReader, AppPreferencesReader preferencesReader, AppMappingsReader mappingsReader)
    {
        def preferences = preferencesReader.getProducedPreferences() // Just to trigger validation
        def definitions = definitionReader.getDefinitions() // Trigger definition validation

        if (preferences && !hasFlag(Flags.DontValidatePreferences))
        {
            validatePreferences(preferences, false, definitions?.oauth as boolean)
        }
    }


    /**
     * Run validations after all pages and sections were added
     * */
    void validatePreferences(Preferences preferences, boolean okEmptyIfRegisteredDynamicPages, boolean oauthEnabled) {
        if (preferences.hasSpecialSinglePage()) {
            preferences.specialSinglePage.validate(this)
        }

        if (!hasFlag(Flags.DontValidatePreferences)) {
            // Needs to have pages
            if (!okEmptyIfRegisteredDynamicPages || preferences.dynamicRegistrations.size() == 0) {
                assert (preferences.pages.size() != 0 || preferences.dynamicPages.size() != 0): "preferences() has to have pages (got ${preferences.pages.size()}), dynamic pages (got ${preferences.dynamicPages.size()})"
            }

            // Page names must be unique
            def allNames = preferences.pages.collect { it.readName() } + preferences.dynamicPages.collect { it.readName() }
            def uniqueNames = [] as HashSet
            allNames.each {
                assert !uniqueNames.contains(
                        it): "Page '${it}' is not a unique page name. Page names must be unique. Please fix that. All page names: ${allNames}"
                uniqueNames << it
            }

            // Multi-page app needs explicit 'install' (otherwise you won't be able to install this)
            if (!preferences.hasSpecialSinglePage() && !hasFlag(Flags.AllowMissingInstall)) {
                assert preferences.pages.findAll { it.options.install == true } + preferences.dynamicPages.findAll {
                    it.options.install == true
                }: "There is no 'install: true' option specified on any page (and it's not a special one-page app). This means you can't install the app. Please add the button to a page."
            }

            // Pages must be reachable
            validatePagesAreReachable(preferences)

            // Validate oauth page
            validateOauth(oauthEnabled, preferences, uniqueNames)
        }
    }

    private void validateOauth(boolean oauthEnabled, Preferences preferences, HashSet<String> uniqueNames)
    {
        if (oauthEnabled && !preferences.hasSpecialSinglePage())
        {
            if (!hasFlag(Flags.AllowMissingOAuthPage)) {
                def oauthPageName = preferences.options.oauthPage as String
                preferencesValidatorWithOauth.validate("preferences()", preferences.options, this)
                assert uniqueNames.contains(oauthPageName): "preferences(): oauthPage = '${oauthPageName}' does not point to a valid page"

                assert preferences.allPages[0].readName() == oauthPageName: "Page '${oauthPageName}' pointed by 'oauthPage' must be a first page, but first page is ${preferences.allPages[0]}"
                assert !preferences.allPages[0].isDynamicPage(): "Page '${oauthPageName}' pointed by 'oauthPage' must be static, not a dynamic page"
            }
        }
        else
        {
            preferencesValidatorNoOauth.validate("preferences()", preferences.options, this)
        }
    }


    private static Page getPageOrAssert(Page currentPage, String referredPage, HashMap<String, Page> allPages)
    {
        def foundPage = allPages[referredPage]
        assert foundPage: "${this}: Page's name '${referredPage}' referred by ${currentPage} is not valid. Valid page names are: ${allPages.keySet()}"
        return foundPage
    }

    private static void addReachablePages(Page p, HashMap<String, Page> allPages, HashSet<String> reachablePages)
    {
        if (!reachablePages.contains(p.readName())) {
            reachablePages << p.readName()

            p.sections.each {
                it.children.each {
                    if (it instanceof HRef) {
                        def referredPage = it.readPage()
                        if (referredPage) {
                            addReachablePages(getPageOrAssert(p, referredPage, allPages), allPages, reachablePages)
                        }
                    }
                }
            }

            if (p.options?.nextPage) {
                addReachablePages(getPageOrAssert(p, p.options.nextPage as String, allPages), allPages, reachablePages)
            }
        }
    }

    private void validatePagesAreReachable(Preferences preferences)
    {
        if (!hasFlag(Flags.AllowUnreachablePages)) {
            def reachablePages = [] as HashSet<String>

            def pagesCombined = (preferences.pages + preferences.dynamicPages)

            if (!pagesCombined.isEmpty()) {
                def allPages = [] as HashMap<String, Page>;
                (pagesCombined).each {
                    allPages[it.readName()] = it
                }


                addReachablePages(pagesCombined[0], allPages, reachablePages);

                def unreachablePages = allPages.keySet() - reachablePages
                assert !unreachablePages: "${this}: pages ${unreachablePages} are not reachable via 'href' or 'nextPage', and thus don't make sense to have";
            }
        }
    }

    void validateInput(Input input)
    {
        if (!hasFlag(Flags.DontValidatePreferences)) {
            inputOptionsValidator.validate(
                    input.toString(),
                    input.unnamedOptions,
                    input.options,
                    this)

            validateInputType(input)
        }
    }

    private void validateInputType(Input input)
    {
        final def inputType = input.readType()
        if (validStaticInputTypes.contains(inputType))
        {
            return
        }

        if (inputType =~ /capability\.[a-zA-Z0-9._]+/)
        {
            if (Capabilities.capabilitiesByDeviceSelector.containsKey(inputType.substring(11)))
            {
                return
            }
            else
            {
                assert false : "Input ${input}'s capability '${inputType}' is not supported. Supported capabilities: ${Capabilities.capabilitiesByDeviceSelector.keySet()}"
            }
        }

        if (inputType =~ /device\.[a-zA-Z0-9._]+/)
        {
            return
        }

        assert false : "Input ${input}'s type ${inputType} is not supported. Valid types are: ${validStaticInputTypes} + 'device.yourDeviceName'"
    }

    void validateSingleDynamicPageFor(Preferences preferences, String methodName) {
        if (!hasFlag(Flags.DontValidatePreferences)) {
            def set = [] as Set<String>;
            preferences.dynamicPages.each {
                assert it.generationMethodName: "Page ${it.readName()} was not produed by a separate method. This is not supported."
                assert !set.contains(
                        it.generationMethodName): "Method ${methodName} contains more than one dynamicPage() invocation: ${preferences.dynamicPages.findAll { it.generationMethodName == methodName }.collect { it.readName() }}"
                set << it.generationMethodName
            }
        }
    }
}
