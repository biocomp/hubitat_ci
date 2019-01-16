package biocomp.hubitatCiTest.apppreferences

import groovy.transform.TypeChecked

enum ValidationFlags
{
    Default,
    DontRunScript,
    DontValidatePreferences,
    DontValidateDefinition,
    DontValidateMappings,
    AllowWritingToSettings,
    AllowReadingNonInputSettings,
    AllowUnreachablePages,
    AllowTitleInPageCallingMethods,

    /**
     * Allow required properties (such as page(title: "")) to be empty strings.
     * It's just a workaround to be able to support existing scripts.
     */
    AllowEmptyOptionValueStrings,

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