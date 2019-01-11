package biocomp.hubitatCiTest.apppreferences

import groovy.transform.TypeChecked

enum ValidationFlags
{
    DontRunScript,
    ValidatePreferences,
    ValidateDefinition,
    NoWritingToSettings,
    NoReadingNonInputSettings

    @TypeChecked
    static EnumSet<ValidationFlags> validatePreferencesAndSettings()
    {
        return from(ValidatePreferences, NoWritingToSettings, NoReadingNonInputSettings)
    }

    @TypeChecked
    static EnumSet<ValidationFlags> from(ValidationFlags... settings)
    {
        EnumSet<ValidationFlags> flags = EnumSet.copyOf(settings.toList());

        validate(flags)
        return flags
    }

    static void validate(EnumSet<ValidationFlags> flags)
    {
        if (flags.contains([NoReadingNonInputSettings, NoWritingToSettings, ValidatePreferences, ValidateDefinition]))
        {
            assert !flags.contains(DontRunScript)
        }
    }
}