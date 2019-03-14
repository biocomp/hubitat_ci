package biocomp.hubitatCi.validation

import biocomp.hubitatCi.*
import biocomp.hubitatCi.apppreferences.*
import biocomp.hubitatCi.capabilities.Capabilities
import biocomp.hubitatCi.deviceMetadata.Attribute
import biocomp.hubitatCi.deviceMetadata.Command
import biocomp.hubitatCi.deviceMetadata.Definition
import biocomp.hubitatCi.deviceMetadata.DeviceInput
import biocomp.hubitatCi.deviceMetadata.DeviceMetadataReader
import groovy.json.JsonBuilder
import groovy.time.TimeCategory
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.control.customizers.SourceAwareCustomizer
import sun.util.calendar.ZoneInfo

import java.lang.reflect.Method
import java.text.DecimalFormat
import java.text.SimpleDateFormat

@TypeChecked
class DeviceValidator extends
        ValidatorBase
{
    //    private static final NamedParametersValidator preferencesValidatorWithOauth = NamedParametersValidator.make {
    //        stringParameter(name: "oauthPage", required: true)
    //    }
    //
    //    private static final NamedParametersValidator preferencesValidatorNoOauth = NamedParametersValidator.make {
    //    }
    //
    //    private static final NamedParametersValidator inputOptionsValidator = NamedParametersValidator.make {
    //        boolParameter(name: "capitalization")
    //        objParameter(name: "defaultValue")
    //        stringParameter(name: "name", required: true)
    //        stringParameter(name: "title")
    //        stringParameter(name: "description")
    //        boolParameter(name: "multiple")
    //        numericRangeParameter(name: "range")
    //        boolParameter(name: "required")
    //        boolParameter(name: "submitOnChange")
    //        listOfStringsParameter(name: "options")
    //        stringParameter(name: "type", required: true)
    //        boolParameter(name: "hideWhenEmpty")
    //    }
    //
    //    private static final HashSet<String> validStaticInputTypes = [
    //        "bool",
    //        //"bolean",
    //        "decimal",
    //        "email",
    //        "enum",
    //        "hub",
    //        "icon",
    //        "number",
    //        "password",
    //        "phone",
    //        "time",
    //        "text"
    //    ] as HashSet


    DeviceValidator(
            EnumSet<Flags> setOfFlags = EnumSet.noneOf(Flags), List<Class> extraAllowedClasses = [],
            List<String> extraAllowedExpressions = [])
    {
        super(setOfFlags, extraAllowedClasses, extraAllowedExpressions)
    }

    DeviceValidator(
            List<Flags> listOfFlags, List<Class> extraAllowedClasses = [], List<String> extraAllowedExpressions = [])
    {
        super(listOfFlags, extraAllowedClasses, extraAllowedExpressions)
    }

    HubitatDeviceScript parseScript(File scriptFile) {
        return constructParser(HubitatDeviceScript).parse(scriptFile) as HubitatDeviceScript
    }

    HubitatDeviceScript parseScript(String scriptText) {
        return constructParser(HubitatDeviceScript).parse(scriptText) as HubitatDeviceScript
    }

    //
    //    void validateAfterRun(AppDefinitionReader definitionReader, AppPreferencesReader preferencesReader, AppMappingsReader mappingsReader)
    //    {
    //        def preferences = preferencesReader.getProducedPreferences() // Just to trigger validation
    //        def definitions = definitionReader.getDefinitions() // Trigger definition validation
    //
    //        if (preferences && !hasFlag(Flags.DontValidatePreferences))
    //        {
    //            validatePreferences(preferences, false, definitions?.oauth as boolean)
    //        }
    //    }
    //
    //
    //    /**
    //     * Run validations after all pages and sections were added*/
    //    void validatePreferences(Preferences preferences, boolean okEmptyIfRegisteredDynamicPages, boolean oauthEnabled) {
    //        if (preferences.hasSpecialSinglePage()) {
    //            preferences.specialSinglePage.validate(this)
    //        }
    //
    //        if (!hasFlag(Flags.DontValidatePreferences)) {
    //            // Needs to have pages
    //            if (!okEmptyIfRegisteredDynamicPages || preferences.dynamicRegistrations.size() == 0) {
    //                assert (preferences.pages.size() != 0 || preferences.dynamicPages.size() != 0): "preferences() has to have pages (got ${preferences.pages.size()}), dynamic pages (got ${preferences.dynamicPages.size()})"
    //            }
    //
    //            // Page names must be unique
    //            def allNames = preferences.pages.collect { it.readName() } + preferences.dynamicPages.collect { it.readName() }
    //            def uniqueNames = [] as HashSet
    //            allNames.each {
    //                assert !uniqueNames.contains(
    //                        it): "Page '${it}' is not a unique page name. Page names must be unique. Please fix that. All page names: ${allNames}"
    //                uniqueNames << it
    //            }
    //
    //            // Multi-page app needs explicit 'install' (otherwise you won't be able to install this)
    //            if (!preferences.hasSpecialSinglePage() && !hasFlag(Flags.AllowMissingInstall)) {
    //                assert preferences.pages.findAll { it.options.install == true } + preferences.dynamicPages.findAll {
    //                    it.options.install == true
    //                }: "There is no 'install: true' option specified on any page (and it's not a special one-page app). This means you can't install the app. Please add the button to a page."
    //            }
    //
    //            // Pages must be reachable
    //            validatePagesAreReachable(preferences)
    //
    //            // Validate oauth page
    //            validateOauth(oauthEnabled, preferences, uniqueNames)
    //        }
    //    }
    //
    //    private void validateOauth(boolean oauthEnabled, Preferences preferences, HashSet<String> uniqueNames)
    //    {
    //        if (oauthEnabled && !preferences.hasSpecialSinglePage())
    //        {
    //            if (!hasFlag(Flags.AllowMissingOAuthPage)) {
    //                def oauthPageName = preferences.options.oauthPage as String
    //                preferencesValidatorWithOauth.validate("preferences()", preferences.options, this)
    //                assert uniqueNames.contains(oauthPageName): "preferences(): oauthPage = '${oauthPageName}' does not point to a valid page"
    //
    //                assert preferences.allPages[0].readName() == oauthPageName: "Page '${oauthPageName}' pointed by 'oauthPage' must be a first page, but first page is ${preferences.allPages[0]}"
    //                assert !preferences.allPages[0].isDynamicPage(): "Page '${oauthPageName}' pointed by 'oauthPage' must be static, not a dynamic page"
    //            }
    //        }
    //        else
    //        {
    //            preferencesValidatorNoOauth.validate("preferences()", preferences.options, this)
    //        }
    //    }
    //
    //
    //    private static Page getPageOrAssert(Page currentPage, String referredPage, HashMap<String, Page> allPages)
    //    {
    //        def foundPage = allPages[referredPage]
    //        assert foundPage: "${this}: Page's name '${referredPage}' referred by ${currentPage} is not valid. Valid page names are: ${allPages.keySet()}"
    //        return foundPage
    //    }
    //
    //    private static void addReachablePages(Page p, HashMap<String, Page> allPages, HashSet<String> reachablePages)
    //    {
    //        if (!reachablePages.contains(p.readName())) {
    //            reachablePages << p.readName()
    //
    //            p.sections.each {
    //                it.children.each {
    //                    if (it instanceof HRef) {
    //                        def referredPage = it.readPage()
    //                        if (referredPage) {
    //                            addReachablePages(getPageOrAssert(p, referredPage, allPages), allPages, reachablePages)
    //                        }
    //                    }
    //                }
    //            }
    //
    //            if (p.options?.nextPage) {
    //                addReachablePages(getPageOrAssert(p, p.options.nextPage as String, allPages), allPages, reachablePages)
    //            }
    //        }
    //    }
    //
    //    private void validatePagesAreReachable(Preferences preferences)
    //    {
    //        if (!hasFlag(Flags.AllowUnreachablePages)) {
    //            def reachablePages = [] as HashSet<String>
    //
    //            def pagesCombined = (preferences.pages + preferences.dynamicPages)
    //
    //            if (!pagesCombined.isEmpty()) {
    //                def allPages = [] as HashMap<String, Page>;
    //                (pagesCombined).each {
    //                    allPages[it.readName()] = it
    //                }
    //
    //
    //                addReachablePages(pagesCombined[0], allPages, reachablePages);
    //
    //                def unreachablePages = allPages.keySet() - reachablePages
    //                assert !unreachablePages: "${this}: pages ${unreachablePages} are not reachable via 'href' or 'nextPage', and thus don't make sense to have";
    //            }
    //        }
    //    }
    //
    //    void validateInput(Input input)
    //    {
    //        if (!hasFlag(Flags.DontValidatePreferences)) {
    //            inputOptionsValidator.validate(
    //                    input.toString(),
    //                    input.unnamedOptions,
    //                    input.options,
    //                    this)
    //
    //            validateInputType(input)
    //        }
    //    }
    //
    //    private void validateInputType(Input input)
    //    {
    //        final def inputType = input.readType()
    //        if (validStaticInputTypes.contains(inputType))
    //        {
    //            return
    //        }
    //
    //        if (inputType =~ /capability\.[a-zA-Z0-9._]+/)
    //        {
    //            if (Capabilities.capabilitiesByDeviceSelector.containsKey(inputType.substring(11)))
    //            {
    //                return
    //            }
    //            else
    //            {
    //                assert false : "Input ${input}'s capability '${inputType}' is not supported. Supported capabilities: ${Capabilities.capabilitiesByDeviceSelector.keySet()}"
    //            }
    //        }
    //
    //        if (inputType =~ /device\.[a-zA-Z0-9._]+/)
    //        {
    //            return
    //        }
    //
    //        assert false : "Input ${input}'s type ${inputType} is not supported. Valid types are: ${validStaticInputTypes} + 'device.yourDeviceName'"
    //    }
    //
    //    void validateSingleDynamicPageFor(Preferences preferences, String methodName) {
    //        if (!hasFlag(Flags.DontValidatePreferences)) {
    //            def set = [] as Set<String>;
    //            preferences.dynamicPages.each {
    //                assert it.generationMethodName: "Page ${it.readName()} was not produed by a separate method. This is not supported."
    //                assert !set.contains(
    //                        it.generationMethodName): "Method ${methodName} contains more than one dynamicPage() invocation: ${preferences.dynamicPages.findAll { it.generationMethodName == methodName }.collect { it.readName() }}"
    //                set << it.generationMethodName
    //            }
    //        }
    //    }

    private static final NamedParametersValidator definitionOptionsValidator = NamedParametersValidator.make {
        stringParameter(name: "name", required: true)
        stringParameter(name: "namespace")
        stringParameter(name: "author")
    }

    void validateDefinition(Definition definition) {
        definitionOptionsValidator.validate("definition(${definition.options})", definition.options, this);
    }

    void validateCapability(String capabilityName) {
        assert (Capabilities.findCapabilityByName(
                capabilityName)): "capability '${capabilityName}' is not supported. Valid capabilities are: ${Capabilities.capabilitiesByPrettyName.keySet() + Capabilities.capabilitiesByDriverDefinition.keySet()}."
    }

    void validateAttribute(Attribute attribute) {
        assert attribute.name: "Attribute ${attribute} doesn't have a name."
        assert attribute.type: "Attribute ${attribute} doesn't have a type."

        assert attribute.type == 'string' || attribute.type == 'enum' || attribute.type == 'number': "Attribute ${attribute}'s type '${attribute.type}' is not supported."
        if (attribute.type == 'enum') {
            assert attribute.possibleValues != null && attribute.possibleValues.size() != 0: "Attribute ${attribute} with 'enum' type must have possible values specified."
        } else {
            assert attribute.possibleValues == null: "Attribute ${attribute} is not of type 'enum', it can't have possible values specified."
        }
    }

    static private <T> List<T> findDuplicates(List<T> inCollection) {
        Set<T> existing = []
        List<T> duplicates = []
        inCollection.each {
            if (existing.contains(it)) {
                duplicates.add(it)
            } else {
                existing.add(it)
            }
        }

        return duplicates
    }

    @TypeChecked(TypeCheckingMode.SKIP)
    void validateAfterRun(DeviceMetadataReader deviceMetadataReader) {
        if (!hasFlag(Flags.DontValidateDefinition)) {
            assert deviceMetadataReader.producedDefinition != null: "Device must have definition()"

            if (!hasFlag(Flags.DontValidateAttributes)) {
                def duplicateAttributes = findDuplicates(
                        deviceMetadataReader.producedDefinition.attributes.collect { it.name })
                assert duplicateAttributes.size() == 0: "Attributes ${duplicateAttributes} are duplicate, this is not useful."
            }

            if (!hasFlag(Flags.DontValidateCapabilities)) {
                assert deviceMetadataReader.producedDefinition.capabilities.size() != 0: "Device must have at least one capability"

                def duplicateCapabilities = findDuplicates(deviceMetadataReader.producedDefinition.capabilities.collect {
                    Capabilities.findCapabilityByName(it)
                })

                assert duplicateCapabilities.size() == 0: "Capabilities ${duplicateCapabilities} are duplicate, this is not useful."
            }

            //{
            def duplicateCommands = findDuplicates(deviceMetadataReader.producedDefinition.commands.collect { it.toString() })
            assert duplicateCommands.size() == 0 : "Commands ${duplicateCommands} are duplicate, this is not useful."
        }
    }

    private static final HashMap<String, Class> supportedCommandArgumentTypes = ['number': Integer, 'string': String] as HashMap

    private static Class parameterTypeToClass(Command command, String typeName) {
        assert supportedCommandArgumentTypes.containsKey(typeName) : "${command}: Argument type '${typeName}' is not supported. Supported types are: ${supportedCommandArgumentTypes}"
        return supportedCommandArgumentTypes.get(typeName)
    }

    @CompileStatic
    MetaMethod findMethodForCommand(MetaClass scriptMetaClass, String name, List<String> parameterTypes)
    {
        def command = new Command(name, parameterTypes, null)

        MetaMethod pickedMethod = scriptMetaClass.pickMethod(name,
                parameterTypes.collect { parameterTypeToClass(command, it) } as Class[])

        // MetaClass.pickMethod will ignore 1-argument parameterTypes, if method takes 0 arguments, so add extra validation.
        if (pickedMethod != null && pickedMethod.parameterTypes.size() != parameterTypes.size() && !hasFlag(Flags.AllowCommandDefinitionWithNoArgsMatchAnyCommandWithSameName)) {
            return null
        }
//
//        if (pickedMethod == null && parameterTypes.size() == 0 && ) {
//            pickedMethod = scriptMetaClass.metaMethods.find{ it.name == name }
//        }

        return pickedMethod
    }

    void validateCommand(Command command) {
        if (!hasFlag(Flags.AllowMissingCommandMethod)) {
            assert command.method != null: "Command ${command} does not have a method with matching signature in current script."
        }
    }

    private static final NamedParametersValidator fingerprintOptionsValidator = NamedParametersValidator.make {
        stringParameter(name: "type")
        stringParameter(name: "mfr")
        stringParameter(name: "prod")
        stringParameter(name: "model")
        stringParameter(name: "cc")
        stringParameter(name: "ccOut")
        stringParameter(name: "sec")
        stringParameter(name: "secOut")
        stringParameter(name: "ff")
        stringParameter(name: "ui")
        stringParameter(name: "deviceJoinName")
        stringParameter(name: "profileId")
        stringParameter(name: "inClusters")
        stringParameter(name: "outClusters")
        stringParameter(name: "manufacturer")
        stringParameter(name: "deviceId")
    }

    void validateFingerprint(Map fingerprint)
    {
        fingerprintOptionsValidator.validate("fingerprint(${fingerprint})", fingerprint, this)
    }

    private static final NamedParametersValidator inputOptionsValidator = NamedParametersValidator.make {
        stringParameter(name: "name", canBeEmpty: true, required: true, dontValidateIfFlags: [Flags.DontValidateDeviceInputName])
        enumStringParameter(name: "type", required: true, values: [
            'bool',
            'decimal',
            'email',
            'enum',
            'number',
            'password',
            'phone',
            'time',
            'text',
            'paragraph' // TODO: verify if it's really supported
        ])
        stringParameter(name: "title")
        stringParameter(name: "description")
        objParameter(name: "defaultValue")
        boolParameter(name: "required")
        boolParameter(name: "displayDuringSetup")
        numericRangeParameter(name: "range")
        listOfStringsParameter(name: "options")
    }

    void validateInput(DeviceInput input)
    {
        inputOptionsValidator.validate(
                input as String,
                input.unnamedOptions,
                input.options,
                this,
                hasFlag(Flags.AllowMissingDeviceInputNameOrType)
                    ? EnumSet.of(NamedParametersValidator.ValidatorOption.IgnoreMissingMandatoryInputs)
                    : EnumSet.noneOf(NamedParametersValidator.ValidatorOption))
    }

    void validateSection(String name)
    {
        if (!hasFlag(Flags.AllowSectionsInDevicePreferences)) {
            assert false, "section('${name}') was used in preferences(), and while the method exists, it's not documented anywhere"
        }
    }
}
