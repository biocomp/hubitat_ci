package me.biocomp.hubitat_ci.api.device_api.zwave.commands.switchcolorv1

import me.biocomp.hubitat_ci.api.device_api.zwave.Command

trait SwitchColorSupportedReport extends Command{
    abstract boolean getAmber() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.getAmber()
    abstract boolean getBlue() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.getBlue()
    abstract boolean getColdWhite() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.getColdWhite()
    abstract boolean getCyan() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.getCyan()
    abstract boolean getGreen() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.getGreen()
    abstract boolean getIndex() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.getIndex()
    abstract boolean getPurple() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.getPurple()
    abstract boolean getRed() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.getRed()
    abstract boolean getWarmWhite() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.getWarmWhite()
    abstract boolean isAmber() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.isAmber()
    abstract boolean isBlue() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.isBlue()
    abstract boolean isColdWhite() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.isColdWhite()
    abstract boolean isCyan() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.isCyan()
    abstract boolean isGreen() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.isGreen()
    abstract boolean isIndex() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.isIndex()
    abstract boolean isPurple() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.isPurple()
    abstract boolean isRed() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.isRed()
    abstract boolean isWarmWhite() // Original: public boolean hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.isWarmWhite()
    abstract void setAmber(boolean a) // Original: public void hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.setAmber(boolean)
    abstract void setBlue(boolean a) // Original: public void hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.setBlue(boolean)
    abstract void setColdWhite(boolean a) // Original: public void hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.setColdWhite(boolean)
    abstract void setCyan(boolean a) // Original: public void hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.setCyan(boolean)
    abstract void setGreen(boolean a) // Original: public void hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.setGreen(boolean)
    abstract void setIndex(boolean a) // Original: public void hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.setIndex(boolean)
    abstract void setPurple(boolean a) // Original: public void hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.setPurple(boolean)
    abstract void setRed(boolean a) // Original: public void hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.setRed(boolean)
    abstract void setWarmWhite(boolean a) // Original: public void hubitat.zwave.commands.switchcolorv1.SwitchColorSupportedReport.setWarmWhite(boolean)
}
