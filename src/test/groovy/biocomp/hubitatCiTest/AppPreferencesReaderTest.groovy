package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.Preferences
import biocomp.hubitatCiTest.apppreferences.ValidationFlags
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

    private static Object parseOneChild(String elementString) {
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

    static Preferences fromScript(String script) {
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
            script                                | expectedErrorStrings
            makePageWithParams('title:"p title"') | ["mandatory parameter", "name"]
            makePageWithParams('null, "p title"') | ["null", "name"]
            makePageWithParams('"", "p title"')   | ["empty", "name"]
            makePageWithParams('name:"p name"')   | ["mandatory parameter", "title"]
            makePageWithParams('"nam", null')     | ["null", "title"]
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

    def "paragraph() with just the text"() {
        when:
            def paragraph = parseOneChild("paragraph 'some text'")

        then:
            paragraph.text == "some text"
    }

    def "paragraph() with map of valid options"() {
        given:
            def paragraph = parseOneChild("paragraph(title:'some text', image:'some img', required: true)")

        expect:
            paragraph.options.title == "some text"
            paragraph.options.image == "some img"
            paragraph.options.required == true
    }

    def "paragraph() with map and invalid option fails"() {
        when:
            parseOneChild("paragraph(badOption:'Im going to fail', title:'some text')")

        then:
            AssertionError e = thrown()
            e.message.contains("badOption")
    }

    def "paragraph() with no options is not supported"() {
        when:
            parseOneChild("paragraph()")

        then:
            MethodSelectionException e = thrown()
            e.message.contains("paragraph")
    }

    // label()
    def "label() with map of valid options"() {
        given:
            def label = parseOneChild("""label(
                title:'some text', 
                description:'some desc', 
                required: true,
                image: 'some img')""")

        expect:
            label.options.title == "some text"
            label.options.description == 'some desc'
            label.options.required == true
            label.options.image == "some img"
    }

    def "label() with map and invalid option fails"() {
        when:
            parseOneChild("label(badOption:'Im going to fail', title:'some text')")

        then:
            AssertionError e = thrown()
            e.message.contains("badOption")
    }

    def "label() with no options is not supported"() {
        when:
            parseOneChild("label()")

        then:
            AssertionError e = thrown()
            e.message.contains("mandatory")
            e.message.contains("title")
    }

    def "dynamicPage()'s contents can use input values from previous pages"() {
        given:
            def preferences = new HubitatAppSandbox($/
preferences{
    page("static page", "static title"){
        section(){
            input "in1", "fromPage0"
        }
    }

    page(name: "makePage2")
    page(name: "makePage3")
}

def makePage2()
{
    dynamicPage(name: "makePage2", title: "tit2"){
        section("$${in1} section, unknown: $${blah}"){
            paragraph "$${in1} paragraph1, unknown: $${in2}"
            input "in2", "in2 = $${in1} input type"
        }
    }
}

def makePage3()
{
    dynamicPage(name: "makePage3", title: "tit3"){
        section("$${in2} section"){
            paragraph "$${in1} paragraph2"
        }
    }
}
/$).readPreferences([in1: ["_": "input1 val everywhere"],
                     in2: ["makePage2": null,
                           "makePage3": "input2 val on page3",
                           "_"        : "should not be used"]],
                    EnumSet.of(ValidationFlags.ValidatePreferences, ValidationFlags.NoWritingToSettings))

        expect:
            preferences.dynamicPages[0].sections[0].title == 'input1 val everywhere section, unknown: null'
            preferences.dynamicPages[0].sections[0].children[
                    0].text == 'input1 val everywhere paragraph1, unknown: null'
            preferences.dynamicPages[0].sections[0].children[1].readType() == 'in2 = input1 val everywhere input type'

            preferences.dynamicPages[1].sections[0].title == 'input2 val on page3 section'
            preferences.dynamicPages[1].sections[0].children[0].text == 'input1 val everywhere paragraph2'
    }

    def "dynamicPage()'s contents can't use unknown inputs in strict mode"() {
        when:
            new HubitatAppSandbox($/
preferences{
    page("static page", "static title"){
        section(){
            input "in1", "fromPage0"
            input "in3", "fromPage0"
        }
    }

    page(name: "makePage2")
}

def makePage2()
{
    dynamicPage(name: "makePage2", title: "tit2"){
        section("sec"){
            paragraph "$${in1} paragraph, unknown: $${in2}"
        }
    }
}
/$).readPreferences([it1: "input1val", it2: "input2val"])

        then:
            AssertionError e = thrown()
            e.message.contains("in2")
            e.message.contains("These are registered inputs: [in1, in3]")
    }

    def "All the pages are reachable (as initial pages or nextPage or href)"() {

        expect:
            assert false
    }

    def "Page is not reachable (via initial pages or nextPage or href), thus validation fails"() {

        expect:
            assert false
    }

    def "install/uninstall needs to be specified for multipage settings"() {

        expect:
            assert false
    }

    def "dynamicPage() name needs to match method it was created by"() {
        expect:
            assert false
    }

    def "Only one dynamicPage() can be eventually created by page() with reference to method name"() {
        expect:
            assert false
    }
}