package me.biocomp.hubitat_ci.api.common_api

/** 
    Same as SmartThings' InstalledSmartApp
*/
trait InstalledAppWrapper
{
    abstract Long getAppTypeId()
    abstract Long getId()

    /**
        @return "complete" or "incomplete"
    */
    abstract String getInstallationState()

    abstract String getLabel()

    abstract String getName()

    abstract Long getParentAppId()

    abstract List<EventSubscriptionWrapper> getSubscriptions()

    abstract void updateLabel(String label)

    abstract void clearSetting(String settingName)
    abstract void removeSetting(String settingName)
    abstract void updateSetting(String settingName, Boolean val)
    abstract void updateSetting(String settingName, Date val)
    abstract void updateSetting(String settingName, Double val)
    abstract void updateSetting(String settingName, List val)
    abstract void updateSetting(String settingName, Long val)
    abstract void updateSetting(String settingName, Map val)
    abstract void updateSetting(String settingName, String val)

    abstract me.biocomp.hubitat_ci.api.hub.AppAtomicState getAtomicState() // Original: public com.hubitat.hub.AppAtomicState com.hubitat.app.InstalledAppWrapper.getAtomicState()
    abstract java.lang.Object getSetting(java.lang.String a) // Original: public java.lang.Object com.hubitat.app.InstalledAppWrapper.getSetting(java.lang.String)
    abstract java.util.Map getState() // Original: public java.util.Map com.hubitat.app.InstalledAppWrapper.getState()
    abstract void saveState() // Original: public void com.hubitat.app.InstalledAppWrapper.saveState()
    abstract void setAtomicState(me.biocomp.hubitat_ci.api.hub.AppAtomicState a) // Original: public void com.hubitat.app.InstalledAppWrapper.setAtomicState(com.hubitat.hub.AppAtomicState)
    abstract void setState(java.util.Map a) // Original: public void com.hubitat.app.InstalledAppWrapper.setState(java.util.Map)
}