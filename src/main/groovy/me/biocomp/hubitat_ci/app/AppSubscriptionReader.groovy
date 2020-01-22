package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.transform.TypeChecked
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.capabilities.Capabilities
import me.biocomp.hubitat_ci.util.NullableOptional
import me.biocomp.hubitat_ci.validation.Flags
import me.biocomp.hubitat_ci.validation.UnvalidatedInput

/**
 * Intercepts subscribe() methods and validates attribute names against capabilities.
 */
@TypeChecked
@PackageScope
class AppSubscriptionReader implements AppExecutor {
    AppSubscriptionReader(AppExecutor delegate, AppValidator validator, AppData data, HubitatAppScript script) {
        this.delegate = delegate
        this.validator = validator
        this.data = data
        this.script = script
    }

    @Override
    @CompileStatic
    void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handlerMethod, Map options) {
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
    void subscribe(Object toWhat, Object handlerMethod) {
        validateSubscribeMethod(
                "subscribe(${toWhat.inspect()}, ${handlerMethod.inspect()})",
                toWhat, NullableOptional.empty(), handlerMethod, this.validator.flags, this.script)
        delegate?.subscribe(toWhat, handlerMethod)
    }

    @Override
    @CompileStatic
    void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handlerMethod) {
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
    private static void validateNotNullOrEmpty(Object toWhat, NullableOptional attributeNameOrNameAndValueOrEventName, Closure makeError) {
        assert toWhat != null: makeError(": object being subscribe()'d to is null")

        if (attributeNameOrNameAndValueOrEventName.hasValue) {
            assert (attributeNameOrNameAndValueOrEventName.value as String): makeError(": event/attribute/value name parameter can't be null or empty")
        }
    }

    @CompileStatic
    private static void validateHandler(Object handlerMethod, HubitatAppScript script, Closure makeError) {
        assert handlerMethod != null: makeError(" can't be called with null event handler");
        if (handlerMethod instanceof String) {
            final def methodWithOneObjectArg = script.getMetaClass().pickMethod(handlerMethod, [Object] as Class[])
            assert methodWithOneObjectArg: makeError(" refers to method '${handlerMethod}' which does not exist in the script (method must one arg)")
        }
    }

    @CompileStatic
    private static void validateLocation(NullableOptional attributeNameOrNameAndValueOrEventName, Closure makeError) {
        if (attributeNameOrNameAndValueOrEventName.hasValue) {
            assert validLocationEvents.find { it == attributeNameOrNameAndValueOrEventName.value }: makeError(": '${attributeNameOrNameAndValueOrEventName.value}' is not a valid event for location. Valid values are: ${(validLocationEvents as HashSet<String>).inspect()}")
        }
    }

    private static HashSet<String> allExistingAttributes =
            Capabilities.capabilitiesByDeviceSelector.collect{Capabilities.readAttributes(it.value).collect{it.key}}.flatten() as HashSet<String>

    @CompileStatic
    private static void validateDevice(
            Object toWhat,
            NullableOptional attributeNameOrNameAndValueOrEventName,
            EnumSet<Flags> flags,
            Closure makeError) {
        assert DeviceWrapper.isInstance(toWhat): makeError(": object ${toWhat} is not a valid input (not a device) to subscribe to. Note: subscribe(app) is not supported")

        // Capability is specified, and if it's valid, return
        if (attributeNameOrNameAndValueOrEventName.hasValue && Capabilities.capabilitiesByDeviceSelector.containsKey(attributeNameOrNameAndValueOrEventName.value)) {
            return
        }

        final def device = (DeviceWrapper) toWhat
        if (!flags.contains(Flags.AllowAnyDeviceAttributeOrCapabilityInSubscribe) && !flags.contains(Flags.AllowAnyExistingDeviceAttributeOrCapabilityInSubscribe)) {
            assert attributeNameOrNameAndValueOrEventName.hasValue: makeError(": when subscribing to device event, you need to specify at least an event name")

            final def attributes = device.supportedAttributes
            final def nameAndMaybeValue = (attributeNameOrNameAndValueOrEventName.value as String).split(/(\.)/)

            assert (nameAndMaybeValue.size() == 1 || nameAndMaybeValue.size() == 2): ": '${attributeNameOrNameAndValueOrEventName.value}' should either be 'attibuteName' or 'attributename.attributevalue'"
            final def attribute = attributes.find { it.name.equalsIgnoreCase(nameAndMaybeValue[0]) }

            assert attribute: makeError(": device '${device.label}' does not contain attribute '${nameAndMaybeValue[0]}'. Valid attributes are: ${attributes.collect { it.name }}")

            // Validate value
            if (!flags.contains(Flags.AllowAnyAttributeEnumValuesInSubscribe) && nameAndMaybeValue.size() == 2) {
                if (attribute.dataType == 'ENUM') {
                    assert (attribute.values.find { it.equalsIgnoreCase(nameAndMaybeValue[1]) } != null): makeError(": '${nameAndMaybeValue[0]}' for device '${device.label}' does not have value '${nameAndMaybeValue[1]}'. Valid values are: ${attribute.values}")
                }
            }
        }
        else if (flags.contains(Flags.AllowAnyExistingDeviceAttributeOrCapabilityInSubscribe)) {
            assert attributeNameOrNameAndValueOrEventName.hasValue: makeError(": when subscribing to device event, you need to specify at least an event name")
            assert allExistingAttributes.find{ it.compareToIgnoreCase(attributeNameOrNameAndValueOrEventName.value as String)} != null: makeError(": '${attributeNameOrNameAndValueOrEventName}' is not one of valid attributes: ${allExistingAttributes})")
        }
    }

    @CompileStatic
    private static void validateSubscribeMethod(String description, Object toWhat, NullableOptional attributeNameOrNameAndValueOrEventName, Object handlerMethod, EnumSet<Flags> flags, HubitatAppScript script) {
        if (flags.contains(Flags.DontValidateSubscriptions)) {
            return
        }

        final def makeError = { String message -> "${description}${message}. You can skip subscription validation with ${Flags.DontValidateSubscriptions}, or make it less strict with ${Flags.AllowAnyExistingDeviceAttributeOrCapabilityInSubscribe} or ${Flags.AllowAnyDeviceAttributeOrCapabilityInSubscribe} flags." }
        validateNotNullOrEmpty(toWhat, attributeNameOrNameAndValueOrEventName, makeError)
        validateHandler(handlerMethod, script, makeError)

        if (!UnvalidatedInput.isInstance(toWhat)) {
            if (Location.isInstance(toWhat)) {
                validateLocation(attributeNameOrNameAndValueOrEventName, makeError)
            } else {
                validateDevice(toWhat, attributeNameOrNameAndValueOrEventName, flags, makeError)
            }
        }
    }

    /**
     * Now that all inputs are known, create efficient lookup map
     */
    @CompileStatic
    void initializationComplete() {
        //assert inputs.isEmpty() : "initializationComplete should only have been called once"
        //data.preferences.allInputs.each{ input -> inputs.add(input.name, input) }
    }

    @Delegate
    final private AppExecutor delegate
    final private AppValidator validator
    final private AppData data
    final private HubitatAppScript script
}
