package biocomp.hubitatCi.device.metadata


import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

/**
 * Information about 'input' in Device.
 */
@TypeChecked
@TupleConstructor
class DeviceInput {
    final Map unnamedOptions
    final Map options

    String readName()
    {
        return unnamedOptions.name != null ? unnamedOptions.name : options.name
    }

    String readType()
    {
        return unnamedOptions.type != null ? unnamedOptions.type: options.type
    }

    @Override
    String toString()
    {
        return "input(unnamed options: ${unnamedOptions}, options = ${options})"
    }

    def makeInputObject(def userProvidedValue)
    {
        return userProvidedValue
    }
}
