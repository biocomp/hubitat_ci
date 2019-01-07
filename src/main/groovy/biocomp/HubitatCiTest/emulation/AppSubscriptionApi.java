package biocomp.hubitatCiTest.emulation;

import java.util.List;
import java.util.Map;

public interface AppSubscriptionApi {
    /**
     * Subscribe to event, or attribute value changes.
     *
     * @param toWhat                                 - could be Device, Location (or app)?
     * @param attributeNameOrNameAndValueOrEventName Could be:
     *                                               1. name of event,
     *                                               2. name of attribute that changed
     *                                               3. Format of 'attribute.value' to get notification only for specific value
     * @param handlerMethod                          - The method to call when the Event is fired.
     *                                               Can be a String of the method name or the method reference itself.
     * @param options
     */
    default void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handlerMethod, Map options) {
    }

    default void subscribe(Object toWhat, Object handlerMethod) {
    }

    default void subscribe(Object toWhat, String attributeNameOrNameAndValueOrEventName, Object handler) {
    }

    /**
     * Deletes all subscriptions for the installed App.
     * Typically should be called in the updated() method, since device preferences may have changed.
     */
    default void unsubscribe() {
    }

    /**
     * Deletes device subscription.
     * Typically should be called in the updated() method, since device preferences may have changed.
     */
    default void unsubscribe(DeviceWrapper device) {
    }

    /**
     * Deletes subscriptions for devices.
     * Typically should be called in the updated() method, since device preferences may have changed.
     */
    default void unsubscribe(List devices) {
    }
}
