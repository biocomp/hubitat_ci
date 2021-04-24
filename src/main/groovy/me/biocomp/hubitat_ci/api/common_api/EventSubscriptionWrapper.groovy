package me.biocomp.hubitat_ci.api.common_api

trait EventSubscriptionWrapper {
    abstract java.lang.String getData() // Original: public java.lang.String com.hubitat.app.EventSubscriptionWrapper.getData()
    abstract me.biocomp.hubitat_ci.api.common_api.DeviceWrapper getDevice() // Original: public com.hubitat.app.DeviceWrapper com.hubitat.app.EventSubscriptionWrapper.getDevice()
    abstract java.lang.Long getDeviceId() // Original: public java.lang.Long com.hubitat.app.EventSubscriptionWrapper.getDeviceId()
    abstract java.lang.String getHandler() // Original: public java.lang.String com.hubitat.app.EventSubscriptionWrapper.getHandler()
    abstract java.lang.Long getId() // Original: public java.lang.Long com.hubitat.app.EventSubscriptionWrapper.getId()
    abstract java.lang.Long getLocationId() // Original: public java.lang.Long com.hubitat.app.EventSubscriptionWrapper.getLocationId()
}

