package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.Preferences
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.runtime.metaclass.MethodSelectionException
import spock.lang.Specification
import spock.lang.Unroll

class AppPreferencesReaderTest extends
        Specification
{
    private static String validInput = 'input "temperature1", "number", title: "Temperature"'
    private static String validSection = "section(\"sec\"){${validInput}}"

    private static String makePropertiesWithPageWithSection(String sectionParams) {
        return """
preferences{
    page("name", "title"){
        section(${sectionParams}){${validInput}}
    }
}
"""
    }

    private static String makePropertiesWithPageWithSectionWithElement(String elementText) {
        return """
preferences{
    page("name", "title"){
        section("sec"){
            ${elementText}
        }
    }
}
"""
    }

    private static Object parseOneChild(String elementString)
    {
        return fromScript(makePropertiesWithPageWithSectionWithElement(elementString)).pages[0].sections[0].children[0]
    }

    private static String makePropertiesWithSection(String sectionParams) {
        println "makePropertiesWithSection('${sectionParams}')"

        return """
preferences{
    section(${sectionParams}){${validInput}}
}
"""
    }

    private static String makePageWithParams(String pageParams) {
        println "makePageWithParams('${pageParams}')"

        return """
preferences{
    page(${pageParams}){${validSection}}
}
"""
    }

    static Preferences fromScript(String script)
    {
        return new HubitatAppSandbox(script).readPreferences()
    }


    def "Page can't be empty when it's not a dynamic page"() {
        given:
            def sandbox = new HubitatAppSandbox(new File("Scripts/test.groovy"))
        when:
            sandbox.readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message.contains("Page")
            ex.message.contains("must have at least one section")
    }

    def "Dynamic page's method will be called right away"() {
        given:
            def sandbox = new HubitatAppSandbox("""
preferences{
    page(name:"dynamicMe")
}

void dynamicMe()
{
    dynamicPage(name:"imSoDynamic", title:"dynamicTitle"){ ${validSection} }
}
""")
            def preferences = sandbox.readPreferences()

        expect:
            preferences.dynamicPages[0].options['name'] == "imSoDynamic"
            preferences.dynamicPages[0].options['title'] == "dynamicTitle"
            preferences.dynamicPages[0].sections[0].children[0].options.title == "Temperature"
    }

    def "Can add sections w/o pages"() {
        given:
            def sandbox = new HubitatAppSandbox('''
preferences{
    section("sec"){
        input "temperature1", "number", title: "Temperature"
    }
}
''')
        def properties = sandbox.readPreferences()

        expect:
            properties.pages[0].sections[0].title == "sec"
    }


    def "Invalid page options cause error"() {
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

    @Unroll
    def "Reading valid page options"(String pageOptions, String propetyName, def expectedValue) {
        given:
            def preferences = new HubitatAppSandbox(makePageWithParams(pageOptions)).readPreferences()

        expect:
            preferences.pages[0].options."${propetyName}" == expectedValue
            preferences.pages[0].options."${propetyName}".class == expectedValue.class

        where:
            pageOptions                                  | propetyName || expectedValue
            '"nam", "titl"'                              | 'name'      || "nam"
            'title:"titl", name:"nam"'                   | 'name'      || "nam"
            '"nam", "titl"'                              | 'title'     || "titl"
            'title:"titl", name:"nam"'                   | 'title'     || "titl"
            'name:"n", title:"t", nextPage: "next page"' | 'nextPage'  || "next page"
            'name:"n", title:"t", install:true'          | 'install'   || true
            'name:"n", title:"t", uninstall: false'      | 'uninstall' || false
    }


    @Unroll
    def "Page must have 'name' and 'title' (when not a dynamic page)"(String script, String[] expectedErrorStrings) {
        when:
            new HubitatAppSandbox(script).readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message ==~ /(?i).*page.*/
            expectedErrorStrings.each { ex.message.contains(it) }

        where:
            script                                   | expectedErrorStrings
            makePageWithParams('title:"p title"') | ["mandatory parameter", "name"]
            makePageWithParams('null, "p title"') | ["null", "name"]
            makePageWithParams('"", "p title"')   | ["empty", "name"]
            makePageWithParams('name:"p name"')   | ["mandatory parameter", "title"]
            makePageWithParams('"nam", null')        | ["null", "title"]
            makePageWithParams('"p nam", ""')     | ["empty", "title"]
    }

    def "preferences() can't be empty"() {
        when:
            new HubitatAppSandbox("preferences{}").readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message.contains("has to have pages (got 0), dynamic pages (got 0)")
    }

    def "preferences() can't be missing"() {
        when:
            new HubitatAppSandbox("").readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message.contains("preferences()")
            ex.message.contains("never called")
    }

    @Unroll
    def "section()'s unsupported properties fail compilation"(String script) {
        when:
            new HubitatAppSandbox(script).readPreferences()

        then:
            def ex = thrown(AssertionError)
            ex.message ==~ /(?i).*option.*/
            ex.message ==~ /.*someUnsupportedOption.*/

        where:
            script << [makePropertiesWithSection('someUnsupportedOption:"blah"'),
                       makePropertiesWithPageWithSection('someUnsupportedOption:"blah"')]
    }

    def "preferences() could be calling methods of script to generate non-dynamic pages"() {
        given:
            def sandbox = new HubitatAppSandbox("""
preferences{
    userProvidedMethodToMakeStaticPages()
}

def userProvidedMethodToMakeStaticPages()
{
    page(name:"fromUserMethod", title:"titleFromUserMethod") { ${validSection} }
}
""")
            def preferences = sandbox.readPreferences()

        expect:
            preferences.pages[0].options['name'] == "fromUserMethod"
            preferences.pages[0].options['title'] == "titleFromUserMethod"
    }

    def "input() can only consist of name and type"()
    {
        given:
            def input = parseOneChild("input 'nam', 'typ'")

        expect:
            input.name == "nam"
            input.type == "typ"
    }

    def "input() can't be empty"()
    {
        when:
            def input = parseOneChild("input()")

        then:
            MethodSelectionException e = thrown()
            e.message.contains("input()")
    }

    def "paragraph() with just the text"()
    {
        when:
            def paragraph = parseOneChild("paragraph 'some text'")

        then:
            paragraph.text == "some text"
    }

    def "paragraph() with map of valid options"()
    {
        given:
            def paragraph = parseOneChild(
                    "paragraph(title:'some text', image:'some img', required: true)"
            )

        expect:
            paragraph.options.title == "some text"
            paragraph.options.image == "some img"
            paragraph.options.required == true
    }

    def "paragraph() with map and invalid option fails"()
    {
        when:
            def paragraph = parseOneChild(
                "paragraph(badOption:'Im going to fail', title:'some text')"
            )

        then:
            AssertionError e = thrown()
            e.message.contains("badOption")
    }

    def "paragraph() with no options"()
    {
        when:
            def paragraph = parseOneChild("paragraph()")

        then:
            MethodSelectionException e = thrown()
            e.message.contains("paragraph")
    }
}