package biocomp.hubitatCi.validation

import groovy.transform.TupleConstructor
import groovy.transform.TypeChecked

@TypeChecked
class Parameter
{
    Parameter(Map<String, Object> options, Closure validator)
    {
        this(options, [], validator)
    }

    Parameter(Map<String, Object> options, List<String> extraValidParameterNames, Closure validator)
    {
        // Check that option's keys only has keys from known ones below and
        // extraValidParameterNames
        assert((
                options.keySet()
                        - ((["name", "required", "dontValidateIfFlags"] as Set)
                        + (extraValidParameterNames as Set))).size() == 0)

        assert(options.name)
        name = options.name

        required = options.get("required", false)

        def dontValidateWithFlagsValue = options.get("dontValidateIfFlags")
        if (dontValidateWithFlagsValue == null)
        {
            dontValidateWithTheseFlags = EnumSet.noneOf(Flags)
        }
        else if (dontValidateWithFlagsValue instanceof List<Flags>)
        {
            dontValidateWithTheseFlags = EnumSet.noneOf(Flags)
            dontValidateWithTheseFlags.addAll(dontValidateWithFlagsValue as List<Flags>)
        }
        else if (dontValidateWithFlagsValue instanceof EnumSet)
        {
            dontValidateWithTheseFlags = dontValidateWithFlagsValue as EnumSet
        }
        else
        {
            assert false, "${dontValidateWithFlagsValue} is not a supported way of passing set of flags for 'dontValidateIfFlags' parameter"
        }

        this.validator = {
            ValidatorBase validatorObject, context, paramName, value ->
                if (!validatorObject.hasAnyOfFlags(dontValidateWithTheseFlags))
                {
                    validator(validatorObject, context, paramName, value)
                }
        }
    }

    final String name
    final boolean required
    final EnumSet<Flags> dontValidateWithTheseFlags
    final Closure validator
}