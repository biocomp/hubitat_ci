package biocomp.hubitatCi.app


import biocomp.hubitatCi.app.preferences.HRef
import biocomp.hubitatCi.app.preferences.Input
import biocomp.hubitatCi.app.preferences.Page
import biocomp.hubitatCi.app.preferences.Preferences
import biocomp.hubitatCi.capabilities.Capabilities
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.NamedParametersValidator
import biocomp.hubitatCi.validation.ValidatorBase
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

@TypeChecked
class AppValidator extends ValidatorBase{

    private static final NamedParametersValidator preferencesValidatorWithOauth = NamedParametersValidator.make {
        stringParameter("oauthPage", required(), mustNotBeEmpty())
    }

    private static final NamedParametersValidator preferencesValidatorNoOauth = NamedParametersValidator.make {
    }

    private static final NamedParametersValidator inputOptionsValidator = NamedParametersValidator.make {
        boolParameter("capitalization", notRequired())
        objParameter("defaultValue", notRequired(), canBeNull())
        stringParameter("name", required(), mustNotBeEmpty())
        stringParameter("title", notRequired(), mustNotBeEmpty())
        stringParameter("description", notRequired(), mustNotBeEmpty())
        boolParameter("multiple", notRequired())
        numericRangeParameter("range", notRequired())
        boolParameter("required", notRequired())
        boolParameter("submitOnChange", notRequired())
        listOfStringsParameter("options", notRequired())
        stringParameter("type", required(), mustNotBeEmpty())
        boolParameter("hideWhenEmpty", notRequired())
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

    @CompileStatic
    void validateAfterRun(AppData data)
    {
        def preferences = data.preferences

        if (preferences && !hasFlag(Flags.DontValidatePreferences)) {
            validatePreferences(preferences, false, data.definitions?.oauth as boolean)
        }
    }

    /**
     * Run validations after all pages and sections were added
     * */
    // @CompileStatic (compiler crashes with: def allNames = preferences.pages.collect { it.readName() } + preferences.dynamicPages.collect { it.readName() })
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
            def uniqueNames = new HashSet<String>()
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

    @CompileStatic
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

    @CompileStatic
    private static Page getPageOrAssert(Page currentPage, String referredPage, HashMap<String, Page> allPages)
    {
        def foundPage = allPages[referredPage]
        assert foundPage: "${this}: Page's name '${referredPage}' referred by ${currentPage} is not valid. Valid page names are: ${allPages.keySet()}"
        return foundPage
    }

    // @CompileStatic (compiler crashes)
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

    // @CompileStatic (compiler crashes on def pagesCombined = preferences.pages + preferences.dynamicPages)
    private void validatePagesAreReachable(Preferences preferences)
    {
        if (!hasFlag(Flags.AllowUnreachablePages)) {
            def reachablePages = [] as HashSet<String>

            List<Page> pagesCombined = preferences.pages + preferences.dynamicPages

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

        if (Input.isCapabilityType(inputType))
        {
            if (Input.findCapabilityFromTypeString(inputType))
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
