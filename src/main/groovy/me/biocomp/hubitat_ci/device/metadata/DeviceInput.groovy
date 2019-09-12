package me.biocomp.hubitat_ci.device.metadata

import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.validation.BooleanInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.IInputValueFactory
import me.biocomp.hubitat_ci.validation.InputCommon
import me.biocomp.hubitat_ci.validation.NamedParametersValidator
import me.biocomp.hubitat_ci.validation.NumberInputValueFactory
import me.biocomp.hubitat_ci.validation.TextInputValueFactory

/**
 * Information about 'input' in Device.
 */
@TypeChecked
class DeviceInput extends InputCommon {
    private static final HashMap<String, IInputValueFactory> validStaticInputTypes =
            [bool    : new BooleanInputValueFactory(),
             decimal : new NumberInputValueFactory(),
             email   : new TextInputValueFactory(),
             enum    : new TextInputValueFactory(), // Todo: make enum input type?
             number  : new NumberInputValueFactory(),
             password: new TextInputValueFactory(),
             phone   : new NumberInputValueFactory(),
             time    : new TextInputValueFactory(),
             text    : new TextInputValueFactory()] as HashMap<String, IInputValueFactory>

    private static final NamedParametersValidator inputOptionsValidator = NamedParametersValidator.make {
        stringParameter("name", required(), canBeEmpty(), [Flags.DontValidateDeviceInputName])
        enumStringParameter("type", required(), [
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
        stringParameter("title", notRequired(), canBeEmpty())
        stringParameter("description", notRequired(), canBeEmpty())
        objParameter("defaultValue", notRequired(), canBeNull())
        boolParameter("required", notRequired())
        boolParameter("displayDuringSetup", notRequired())
        numericRangeParameter("range", notRequired())
        listOfStringsParameter("options", notRequired())
    }

    DeviceInput(Map unnamedOptions, Map options, EnumSet<Flags> validationFlags) {
        super(unnamedOptions, options, validationFlags, validStaticInputTypes)
    }

    @Override
    boolean validateInputBasics()
    {
        if (!validationFlags.contains(Flags.DontValidatePreferences)) {
            inputOptionsValidator.validate(toString(),
                    unnamedOptions,
                    options,
                    validationFlags,
                    validationFlags.contains(Flags.AllowMissingDeviceInputNameOrType) ? EnumSet.of(
                            NamedParametersValidator.ValidatorOption.IgnoreMissingMandatoryInputs) : EnumSet.noneOf(
                            NamedParametersValidator.ValidatorOption))

            return true
        }

        return false
    }

    @Override
    IInputValueFactory typeNotFoundInTypeTable(String inputType) {
        assert false: "Input ${this}'s type ${inputType} is not supported. Valid types are: ${validStaticInputTypes}"
    }
}
