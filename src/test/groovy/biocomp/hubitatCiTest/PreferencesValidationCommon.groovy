package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.Preferences

class PreferencesValidationCommon {
    private static String validInput = 'input "temperature1", "number", title: "Temperature"'
    protected static String validSection = "section(\"sec\"){${validInput}}"

    static String makePropertiesWithPageWithSection(String sectionParams) {
        return """
preferences{
    page("name", "title"){
        section(${sectionParams}){${validInput}}
    }
}
"""
    }

    static String pageWith(String elementText) {
        return """
preferences{
    page("name", "title", install: true){
        section("sec"){
            ${elementText}
        }
    }
}
"""
    }

    static Object parseOneChild(String elementString) {
        return fromScript(pageWith(elementString)).pages[0].sections[0].children[0]
    }

    static String makePropertiesWithSection(String sectionParams) {
        println "makePropertiesWithSection('${sectionParams}')"

        return """
preferences{
    section(${sectionParams}){${validInput}}
}
"""
    }

    static String makePageWithParams(String pageParams) {
        return """
preferences{
    page(${pageParams}){${validSection}}
}
"""
    }

    static Preferences fromScript(String script) {
        return new HubitatAppSandbox(script).readPreferences()
    }
}
