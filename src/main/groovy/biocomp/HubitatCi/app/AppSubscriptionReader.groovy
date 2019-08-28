package me.biocomp.hubitat_ci.app

import biocomp.hubitatCi.api.Device
import biocomp.hubitatCi.api.app_api.AppExecutor
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.transform.TypeChecked

/**
 * Intercepts subscribe() methods and validates attribute names against capabilities.
 */
@TypeChecked
@PackageScope
class AppSubscriptionReader implements AppExecutor  {
    AppSubscriptionReader(AppExecutor delegate, AppValidator validator, AppData data)
    {
        this.delegate = delegate
        this.validator = validator
        this.data = data
    }

    @Override
    @CompileStatic
    void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handlerMethod, Map options)
    {
        validateSubscribeMethod(toWhat, attributeNameOrNameAndValueOrEventName, handlerMethod)
        delegate?.subscribe(toWhat, attributeNameOrNameAndValueOrEventName, handlerMethod, options)
    }

    @Override
    @CompileStatic
    void subscribe(Object toWhat, Object handlerMethod)
    {
        validateSubscribeMethod(toWhat, null, handlerMethod)
        delegate?.subscribe(toWhat, handlerMethod)
    }

    @Override
    @CompileStatic
    void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handler)
    {
        validateSubscribeMethod(toWhat, attributeNameOrNameAndValueOrEventName, handler)
        delegate?.subscribe(toWhat, attributeNameOrNameAndValueOrEventName, handler)
    }

    /*
    void subscribe(deviceOrDevices, String attributeName, handlerMethod)
    void subscribe(deviceOrDevices, String attributeNameAndValue, handlerMethod)
    void subscribe(Location location, handlerMethod)
    void subscribe(Location location, String eventName, handlerMethod)
    void subscribe(app, handlerMethod)
    */

    @CompileStatic
    private void validateSubscribeMethod(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handlerMethod)
    {
        // Need to be able to get input object.
        assert Device.isInstance(toWhat) : "Object ${toWhat} is not a valid input (not a device) to subscribe to."

        def device = (Device)toWhat
        def capability = device.capability

        // Only validate capability attributes if capability is known
        if (capability != null) {
            def attributes = device.supportedAttributes
            assert attributes.find {
                it.name == attributeNameOrNameAndValueOrEventName
            }: "Device '${capability.simpleName}' does not contain attribute '${attributeNameOrNameAndValueOrEventName}'. Valid attributes are: ${attributes.collect { it.name }}"
        }

        // Then check that it's a capability
        // Then parse attributeNameOrNameAndValueOrEventName and get an attribute or event name.

        // From docs: "The specific attribute value to subscribe to, in the format "<attributeName>.<attributeValue>"


        // Then validate that capability has it
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
}
