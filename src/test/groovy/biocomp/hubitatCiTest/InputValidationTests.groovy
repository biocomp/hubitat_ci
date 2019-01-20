package biocomp.hubitatCiTest

import org.codehaus.groovy.runtime.metaclass.MethodSelectionException
import spock.lang.Specification

import static biocomp.hubitatCiTest.PreferencesValidationCommon.parseOneChild

/**
 * Validation tests for preferences' inputs
 * (separated from the rest of preferences test).
 */
class InputValidationTests extends Specification {

    def "input() can only consist of name and type"() {
        given:
            def input = parseOneChild("input 'nam', 'typ'")

        expect:
            input.name == "nam"
            input.type == "typ"
    }

    def "input(name:'some name', type:text) should fail because text should be quoted"() {
        when:
            parseOneChild("input name:'some nam', type:text")

        then:
            AssertionError e = thrown()
            e.message.contains('not registered inputs: [text]')
    }

    def "input() can't be empty"() {
        when:
            def input = parseOneChild("input()")

        then:
            MethodSelectionException e = thrown()
            e.message.contains("input()")
    }
}
