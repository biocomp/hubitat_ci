package biocomp.hubitatCiTest.apppreferences


import biocomp.hubitatCiTest.validation.Validator
import groovy.transform.TypeChecked

@TypeChecked
class PreferencesReaderState {
    PreferencesReaderState(Validator validator)
    {
        this.validator = validator;
    }

    final private Validator validator

    Preferences currentPreferences
    Page currentPage
    Section currentSection
    String currentDynamicMethod

    Preferences getCurrentPreferences()
    {
        assert this.@currentPreferences != null : "preferences() was not properly called"
        return this.@currentPreferences
    }

    boolean hasCurrentPage() { return this.@currentPage != null }

    Page getCurrentPage()
    {
        assert this.@currentPage != null : "page() was not properly called"
        return this.@currentPage
    }

    Section getCurrentSection()
    {
        assert this.@currentSection != null : "section() was not properly called"
        return this.@currentSection
    }

    Preferences initWithPreferences(Preferences newPrefrences, Closure makeContents)
    {
        try
        {
            assert !this.@currentPreferences : "preferences() are being called recursively. This is not supported."

            currentPreferences = newPrefrences
            makeContents()
        }
        finally
        {
            currentPreferences = null
        }

        return newPrefrences
    }

    Page initWithPage(Page newPage, Closure makeContents, boolean validate = true) {
        try {
            assert !this.@currentPage: "page() is being called recursively. This is not supported."

            currentPage = newPage
            makeContents()

            if (validate) {
                currentPage.validate(validator)
            }
        } finally {
            currentPage = null
        }

        return newPage
    }

    void withCurrentDynamicMethod(String methodName, Closure makeContents) {
        try {
            assert !this.@currentDynamicMethod: "${currentDynamicMethod}() is being called recursively. This is not supported."

            currentDynamicMethod = methodName
            makeContents()

            validator.validateSingleDynamicPageFor(currentPreferences, currentDynamicMethod)

        } finally {
            currentDynamicMethod = null
        }
    }

    Section initWithSection(Section newSection, Closure makeContents) {
        try {
            assert !this.@currentSection: "section() is being called recursively. This is not supported."

            currentSection = newSection
            makeContents()

            currentSection.validate(validator)
        } finally {
            currentSection = null
        }

        return newSection
    }
}
