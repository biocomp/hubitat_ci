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
            ex.message.contains("page")
            ex.message.contains("can't be empty")
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
            ex.message.contains("unsupported option 'someInvalidOption'")
    }

    def "Reading valid page options"(String validOptionName, def expectedValue)
    {
        given:
            def preferences = new HubitatAppSandbox("""
preferences{
    page(${validOptionName}:"${expectedValue}"){
        section{
            input "temperature1", "number", title: "Temperature"
        }
    }
}""").readPreferences()

        expect:
            preferences.pages[0].options."${validOptionName}" == expectedValue

        where:
            validOptionName | expectedValue
            "name"          | "some name"
    }

    def "preferences() can't be empty"()
    {
        when:
            new HubitatAppSandbox("preferences{}").readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message.contains("preferences")
            ex.message.contains("can't be empty")
    }
//
//    def "Page needs to have name and title"(String script, String expectedError)
//    {
//        when:
//            new HubitatAppSandbox(script).preferencesAreCorrect()
//
//        then:
//            thrown().message.contains(expectedError)
//
//        where:
//            script | expectedError
//            "preferences(page())"
//    }
}