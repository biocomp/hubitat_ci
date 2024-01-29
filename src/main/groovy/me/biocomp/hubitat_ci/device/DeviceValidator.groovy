package me.biocomp.hubitat_ci.device

import me.biocomp.hubitat_ci.capabilities.Capabilities
import me.biocomp.hubitat_ci.device.metadata.*
import me.biocomp.hubitat_ci.util.RequreParseCompilationCusomizer
import me.biocomp.hubitat_ci.validation.InputCommon
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import me.biocomp.hubitat_ci.validation.ValidatorBase
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import org.codehaus.groovy.control.customizers.CompilationCustomizer

import java.lang.reflect.Method

@TypeChecked
class DeviceValidator extends
        ValidatorBase
{
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
        def scriptFileText = scriptFile.getText('UTF-8')
        return parseScript(scriptFileText)
    }

    HubitatDeviceScript parseScript(String scriptText) {
        scriptText = super.replaceDateReferences(scriptText)
        return constructParser(HubitatDeviceScript, makeCustomizers()).parse(scriptText, "Script1") as HubitatDeviceScript
    }

    /**
     * Add device-specific compilation customizers
     */
    private List<CompilationCustomizer> makeCustomizers()
    {
        if (!flags.contains(Flags.DontRequireParseMethodInDevice)) {
            return [new RequreParseCompilationCusomizer() as CompilationCustomizer]
        }
        else {
            return []
        }
    }

    private static final NamedParametersValidator definitionOptionsValidator = NamedParametersValidator.make {
        stringParameter("name", required(), mustNotBeEmpty())
        stringParameter("namespace", required(), mustNotBeEmpty())
        stringParameter("author", required(), mustNotBeEmpty())

        // Undocumented, but mentioned here:
        // https://community.hubitat.com/t/import-code-from-website-driver-code/10069/4
        stringParameter("importUrl", notRequired(), mustNotBeEmpty())
    }

    private static String printMethod(Method m)
    {
        def params = m.parameters.collect{
            "${it.type.simpleName} ${it.name}"
        }.join(", ")

        return "${m.name}(${params})"
    }

    void validateDefinition(Definition definition, MetaClass scriptMetaClass) {
        definitionOptionsValidator.validate("definition(${definition.options})", definition.options, flags)

        if (!hasFlag(Flags.DontRequireCapabilityImplementationMethods)) {
            def scriptActualMethods = scriptMetaClass.theClass.methods.findAll {
                it.declaringClass == scriptMetaClass.theClass
            }.collectEntries {
                [it.name, it]
            }

            definition.capabilities.each {
                def capabilityClass = Capabilities.findCapabilityByName(it)

                def missingMethods = capabilityClass.methods.findAll {
                    if (it.declaringClass == capabilityClass) {
                        //println "### Expecting method ${it.name}. Map ${scriptActualMethods.containsKey(it.name) ? "has it" : "doesn't have it" }"

                        return !scriptActualMethods.containsKey(it.name)
                    }
                }.collect { printMethod(it) }

                assert !missingMethods, "capability '${capabilityClass.simpleName}' method ${missingMethods} not implemented"
            }
        }
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
        if (hasFlag(Flags.DontValidateMetadata)) {
            return
        }

        assert deviceMetadataReader.hasMetadataCall: "Device does not have 'metadata' call"

        if (hasFlag(Flags.DontValidateDefinition)) {
            return
        }

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

        def duplicateCommands = findDuplicates(deviceMetadataReader.producedDefinition.commands.collect { it.toString() })
        assert duplicateCommands.size() == 0 : "Commands ${duplicateCommands} are duplicate, this is not useful."
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

        return pickedMethod
    }

    void validateCommand(Command command) {
        if (!hasFlag(Flags.AllowMissingCommandMethod)) {
            assert command.method != null: "Command ${command} does not have a method with matching signature in current script."
        }
    }

    private static final NamedParametersValidator fingerprintOptionsValidator = NamedParametersValidator.make {
        stringParameter("type", notRequired(), canBeEmpty())
        stringParameter("mfr", notRequired(), canBeEmpty())
        stringParameter("prod", notRequired(), canBeEmpty())
        stringParameter("model", notRequired(), canBeEmpty())
        stringParameter("cc", notRequired(), canBeEmpty())
        stringParameter("ccOut", notRequired(), canBeEmpty())
        stringParameter("sec", notRequired(), canBeEmpty())
        stringParameter("secOut", notRequired(), canBeEmpty())
        stringParameter("ff", notRequired(), canBeEmpty())
        stringParameter("ui", notRequired(), canBeEmpty())
        stringParameter("deviceJoinName", notRequired(), canBeEmpty())
        stringParameter("profileId", notRequired(), canBeEmpty())
        stringParameter("inClusters", notRequired(), canBeEmpty())
        stringParameter("outClusters", notRequired(), canBeEmpty())
        stringParameter("manufacturer", notRequired(), canBeEmpty())
        stringParameter("deviceId", notRequired(), canBeEmpty())
    }

    void validateFingerprint(Map fingerprint)
    {
        fingerprintOptionsValidator.validate("fingerprint(${fingerprint})", fingerprint, flags)
    }

    void validateSection(String name)
    {
        if (!hasFlag(Flags.AllowSectionsInDevicePreferences)) {
            assert false, "section('${name}') was used in preferences(), and while the method exists, it's not documented anywhere. Add Flags.AllowSectionsInDevicePreferences to allow sections."
        }
    }
}
