package biocomp.hubitatCi.app.preferences

import groovy.transform.TupleConstructor

/**
 * Information and helper methods around input type
 */
interface IInputType
{
    /**
        Makes an instance of input object that can be used in the script.
        In this case, user has provided a value, and it will be somehow incorporated in that object (or completely replaces it).
     */
    def makeInputObject(String inputName, String inputType, def userProvidedValue)

    /**
     * Makes an instance of input object that can be used in the script.
     */
    def makeInputObject(String inputName, String inputType)
}

/**
 * Any text input type like 'email', 'text' and so on.
 */
class TextInputType implements IInputType
{
    final String inputName

    @Override
    def makeInputObject(String inputName, String inputType, Object userProvidedValue) {
        return userProvidedValue
    }

    @Override
    def makeInputObject(String inputName, String inputType) {
        return "Input '${inputName}' of type '${inputType}'"
    }
}

/**
 * Any text input type like 'email', 'text' and so on.
 */
class BooleanInputType implements IInputType
{
    final String inputName

    @Override
    def makeInputObject(String inputName, String inputType, Object userProvidedValue) {
        return userProvidedValue
    }

    @Override
    def makeInputObject(String inputName, String inputType) {
        return true
    }
}

/**
 * Any text input type like 'email', 'text' and so on.
 */
class NumberInputType implements IInputType
{
    @Override
    def makeInputObject(String inputName, String inputType, Object userProvidedValue) {
        return userProvidedValue
    }

    @Override
    def makeInputObject(String inputName, String inputType) {
        return 42
    }
}

/**
 * When input validation is disabled, this type wrapper is used.
 */
class UnvalidatedInputType implements IInputType
{
    @Override
    def makeInputObject(String inputName, String inputType, Object userProvidedValue) {
        return userProvidedValue
    }

    @Override
    def makeInputObject(String inputName, String inputType) {
        return "Input '${inputName}' type '${inputType}' was not validated, so this generic string is used as mock value"
    }
}

