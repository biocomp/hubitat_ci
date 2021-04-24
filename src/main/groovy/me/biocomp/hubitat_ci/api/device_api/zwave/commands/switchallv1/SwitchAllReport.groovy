package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchallv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait SwitchAllReport extends Command{
    abstract java.lang.Short getMODE_EXCLUDED_FROM_THE_ALL_OFF_FUNCTIONALITY_BUT_NOT_ALL_ON() // Original: public static java.lang.Short hubitat.zwave.commands.switchallv1.SwitchAllReport.getMODE_EXCLUDED_FROM_THE_ALL_OFF_FUNCTIONALITY_BUT_NOT_ALL_ON()
    abstract java.lang.Short getMODE_EXCLUDED_FROM_THE_ALL_ON_ALL_OFF_FUNCTIONALITY() // Original: public static java.lang.Short hubitat.zwave.commands.switchallv1.SwitchAllReport.getMODE_EXCLUDED_FROM_THE_ALL_ON_ALL_OFF_FUNCTIONALITY()
    abstract java.lang.Short getMODE_EXCLUDED_FROM_THE_ALL_ON_FUNCTIONALITY_BUT_NOT_ALL_OFF() // Original: public static java.lang.Short hubitat.zwave.commands.switchallv1.SwitchAllReport.getMODE_EXCLUDED_FROM_THE_ALL_ON_FUNCTIONALITY_BUT_NOT_ALL_OFF()
    abstract java.lang.Short getMODE_INCLUDED_IN_THE_ALL_ON_ALL_OFF_FUNCTIONALITY() // Original: public static java.lang.Short hubitat.zwave.commands.switchallv1.SwitchAllReport.getMODE_INCLUDED_IN_THE_ALL_ON_ALL_OFF_FUNCTIONALITY()
    abstract java.lang.Short getMode() // Original: public java.lang.Short hubitat.zwave.commands.switchallv1.SwitchAllReport.getMode()
    abstract void setMODE_EXCLUDED_FROM_THE_ALL_OFF_FUNCTIONALITY_BUT_NOT_ALL_ON(java.lang.Short a) // Original: public static void hubitat.zwave.commands.switchallv1.SwitchAllReport.setMODE_EXCLUDED_FROM_THE_ALL_OFF_FUNCTIONALITY_BUT_NOT_ALL_ON(java.lang.Short)
    abstract void setMODE_EXCLUDED_FROM_THE_ALL_ON_ALL_OFF_FUNCTIONALITY(java.lang.Short a) // Original: public static void hubitat.zwave.commands.switchallv1.SwitchAllReport.setMODE_EXCLUDED_FROM_THE_ALL_ON_ALL_OFF_FUNCTIONALITY(java.lang.Short)
    abstract void setMODE_EXCLUDED_FROM_THE_ALL_ON_FUNCTIONALITY_BUT_NOT_ALL_OFF(java.lang.Short a) // Original: public static void hubitat.zwave.commands.switchallv1.SwitchAllReport.setMODE_EXCLUDED_FROM_THE_ALL_ON_FUNCTIONALITY_BUT_NOT_ALL_OFF(java.lang.Short)
    abstract void setMODE_INCLUDED_IN_THE_ALL_ON_ALL_OFF_FUNCTIONALITY(java.lang.Short a) // Original: public static void hubitat.zwave.commands.switchallv1.SwitchAllReport.setMODE_INCLUDED_IN_THE_ALL_ON_ALL_OFF_FUNCTIONALITY(java.lang.Short)
    abstract void setMode(java.lang.Short a) // Original: public void hubitat.zwave.commands.switchallv1.SwitchAllReport.setMode(java.lang.Short)
}
