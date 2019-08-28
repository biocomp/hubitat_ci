package me.biocomp.hubitat_ci.device.metadata

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

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

