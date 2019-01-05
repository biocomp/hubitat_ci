package biocomp.hubitatCiTest

import spock.lang.Specification

class AppPreferencesValidatorTest extends Specification {
    def "Page can't be empty"()
    {
        given:
            def sandbox = new HubitatAppSandbox('''
preferences{
    page("name", "title"){}
}
''')
        when:
            sandbox.readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message.contains("Page")
            ex.message.contains("must have at least one section")
    }

    def "Can add sections w/o pages"()
    {
        given:
            def sandbox = new HubitatAppSandbox('''
preferences{
    section("sec"){
        input "temperature1", "number", title: "Temperature"
    }
}
''')
        expect:
            sandbox.readPreferences()
    }



    def "Invalid page options cause error"()
    {
        when:
            new HubitatAppSandbox("""
preferences{
    page(someInvalidOption:""){
        section{
            input "temperature1", "number", title: "Temperature"
        }
    }
}""").readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message.contains("Option 'someInvalidOption' is not supported")
    }

    def "Reading valid page options"(String validOptionName, def givenValue, def expectedValue)
    {
        given:
            def preferences = new HubitatAppSandbox("""
preferences{
    page(${validOptionName}:${givenValue}){
        section{
            input "temperature1", "number", title: "Temperature"
        }
    }
}""").readPreferences()

        expect:
            preferences.pages[0].options."${validOptionName}" == expectedValue
            preferences.pages[0].options."${validOptionName}".class == expectedValue.class

        where:
            validOptionName | givenValue              || expectedValue
            "name"          | '"some name"'           || "some name"
            "nextPage"      | '"some next page name"' || "some next page name"
            "install"       | true                    || true
            "uninstall"     | false                   || false
            "install"       | "true"                  || true
            "uninstall"     | "false"                 || false
    }

    def "preferences() can't be empty"()
    {
        when:
            new HubitatAppSandbox("preferences{}").readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message.contains("has to have pages (got 0) or sections (got 0)")
    }

    def "section() must have a title"()
    {
        when:
            new HubitatAppSandbox("preferences{}").readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message.contains("has to have pages (got 0) or sections (got 0)")
    }
}