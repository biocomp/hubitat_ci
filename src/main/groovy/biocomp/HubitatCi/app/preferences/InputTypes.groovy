package biocomp.hubitatCi.app.preferences

import groovy.transform.PackageScope

/**
 * Information and helper methods around input type
 */
interface IInputType
{
    /**
        Makes an instance of input object that can be used in the script.
        In this case, user has provided a value, and it will be somehow incorporated in that object (or completely replaces it).

        @param userProvidedAndDefaultValues - map of 0-2 elements with possible values:
         <br/> userProvidedValue - value that user provided as mock (possibly null)
         <br/> defaultValue - value that was specified as default in input() (possibly null)
     */
    def makeInputObject(String inputName, String inputType, Map<String, Object> userProvidedAndDefaultValues)
}

@PackageScope
class InputImplCommon
{
    static def returnUserOrDefaultOrCustomValue(Map<String, Object> userProvidedAndDefaultValues, Object customValue)
    {
        if (userProvidedAndDefaultValues.containsKey('userProvidedValue'))
        {
            return userProvidedAndDefaultValues.userProvidedValue
        }
        else if (userProvidedAndDefaultValues.containsKey('defaultValue'))
        {
            return userProvidedAndDefaultValues.defaultValue
        }
        else
        {
            return customValue
        }
    }
}

/**
 * Any text input type like 'email', 'text' and so on.
 */
class TextInputType implements IInputType
{
    final String inputName

    @Override
    def makeInputObject(String inputName, String inputType, Map<String, Object> userProvidedAndDefaultValues) {
        return InputImplCommon.returnUserOrDefaultOrCustomValue(
                userProvidedAndDefaultValues,
                "Input '${inputName}' of type '${inputType}'")
    }
}

/**
 * Any text input type like 'email', 'text' and so on.
 */
class BooleanInputType implements IInputType
{
    final String inputName

    @Override
    def makeInputObject(String inputName, String inputType, Map<String, Object> userProvidedAndDefaultValues) {
        return InputImplCommon.returnUserOrDefaultOrCustomValue(userProvidedAndDefaultValues, new Boolean(true))
    }
}

/**
 * Any text input type like 'email', 'text' and so on.
 */
class NumberInputType implements IInputType
{
    @Override
    def makeInputObject(String inputName, String inputType, Map<String, Object> userProvidedAndDefaultValues) {
        return InputImplCommon.returnUserOrDefaultOrCustomValue(userProvidedAndDefaultValues, new Integer(0))
    }
}

/**
 * When input validation is disabled, this type wrapper is used.
 */
class UnvalidatedInputType implements IInputType
{
    @Override
    def makeInputObject(String inputName, String inputType, Map<String, Object> userProvidedAndDefaultValues) {
        return InputImplCommon.returnUserOrDefaultOrCustomValue(
                userProvidedAndDefaultValues,
                "Input '${inputName}' type '${inputType}' was not validated, so this generic string is used as mock value")
    }
}

