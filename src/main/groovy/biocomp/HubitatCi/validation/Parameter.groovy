package biocomp.hubitatCi.validation

import groovy.transform.TupleConstructor

@TupleConstructor
class Parameter
{
    final String name
    final boolean required
    final Closure validator
}