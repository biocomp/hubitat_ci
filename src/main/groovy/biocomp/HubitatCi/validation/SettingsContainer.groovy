package biocomp.hubitatCi.validation

import biocomp.hubitatCi.app.InputWrapper
import biocomp.hubitatCi.app.preferences.Input
import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode

/**
 * Helps ensure that scripts accesses only defined inputs.
 */
@TypeChecked
class SettingsContainer implements Map<String, Object>
{
    /**
     * @param userSettingsValue - user can define settings in UTs, and rest of the settings will be added to it too.
     * @param mode - how to validate user actions with settings.
     */
    SettingsContainer(Closure<String> getCurrentPageName, ValidatorBase validator, Map<String, Object> userSettingsValue) {
        this.getCurrentPageName = getCurrentPageName
        this.validator = validator
        this.userSettingValues = userSettingsValue
    }

    /**
     * After reading preferences, this method is called.
     * Any call to validateAfterPreferences() should happen after this method, or is ignored.
     */
    void preferencesReadingDone()
    {
        this.@preferencesReadingIsDone = true
    }

    /**
     * After all the relevant script methods were run, execute this method to (if in strict mode):

     * 1. Verify that settings were only read, and not set.
     * 2. Verify that only settings from 'inputs' param were read.*/
    void validateAfterPreferences(String methodName = "[initialization]") {
        if (!validator.hasFlag(Flags.AllowReadingNonInputSettings) && this.@preferencesReadingIsDone) {
            def readSettingsThatAreNotInputs = settingsRead - registeredInputs.keySet()
            assert !readSettingsThatAreNotInputs : "In ${methodName} settings were read that are not registered inputs: ${readSettingsThatAreNotInputs}. These are registered inputs: ${registeredInputs}. This is not allowed in strict mode (add Flags.AllowReadingNonInputSettings to allow this)"
        }
    }

    /**
     * Registers data for future validation + gets actual value from user-provided data.
     * User data may contain either name-value pair for input values,
     * or value could itself be a map with page names as key and corresponding values.
     * @param name
     * @return
     */
    @TypeChecked(TypeCheckingMode.SKIP) // Skip for easier working with maps
    @Override
    Object get(Object name) {
        name = name as String
        settingsRead << name

        // We have per page mapping of values
        def currentPageName = getCurrentPageName()
        def userSettingValue = userSettingValues.get(name)
        if (currentPageName && userSettingValue instanceof Map && userSettingValue.containsKey('_') && userSettingValue.containsKey(currentPageName))
        {
            return userSettingValue."${currentPageName}"
        }
        else if (currentPageName && userSettingValue instanceof Map && userSettingValue.containsKey('_'))
        {
            return userSettingValue."_"
        }

        if (userSettingValue != null) {
            return userSettingValue
        }
        else
        {
            def foundInput = registeredInputs.get(name);
            if (foundInput)
            {
                return new InputWrapper(foundInput)
            }
        }

        return null
    }

    @Override
    Object put(String name, Object value) {
        if (!validator.hasFlag(Flags.AllowWritingToSettings)) {
            assert false: "You tried assigning '${value}' to '${name}', but writing to settings is not allowed in strict mode (add Flags.AllowWritingToSettings to allow this)."
        }

        return userSettingValues.put(name, value)
    }

    /**
     * Call this method to provide information about valid user input name.
     * It's called by AppPreferencesReader, during reading the preferences.
     *
     * This allows (in strict checking mode) to make sure that only
     * inputs provided so far were read.
     * @param p
     */
    @CompileStatic
    void userInputFound(Input input) {
        registeredInputs.put(input.readName(), input)
    }

    @CompileStatic
    void userInputFound(String inputName) {
        registeredInputs.put(inputName, null)
    }

    private final Closure<String> getCurrentPageName
    private final ValidatorBase validator

    private final Set<String> settingsRead = []
    private final HashMap<String, Input> registeredInputs = [:]

    @Delegate
    private final Map<String, Object> userSettingValues

    private boolean preferencesReadingIsDone = false
}
