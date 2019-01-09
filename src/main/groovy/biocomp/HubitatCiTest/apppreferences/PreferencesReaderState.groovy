package biocomp.hubitatCiTest.apppreferences

class PreferencesReaderState {
    Preferences currentPreferences
    Page currentPage
    Section currentSection

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
            currentPreferences.validate()
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
                currentPage.validate()
            }
        } finally {
            currentPage = null
        }

        return newPage
    }

    Section initWithSection(Section newSection, Closure makeContents) {
        try {
            assert !this.@currentSection: "section() is being called recursively. This is not supported."

            currentSection = newSection
            makeContents()

            currentSection.validate()
        } finally {
            currentSection = null
        }

        return newSection
    }
}
