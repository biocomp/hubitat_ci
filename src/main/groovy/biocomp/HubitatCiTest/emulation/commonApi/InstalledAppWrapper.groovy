package biocomp.hubitatCiTest.emulation.commonApi

/*
Real methods:
public void com.hubitat.appApi.InstalledAppWrapper.clearSetting(java.lang.String),

public java.lang.Long com.hubitat.appApi.InstalledAppWrapper.getAppTypeId(),
public java.lang.Long com.hubitat.appApi.InstalledAppWrapper.getId(),
public java.lang.String com.hubitat.appApi.InstalledAppWrapper.getInstallationState(),
public java.lang.String com.hubitat.appApi.InstalledAppWrapper.getLabel(),
public java.lang.String com.hubitat.appApi.InstalledAppWrapper.getName(),
public java.lang.Long com.hubitat.appApi.InstalledAppWrapper.getParentAppId(),
public java.util.List com.hubitat.appApi.InstalledAppWrapper.getSubscriptions(),

public void com.hubitat.appApi.InstalledAppWrapper.removeSetting(java.lang.String),

public void com.hubitat.appApi.InstalledAppWrapper.updateLabel(java.lang.String),

public void com.hubitat.appApi.InstalledAppWrapper.updateSetting(java.lang.String,java.lang.Boolean),
public void com.hubitat.appApi.InstalledAppWrapper.updateSetting(java.lang.String,java.lang.Double),
public void com.hubitat.appApi.InstalledAppWrapper.updateSetting(java.lang.String,java.lang.Long),
public void com.hubitat.appApi.InstalledAppWrapper.updateSetting(java.lang.String,java.lang.String),
public void com.hubitat.appApi.InstalledAppWrapper.updateSetting(java.lang.String,java.util.Date),
public void com.hubitat.appApi.InstalledAppWrapper.updateSetting(java.lang.String,java.util.List),
public void com.hubitat.appApi.InstalledAppWrapper.updateSetting(java.lang.String,java.util.Map)
 */

/** 
    Same as SmartThings' InstalledSmartApp
*/
trait InstalledAppWrapper
{
    abstract String getId()

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
    abstract void updateSetting(String settingName, def val)
}