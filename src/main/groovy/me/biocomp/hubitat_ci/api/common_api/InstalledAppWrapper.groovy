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
}