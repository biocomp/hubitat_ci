package me.biocomp.hubitat_ci.api.device_api.zigbee.clusters.iaszone

trait ZoneStatus {
    abstract int getAc()
    abstract int getAlarm1()
    abstract int getAlarm2()
    abstract int getBattery()
    abstract int getBatteryDefect()
    abstract int getRestoreReports()
    abstract int getSupervisionReports()
    abstract int getTamper()
    abstract int getTest()
    abstract int getTrouble()
    abstract boolean isAcSet()
    abstract boolean isAlarm1Set()
    abstract boolean isAlarm2Set()
    abstract boolean isBatteryDefectSet()
    abstract boolean isBatterySet()
    abstract boolean isRestoreReportsSet()
    abstract boolean isSupervisionReportsSet()
    abstract boolean isTamperSet()
    abstract boolean isTestSet()
    abstract boolean isTroubleSet()
}
