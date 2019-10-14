package me.biocomp.hubitat_ci.validation

import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.util.NullableOptional

@CompileStatic
class DefaultAndUserValues
{
    static DefaultAndUserValues empty()
    {
        return new DefaultAndUserValues()
    }

    static DefaultAndUserValues defaultValueOnly(NullableOptional defaultValue)
    {
        return new DefaultAndUserValues(defaultValue)
    }

    static DefaultAndUserValues bothValues(NullableOptional defaultValue, NullableOptional userProvidedValue)
    {
        return new DefaultAndUserValues(defaultValue, userProvidedValue)
    }

    private DefaultAndUserValues() {}

    private DefaultAndUserValues(NullableOptional defaultValue)
    {
        this.@defaultValue = defaultValue
    }

    private DefaultAndUserValues(NullableOptional defaultValue, NullableOptional userProvidedValue)
    {
        this.@defaultValue = defaultValue
        this.@userProvidedValue = userProvidedValue
    }

    final NullableOptional defaultValue = NullableOptional.empty()
    final NullableOptional userProvidedValue = NullableOptional.empty()
}