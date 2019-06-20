package biocomp.hubitatCi.app

import biocomp.hubitatCi.api.appApi.AppExecutor
import biocomp.hubitatCi.app.preferences.Input
import biocomp.hubitatCi.capabilities.CapabilityAttributeInfo
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
        println "Got ${toWhat}"

        // Need to be able to get input object.
        assert toWhat instanceof InputWrapper : "Object ${toWhat} is not a valid input to subscribe to."

        def input = ((InputWrapper)toWhat).inputDescription_Internal
        assert Input.isCapabilityType(input.readType()) : "Input ${input}'s type is not a capability."

        def capability = Input.findCapabilityFromTypeString(input.readType())
        assert capability != null: "Input ${input}'s type ${input.readType()} points to unknown capability."

        def attributes = (Map<String, CapabilityAttributeInfo>)(capability.getField("_internalAttributes").get(null))
        assert attributes != null: "Capability ${capability} does not define any attributes to subscribe to"

        assert attributes.get(attributeNameOrNameAndValueOrEventName) : "Capability ${capability} does not have attribute ${attributeNameOrNameAndValueOrEventName}, it has these attributes: ${attributes.collect{it.key}}"


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
