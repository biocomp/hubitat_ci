package biocomp.hubitatCiTest.emulation

/** 
    Same as SmartThings' InstalledSmartApp
*/
trait InstalledAppWrapper
{
    /**
        @return state of the attribute.
    */
    abstract AppState getCurrentState(String attributeName)

    /**
        Owner's account ID.
    */
    abstract String getAccountId()

    /**
        @return all child apps, even if installation state is "incomplete"
    */
    abstract List<InstalledAppWrapper> getAllChildApps()

    abstract Map getAppSettings()

    /**
        @return only child apps whose state is "complete"
    */
    abstract List<InstalledAppWrapper> getChildApps()

    abstract List<Device> getChildDevices()

    abstract boolean getExecutionIsModeRestricted()

    /**
        @return list of all modes App can run in
    */
    abstract List<Mode> getExecutableModes()

    abstract String getId()

    /**
        @return "complete" or "incomplete"
    */
    abstract String getInstallationState()

    abstract String getLabel()

    abstract String getName()

    abstract String getNamespace()

    abstract InstalledAppWrapper getParent()

    abstract List<EventSubscriptionWrapper> getSubscriptions()

    /**
     App's states in reverse chronological order.
     @param options : max (Number) - max states to return (default = 10)
     */
    abstract List<AppState> statesBetween(String attributeName, Date startDate, Date endDate, Map options = null)

    /**
     App's states in reverse chronological order.
     @param options : max (Number) - max states to return (default = 10).
     @return list of states, up to 7 days, and up to 1000.
     */
    abstract List<AppState> statesSince(String attributeName, Date startDate, Map options = null)

    abstract void updateLabel(String label)
}