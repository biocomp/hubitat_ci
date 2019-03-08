package biocomp.hubitatCi.deviceMetadata

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TupleConstructor
@TypeChecked
class DeviceInput {
    final Map options
    final String name
    final String type

    String getName()
    {
        return name != null ? name : options.name
    }

    String getType()
    {
        return type != null ? type : options.type
    }

    @Override
    String toString()
    {
        return "input('${getName()}', ${getType()}, options = ${options})"
    }
}
