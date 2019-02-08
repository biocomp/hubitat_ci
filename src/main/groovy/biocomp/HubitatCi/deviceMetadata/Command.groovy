package biocomp.hubitatCi.deviceMetadata

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TypeChecked
@TupleConstructor
class Command
{
    String name
    List<String> parameterTypes

    @Override
    String toString()
    {
        return "command(name = ${name}, parameterTypes = ${parameterTypes})"
    }
}

