package biocomp.hubitatCi.app.preferences

import biocomp.hubitatCi.capabilities.Capabilities
import biocomp.hubitatCi.validation.Flags
import biocomp.hubitatCi.validation.NamedParametersValidator
import groovy.transform.CompileStatic

@CompileStatic
class Input {
    final Map unnamedOptions
    final Map options
    final EnumSet<Flags> validationFlags
    final IInputType typeWrapper

    Input(Map unnamedOptions, Map options, EnumSet<Flags> validationFlags) {
        this.unnamedOptions = unnamedOptions
        this.options = options
        this.validationFlags = validationFlags

        this.typeWrapper = validateAndInitType()
    }

    String readName()
    {
        return unnamedOptions.name ? unnamedOptions.name : options?.name
    }

    String readType()
    {
        return unnamedOptions.type ? unnamedOptions.type : options?.type
    }

    @CompileStatic
    static boolean isCapabilityType(String type) {
        return type =~ /capability\.[a-zA-Z0-9._]+/
    }

    @CompileStatic
    static Class findCapabilityFromTypeString(String type)
    {
        assert isCapabilityType(type), "Call this method only if input type points to capability. Current value: '${type}'"
        return (Class)Capabilities.capabilitiesByDeviceSelector.get(type.substring(11))
    }

    @Override
    String toString()
    {
        return "input(options: ${options}, unnamedOptions: ${unnamedOptions})"
    }

    @CompileStatic
    def makeInputObject(def userProvidedValue)
    {
        return typeWrapper.makeInputObject(readName(), readType(), userProvidedValue)
    }

    @CompileStatic
    def makeInputObject()
    {
        return typeWrapper.makeInputObject(readName(), readType())
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

    private static final HashMap<String, IInputType> validStaticInputTypes = [
            bool: new BooleanInputType(),
            //"boolean",
            decimal: new NumberInputType(),
            email: new TextInputType(),
            enum: new TextInputType(), // Todo: make enum input type?
            hub: new TextInputType(),
            icon: new TextInputType(),
            number: new NumberInputType(),
            password: new TextInputType(),
            phone: new NumberInputType(),
            time: new NumberInputType(),
            text: new TextInputType()
    ] as HashMap<String, IInputType>

    private IInputType validateAndInitType()
    {
        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            inputOptionsValidator.validate(
                    toString(),
                    unnamedOptions,
                    options,
                    validationFlags)

            return validateInputType()
        }

        return new UnvalidatedInputType();
    }

    private IInputType validateInputType()
    {
        final def inputType = readType()
        final def foundStaticType = validStaticInputTypes.get(inputType)
        if (foundStaticType)
        {
            return foundStaticType
        }

        if (Input.isCapabilityType(inputType))
        {
            final def foundCapability = Input.findCapabilityFromTypeString(inputType)
            if (foundCapability)
            {
                return new CapabilityInputType(foundCapability)
            }
            else
            {
                assert false : "Input ${this}'s capability '${inputType}' is not supported. Supported capabilities: ${Capabilities.capabilitiesByDeviceSelector.keySet()}"
            }
        }

        if (inputType =~ /device\.[a-zA-Z0-9._]+/)
        {
            return new DeviceInputType()
        }

        assert false : "Input ${this}'s type ${inputType} is not supported. Valid types are: ${validStaticInputTypes} + 'capability.yourCapabilityName' + 'device.yourDeviceName'"
    }


}
