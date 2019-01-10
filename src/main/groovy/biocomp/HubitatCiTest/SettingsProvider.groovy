package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.Preferences

import java.util.EnumSet
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import groovy.transform.TypeChecked

/**
 * When dynamic property (like input's value) is read or set
 * (either directly or through settings), it can be validated against existing inputs.
 */
enum SettingsCheckingMode
{
    /**
     * Make sure no properties are set this way, and only existing inputs are read.
     */
    Strict,

    /**
     * Any property name is 'valid' and anything can be set and read, including existing inputs.
     */
    None
}

@TypeChecked
class SettingsProvider implements AppExecutor{
    /**
     * @param delegate - forward all of AppExecutor's methods to this delegate (except for getSettings).
     * @param existongSettings - user can define settings in UTs, and rest of the settings will be added to it too.
     * @param mode - how to validate user actions with settings.
     */
    SettingsProvider(AppExecutor delegate, SettingsCheckingMode mode, Map existongSettings = [:])
    {
        this.delegate = delegate
        this.settings = existongSettings
        this.mode = mode

        settings.each {
            settingsBehavior[it.key as String] = EnumSet.of(SettingBehavior.UserProvided);
        }
    }

    @Override
    Map getSettings()
    {
        return settings
    }

    /**
     * After all the relevant script methods were run, execute this method to (if in strict mode):

     * 1. Verify that settings were only read, and not set.
     * 2. Verify that only settings from 'inputs' param were read.
     */
    void validate()
    {
        if (mode == SettingsCheckingMode.Strict) {
            assert preferences: "preferences were not set to validate the settings against input names"
        }
    }

    void setPreferences(Preferences preferences)
    {
        this.preferences = preferences
    }

    def getSetting(String name)
    {
        if (!settings.containsKey(name))
        {
            settingsBehavior.put(name, EnumSet.of(SettingBehavior.WasSet))
            return null
        }
        else
        {
            settingsBehavior[name].add(SettingBehavior.WasSet)
            return settings[name]
        }
    }

    void setSetting(String name, def value)
    {
        if (mode == SettingsCheckingMode.Strict)
        {
            assert false : "You tried assigning '${value}' to '${name}', but setting settings is not allowed in strict mode."
        }

        if (!settings.containsKey(name))
        {
            settingsBehavior.put(name, EnumSet.of(SettingBehavior.WasRead, SettingBehavior.WasReadBeforeSet))
            settings.put(name, value)
        }
        else
        {
            settingsBehavior[name].add(SettingBehavior.WasRead)
            settings[name] = value
        }
    }

    private enum SettingBehavior
    {
        UserProvided,
        WasRead,
        WasSet,
        WasReadBeforeSet
    }

    private final SettingsCheckingMode mode
    private final Map<String, EnumSet<SettingBehavior>> settingsBehavior = [:]
    private final Map settings
    private Preferences preferences = null

    @Delegate
    private final AppExecutor delegate
}
