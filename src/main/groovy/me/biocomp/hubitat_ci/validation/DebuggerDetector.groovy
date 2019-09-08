package me.biocomp.hubitat_ci.validation

import groovy.transform.CompileStatic

/**
 * Helps detect when SettingsContainer's get() method was actually triggered by
 * debugger updating 'watch' or 'variables' window.
 *
 * TODO: this workaround is a fragile and not generic. I'm sure there must be a better way of detecting this.
 */
@CompileStatic
class DebuggerDetector {
    /**
     * Only detects debugger-injected code for IntellijIDEA (in its current form)
     */
    boolean isTraceFromDebugger(StackTraceElement[] trace)
    {
        return trace.findIndexOf {it.className == 'DUMMY' } != -1
    }
}
