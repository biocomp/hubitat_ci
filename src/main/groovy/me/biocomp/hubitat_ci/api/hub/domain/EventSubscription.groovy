package me.biocomp.hubitat_ci.api.hub.domain

trait EventSubscription {
    abstract java.lang.Long getDeviceId()
    abstract boolean getFilter()
    abstract java.lang.String getHandler()
    abstract java.lang.Long getId()
    abstract me.biocomp.hubitat_ci.api.hub.domain.InstalledApp getInstalledApp()
    abstract java.lang.Long getInstalledAppId()
    abstract java.lang.Long getLocationId()
    abstract java.lang.String getName()
    abstract boolean isFilter()
    abstract void setDeviceId(java.lang.Long a)
    abstract void setFilter(boolean a)
    abstract void setHandler(java.lang.String a)
    abstract void setId(java.lang.Long a)
    abstract void setInstalledApp(me.biocomp.hubitat_ci.api.hub.domain.InstalledApp a)
    abstract void setInstalledAppId(java.lang.Long a)
    abstract void setLocationId(java.lang.Long a)
    abstract void setName(java.lang.String a)
}
