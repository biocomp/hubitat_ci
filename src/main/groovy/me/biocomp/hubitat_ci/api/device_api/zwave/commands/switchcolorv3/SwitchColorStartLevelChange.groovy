package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv3

trait SwitchColorStartLevelChange {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract short getColorComponentId()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract short getDimmingDuration()
    abstract boolean getIgnoreStartLevel()
    abstract java.util.List getPayload()
    abstract short getStartLevel()
    abstract boolean getUpDown()
    abstract boolean isIgnoreStartLevel()
    abstract boolean isUpDown()
    abstract void setColorComponentId(short a)
    abstract void setDimmingDuration(short a)
    abstract void setIgnoreStartLevel(boolean a)
    abstract void setStartLevel(short a)
    abstract void setUpDown(boolean a)
}
