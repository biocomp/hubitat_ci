package biocomp.hubitatCi.deviceMetadata

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

import java.lang.reflect.Method

@TypeChecked
@TupleConstructor
class Command
{
    String name
    List<String> parameterTypes
    MetaMethod  method

    @Override
    String toString()
    {
        return "command(name = ${name}, parameterTypes = ${parameterTypes})"
    }
}

