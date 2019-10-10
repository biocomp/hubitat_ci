package me.biocomp.hubitat_ci.validation

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked

/**
 * Helps ensure that scripts accesses only defined inputs.*/
@TypeChecked
class SettingsContainer implements
        Map<String, Object>
{
    /**
     * @param userSettingsValue - user can define settings in UTs, and rest of the settings will be added to it too.
     */
    SettingsContainer(
            Closure<String> getCurrentPageName, ValidatorBase validator, Map<String, Object> userSettingsValue,
            IInputSource registeredInputs, DebuggerDetector debuggerDetector)
    {
        this.getCurrentPageName = getCurrentPageName
        this.validator = validator
        this.userSettingValues = userSettingsValue
        this.registeredInputs = registeredInputs
        this.debuggerDetector = debuggerDetector
    }

    /**
     * After reading preferences, this method is called.
     * Any call to validateAfterPreferences() should happen after this method, or is ignored.*/
    void preferencesReadingDone() {
        this.@preferencesReadingIsDone = true
    }

    /**
     * After all the relevant script methods were run, execute this method to (if in strict mode):

     * 1. Verify that settings were only read, and not set.
     * 2. Verify that only settings from 'inputs' param were read.*/
    // @CompileStatic (compiler crashes)
    void validateAfterPreferences(String methodName) {
        if (!validator.hasFlag(Flags.AllowReadingNonInputSettings) && this.@preferencesReadingIsDone) {
            def allValidInputNames = registeredInputs.getAllInputNames()
            def readSettingsThatAreNotInputs = settingsRead.keySet() - allValidInputNames
            assert !readSettingsThatAreNotInputs: "In '${methodName}' settings were read that are not registered inputs: ${readSettingsThatAreNotInputs}. These are registered inputs: ${allValidInputNames}. This is not allowed in strict mode (add Flags.AllowReadingNonInputSettings to allow this)"
        }
    }

    /**
     * Registers data for future validation + gets actual value from user-provided data.
     * User data may contain either name-value pair for input values,
     * or value could itself be a map with page names as key and corresponding values.
     * @param name
     * @return
     */
    @CompileStatic
    @Override
    Object get(Object name) {
        name = name as String

        final def trace = Thread.currentThread().stackTrace
        if (!this.@debuggerDetector.isTraceFromDebugger(trace)) {
            List<StackTraceElement[]> stacks = null
            if (this.@settingsRead.containsKey(name)) {
                stacks = this.@settingsRead.get(name)
            } else {
                stacks = new ArrayList<StackTraceElement[]>()
                this.@settingsRead.put(name, stacks)
            }

            stacks.add(trace)
        }

        // We have per page mapping of values
        final def currentPageName = getCurrentPageName()
        Object userSettingValue = this.@userSettingValues.get(name)

        // If these are per-page values
        if (userSettingValue instanceof Map) {
            final def userSettingMap = ((Map) userSettingValue)

            if (currentPageName && userSettingMap.containsKey(currentPageName)) {
                return userSettingValue = userSettingMap.get(currentPageName)
            } else if (userSettingMap.containsKey('_')) {
                return userSettingValue = userSettingMap.get("_")
            }
        }

        if (this.@userSettingValues.containsKey(name)) {
            return registeredInputs.generateInputWrapper(name, userSettingValue)
        }
        else {
            return registeredInputs.generateInputWrapper(name)
        }
    }

    @Override
    @CompileStatic
    Object put(String name, Object value) {
        if (!validator.hasFlag(Flags.AllowWritingToSettings)) {
            assert false: "You tried assigning '${value}' to '${name}', but writing to settings is not allowed in strict mode (add Flags.AllowWritingToSettings to allow this)."
        }

        return userSettingValues.put(name, value)
    }

    private final Closure<String> getCurrentPageName = null
    private final ValidatorBase validator = null

    private final HashMap<String, List<StackTraceElement[]>> settingsRead = [:]
    private final IInputSource registeredInputs = null

    @Delegate
    private final Map<String, Object> userSettingValues = null

    private boolean preferencesReadingIsDone = false

    private final DebuggerDetector debuggerDetector = null
}
