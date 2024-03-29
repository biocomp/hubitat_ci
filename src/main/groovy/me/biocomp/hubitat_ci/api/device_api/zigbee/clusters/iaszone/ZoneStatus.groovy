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
    abstract void setAc(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setAc(int)
    abstract void setAlarm1(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setAlarm1(int)
    abstract void setAlarm2(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setAlarm2(int)
    abstract void setBattery(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setBattery(int)
    abstract void setBatteryDefect(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setBatteryDefect(int)
    abstract void setRestoreReports(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setRestoreReports(int)
    abstract void setSupervisionReports(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setSupervisionReports(int)
    abstract void setTamper(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setTamper(int)
    abstract void setTest(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setTest(int)
    abstract void setTrouble(int a) // Original: public void hubitat.zigbee.clusters.iaszone.ZoneStatus.setTrouble(int)
}
