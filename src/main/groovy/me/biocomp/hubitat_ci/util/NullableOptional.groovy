package me.biocomp.hubitat_ci.util

import groovy.transform.CompileStatic

/**
 * An optional that can also hold a null value.
 */
@CompileStatic
class NullableOptional {
    private NullableOptional()
    {}

    private NullableOptional(def value)
    {
        setValue(value)
    }

    static NullableOptional empty()
    {
        return new NullableOptional()
    }

    static NullableOptional withValue(def value)
    {
        return new NullableOptional(value)
    }

    def getValue() {
        assert hasValue

        return this.@value
    }

    void setValue(def value) {
        this.@value = value
        this.hasValue = true
    }

    boolean getHasValue() { return hasValue }

    private boolean hasValue = false
    private def value = null
}
