package me.biocomp.hubitat_ci.validation

import groovy.transform.CompileStatic

/**
 * Helps detect when SettingsContainer's get() method was actually triggered by
 * debugger updating 'watch' or 'variables' window.
 *
 * TODO: this is an involved workaround, I'm sure there must be a better way of detecting this.
 */
@CompileStatic
class DebuggerDetector {
    final static int appScriptToSettingsGetFrames = 15
    final static int deviceScriptToSettingsGetFrames = 15

    DebuggerDetector(String scriptClassName, int expectedDistanceBetweenStackFrames)
    {
        this.scriptClassName = scriptClassName
        this.expectedDistanceBetweenStackFrames = expectedDistanceBetweenStackFrames
    }

    static int calculateDistanceBetweenCalls(StackTraceElement[] trace, String fromClass)
    {
        final def toFrameIndex = trace.findIndexOf {it.className == settingsContainerClassName && it.methodName == settingsMethodName }
        if (toFrameIndex != -1)
        {
            def fromFrameIndex = trace.findIndexOf(toFrameIndex, { it.className == fromClass })
            if (fromFrameIndex != -1) {
                fromFrameIndex == trace.size()
            }

            return fromFrameIndex - toFrameIndex
        }

        return trace.size()
    }

    /**
     * Only works for detection of SettingsContainer.get() call originating from a script.
     * @param trace
     * @return
     */
    boolean isTraceFromDebugger(StackTraceElement[] trace)
    {
        return trace.findIndexOf {it.className == 'DUMMY' } != -1//calculateDistanceBetweenCalls(trace, scriptClassName) != expectedDistanceBetweenStackFrames
    }

    private final int expectedDistanceBetweenStackFrames
    private final static String settingsContainerClassName = SettingsContainer.class.name
    private final static String settingsMethodName = "get"
    private final String scriptClassName
}
