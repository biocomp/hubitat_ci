package me.biocomp.hubitat_ci.util.integration

import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper

abstract class IntegrationDeviceWrapper implements DeviceWrapper {
    private Map attributeValues = [:]

    @Override
    void sendEvent(Map properties) {
        // The main result of sending an event is that the device's attributes are updated.
        attributeValues[properties.name] = properties.value
    }

    def currentValue(String attributeName) {
        return currentValue(attributeName, false)
    }

    @Override
    def currentValue(String attributeName, boolean skipCache) {
        return attributeValues[attributeName]
    }
}