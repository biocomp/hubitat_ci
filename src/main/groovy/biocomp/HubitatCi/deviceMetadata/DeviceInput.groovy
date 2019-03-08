package biocomp.hubitatCi.deviceMetadata

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TypeChecked
class DeviceInput {
    DeviceInput(
        Map options,
        String name,
        String type)
    {
        this.options = options
        this.name = name
        this.type = type
        this.mergedOptions = options.clone() as Map

        if (this.name != null) {
            this.mergedOptions.name = name
        }

        if (this.type != null) {
            this.mergedOptions.type = type
        }
    }

    final Map options
    final Map mergedOptions
    final String name
    final String type

    String getName()
    {
        return mergedOptions.name
    }

    String getType()
    {
        return mergedOptions.type
    }

    @Override
    String toString()
    {
        return "input('${getName()}', ${getType()}, options = ${options})"
    }

    Map getMergedOptions()
    {
        return mergedOptions
    }
}
