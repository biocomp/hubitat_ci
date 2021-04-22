package me.biocomp.hubitat_ci.api.app_api;

import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper

interface Subscription {
    /**
     * Don't expect this class to be exported from Hubitat controller
     */
    abstract boolean _is_hubitat_ci_private()

    /**
     * Subscribe to event, or attribute value changes.
     *
     * @param toWhat                                 - could be Device, Location (or app_api)?
     * @param attributeNameOrNameAndValueOrEventName Could be:
     *                                               1. name of event,
     *                                               2. name of attribute that changed
     *                                               3. Format of 'attribute.value' to get notification only for specific value
     * @param handlerMethod                          - The method to call when the Event is fired.
     *                                               Can be a String of the method name or the method reference itself.
     * @param options
     */
    abstract void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handlerMethod, Map options)

    abstract void subscribe(Object location, Object handlerMethod)

    abstract void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handler)

    abstract void subscribe(java.lang.Object a, groovy.lang.MetaMethod b, java.util.Map c) // Original: public void com.hubitat.hub.executor.AppExecutor.subscribe(java.lang.Object,groovy.lang.MetaMethod,java.util.Map)


    /**
     * Deletes all subscriptions for the installed App.
     * Typically should be called in the updated() method, since device preferences may have changed.
     */
    abstract void unsubscribe()

    /**
     * Deletes device subscription.
     * Typically should be called in the updated() method, since device preferences may have changed.
     */
    abstract void unsubscribe(DeviceWrapper device)

    /**
     * Deletes subscriptions for devices.
     * Typically should be called in the updated() method, since device preferences may have changed.
     */
    abstract void unsubscribe(List devices)

    abstract void unsubscribe(me.biocomp.hubitat_ci.api.common_api.Location a, java.lang.String b, java.lang.String c) // Original: public void com.hubitat.hub.executor.AppExecutor.unsubscribe(com.hubitat.hub.domain.Location,java.lang.String,java.lang.String)
}
