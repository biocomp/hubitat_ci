package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.api.Device
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.util.NullableOptional
import me.biocomp.hubitat_ci.validation.Flags

/**
 * Intercepts subscribe() methods and validates attribute names against capabilities.
 */
@TypeChecked
@PackageScope
class AppSubscriptionReader implements AppExecutor  {
    AppSubscriptionReader(AppExecutor delegate, AppValidator validator, AppData data, HubitatAppScript script)
    {
        this.delegate = delegate
        this.validator = validator
        this.data = data
        this.script = script
    }

    @Override
    @CompileStatic
    void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handlerMethod, Map options)
    {
        validateSubscribeMethod(
                "subscribe(${toWhat.inspect()}, '${attributeNameOrNameAndValueOrEventName}', ${handlerMethod.inspect()}, ${options})",
                toWhat,
                NullableOptional.withValue(attributeNameOrNameAndValueOrEventName),
                handlerMethod,
                this.validator.flags,
                this.script)
        delegate?.subscribe(toWhat, attributeNameOrNameAndValueOrEventName, handlerMethod, options)
    }

    @Override
    @CompileStatic
    void subscribe(Object toWhat, Object handlerMethod)
    {
        validateSubscribeMethod(
                "subscribe(${toWhat.inspect()}, ${handlerMethod.inspect()})",
                toWhat, NullableOptional.empty(), handlerMethod, this.validator.flags, this.script)
        delegate?.subscribe(toWhat, handlerMethod)
    }

    @Override
    @CompileStatic
    void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handlerMethod)
    {
        validateSubscribeMethod(
                "subscribe(${toWhat.inspect()}, '${attributeNameOrNameAndValueOrEventName}', ${handlerMethod.inspect()})",
                toWhat,
                NullableOptional.withValue(attributeNameOrNameAndValueOrEventName),
                handlerMethod,
                this.validator.flags,
                this.script)
        delegate?.subscribe(toWhat, attributeNameOrNameAndValueOrEventName, handlerMethod)
    }

    /*
    void subscribe(deviceOrDevices, String attributeName, handlerMethod)
    void subscribe(deviceOrDevices, String attributeNameAndValue, handlerMethod)
    void subscribe(Location location, handlerMethod)
    void subscribe(Location location, String eventName, handlerMethod)
    void subscribe(app, handlerMethod)
    */

    private final static def validLocationEvents = ["mode", "position", "sunset", "sunrise", "sunriseTime", "sunsetTime"] as HashSet<String>

    @CompileStatic
    private static void validateNotNullOrEmpty(String description, Object toWhat, NullableOptional attributeNameOrNameAndValueOrEventName)
    {
        assert toWhat != null : "${description}: Object being subscribe()'d to is null"

        if (attributeNameOrNameAndValueOrEventName.hasValue) {
            assert (attributeNameOrNameAndValueOrEventName.value as String): "${description}: event/attribute/value name parameter can't be null or empty"
        }
    }

    @CompileStatic
    private static void validateHandler(String description, Object handlerMethod, HubitatAppScript script)
    {
        assert handlerMethod != null: "${description} can't be called with null event handler"
        if (handlerMethod instanceof String) {
            final def methodWithOneObjectArg = script.getMetaClass().pickMethod(handlerMethod, [Object] as Class[])
            assert methodWithOneObjectArg: "${description} refers to method '${handlerMethod}' which does not exist in the script (method must one arg)."
        }
    }

    @CompileStatic
    private static void validateLocation(String description, NullableOptional attributeNameOrNameAndValueOrEventName)
    {
        if (attributeNameOrNameAndValueOrEventName.hasValue) {
            assert validLocationEvents.find{ it == attributeNameOrNameAndValueOrEventName.value }: "${description}: '${attributeNameOrNameAndValueOrEventName.value}' is not a valid event for location. Valid values are: ${(validLocationEvents as HashSet<String>).inspect()}."
        }
    }

    @CompileStatic
    private static void validateDevice(String description, Object toWhat, NullableOptional attributeNameOrNameAndValueOrEventName)
    {
        assert Device.isInstance(toWhat)  : "${description}: Object ${toWhat} is not a valid input (not a device) to subscribe to. Note: subscribe(app) is not supported."

        final def device = (Device)toWhat
        final def capability = device.capability

        // Only validate capability attributes if capability is known
        if (capability != null) {
            assert attributeNameOrNameAndValueOrEventName.hasValue : "${description}: when subscribing to device event, you need to specify at least the capability"

            final def attributes = device.supportedAttributes
            final def nameAndMaybeValue = (attributeNameOrNameAndValueOrEventName.value as String).split(/(\.)/)

            assert(nameAndMaybeValue.size() == 1 || nameAndMaybeValue.size() == 2): "'${attributeNameOrNameAndValueOrEventName.value}' should either be 'attibuteName' or 'attributename.attributevalue'"
            final def attribute = attributes.find { it.name == nameAndMaybeValue[0]}

            assert attribute: "${description}: Device '${capability.simpleName}' does not contain attribute '${nameAndMaybeValue[0]}'. Valid attributes are: ${attributes.collect { it.name }}"

            // Validate value
            if (nameAndMaybeValue.size() == 2)
            {
                if (attribute.dataType == 'ENUM')
                {
                    assert (attribute.values.find{ it == nameAndMaybeValue[1]}!= null): "${description}: '${nameAndMaybeValue[0]}' for device '${capability.simpleName}' does not have value '${nameAndMaybeValue[1]}'. Valid values are: ${attribute.values}"
                }
            }
        }
    }

    @CompileStatic
    private static void validateSubscribeMethod(String description, Object toWhat, NullableOptional attributeNameOrNameAndValueOrEventName, Object handlerMethod, EnumSet<Flags> flags, HubitatAppScript script)
    {
        if (flags.contains(Flags.DontValidateSubscriptions)) {
            return
        }

        validateNotNullOrEmpty(description, toWhat, attributeNameOrNameAndValueOrEventName)
        validateHandler(description, handlerMethod, script)
        if (Location.isInstance(toWhat)) {
            validateLocation(description, attributeNameOrNameAndValueOrEventName)
        }
        else {
            validateDevice(description, toWhat, attributeNameOrNameAndValueOrEventName)
        }
    }

    /**
     * Now that all inputs are known, create efficient lookup map
     */
    @CompileStatic
    void initializationComplete()
    {
        //assert inputs.isEmpty() : "initializationComplete should only have been called once"
        //data.preferences.allInputs.each{ input -> inputs.add(input.name, input) }
    }

    @Delegate
    final private AppExecutor delegate
    final private AppValidator validator
    final private AppData data
    final private HubitatAppScript script
}
