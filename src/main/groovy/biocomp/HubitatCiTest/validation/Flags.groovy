package biocomp.hubitatCiTest.validation

import groovy.transform.TypeChecked

enum Flags
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
    static EnumSet<Flags> from(Flags... settings)
    {
        return  EnumSet.copyOf(settings.toList());
    }
}