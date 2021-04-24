package me.biocomp.hubitat_ci.api.device_api.zwave.commands.screenattributesv2

trait ScreenAttributesReport {
    abstract java.lang.String format()
    abstract java.lang.String getCMD()
    abstract java.lang.Short getCommandClassId()
    abstract java.lang.Short getCommandId()
    abstract java.lang.Boolean getEscapeSequence()
    abstract java.lang.Short getNumberOfCharactersPerLine()
    abstract java.lang.Short getNumberOfLines()
    abstract java.lang.Short getNumericalPresentationOfACharacter()
    abstract java.util.List getPayload()
    abstract java.lang.Short getScreenTimeout()
    abstract java.lang.Short getSizeOfLineBuffer()
    abstract void setEscapeSequence(java.lang.Boolean a)
    abstract void setNumberOfCharactersPerLine(java.lang.Short a)
    abstract void setNumberOfLines(java.lang.Short a)
    abstract void setNumericalPresentationOfACharacter(java.lang.Short a)
    abstract void setScreenTimeout(java.lang.Short a)
    abstract void setSizeOfLineBuffer(java.lang.Short a)
}
