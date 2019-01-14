package biocomp.hubitatCiTest.apppreferences

import groovy.transform.TypeChecked

enum ValidationFlags
{
    Default,
    DontRunScript,
    DontValidatePreferences,
    DontValidateDefinition,
    AllowWritingToSettings,
    AllowReadingNonInputSettings,
    AllowUnreachablePages,

    /**
     * Don't require install: true on at least one of the pages.
     */
    AllowMissingInstall

    @TypeChecked
    static EnumSet<ValidationFlags> from(ValidationFlags... settings)
    {
        return  EnumSet.copyOf(settings.toList());
    }
}