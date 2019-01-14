package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.Mode
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

    private static String pageWith(String elementText) {
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

    private static Object parseOneChild(String elementString) {
        return fromScript(pageWith(elementString)).pages[0].sections[0].children[0]
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

    def "page() names must be unique"() {
        when:
            new HubitatAppSandbox(script).readPreferences()

        then:
            AssertionError e = thrown()
            e.message.contains("page2")
            e.message.contains("must be unique")

        where:
            script << ["""preferences{ 
    page(name: "page2", title: "t2"){${validSection} }
    page(name: "page2", title: "t2"){${validSection} }
}""",
                       """preferences{ 
    page(name: "page2", title: "t2"){${validSection} }
    page(name: "page2")
}

def page2()
{
    dynamicPage(name: "page2", title: "t2"){${validSection}
}
}"""]
    }

    def "Dynamic page's method will be called right away"() {
        given:
            def sandbox = new HubitatAppSandbox("""
preferences{
    page(name:"imSoDynamic")
}

void imSoDynamic()
{
    dynamicPage(name:"imSoDynamic", title:"dynamicTitle", install: true){ ${validSection} }
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
            def preferences = new HubitatAppSandbox(makePageWithParams(pageOptions)).readPreferences(
                    validationFlags: [ValidationFlags.AllowMissingInstall, ValidationFlags.AllowUnreachablePages])

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
    page(name:"fromUserMethod", title:"titleFromUserMethod", install: true) { ${validSection} }
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
    dynamicPage(name: "makePage2", title: "tit2", install: true){
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
/$).readPreferences(userSettingValues: [in1: ["_": "input1 val everywhere"],
                                        in2: ["makePage2": null,
                                              "makePage3": "input2 val on page3",
                                              "_"        : "should not be used"]],
                    validationFlags: [ValidationFlags.AllowReadingNonInputSettings, ValidationFlags.AllowUnreachablePages])

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
    dynamicPage(name: "makePage2", title: "tit2", install: true){
        section("sec"){
            paragraph "$${in1} paragraph, unknown: $${in2}"
        }
    }
}
/$).readPreferences(userSettingValues: [it1: "input1val", it2: "input2val"], validationFlags: [ValidationFlags.AllowUnreachablePages])

        then:
            AssertionError e = thrown()
            e.message.contains("in2")
            e.message.contains("These are registered inputs: [in1, in3]")
    }

    @Unroll
    def "href and page's nextPage fail if referring to invalid pages"(String script) {
        when:
            new HubitatAppSandbox(script).readPreferences(validationFlags: [ValidationFlags.AllowMissingInstall])

        then:
            AssertionError e = thrown()
            e.message.contains("invalidPageName")
            e.message.contains("reference")

        where:
            script << [
                """
preferences{
    page("p1", "tit", nextPage:'invalidPageName') { ${validSection}}
}
""",
                """
preferences{
    page("p1", "tit") {section('tit'){ href('invalidPageName') }}
}
""",
                """
preferences{
    page("p1", "tit") {section('tit'){ href(page: 'invalidPageName') }}
}
"""
            ]
    }

    @Unroll
    def "Page is not reachable (via initial pages or nextPage or href), thus validation fails"(String script) {
        when:
            new HubitatAppSandbox(script).readPreferences(validationFlags: [ValidationFlags.AllowMissingInstall])

        then:
            AssertionError e = thrown()
            !e.message.contains("p1")
            !e.message.contains("p3")
            !e.message.contains("p5")
            e.message.contains("p2")
            e.message.contains("p4")
            e.message.contains("not reachable")

        where:
            script << ["""
preferences{
    page("p1", "tit") { 
        section("tit") { href('p3') }
        section("tit") { href(style: 'page', page: 'p5') }
    }
    page("p2", "tit") {
        section("tit") { href('p4') }
    }
    page("p3", "tit") {${validSection}}
    page("p4", "tit") {${validSection}}
    page("p5", "tit") {${validSection}}
}
""",
                       """
preferences{
    page("p1", "tit", nextPage: 'p3') {${validSection}}
    page(name: "p2")
    page("p3", "tit") {${validSection}}
    page("p4", "tit") {${validSection}}
}
    
def p2()
{
    dynamicPage(name: "p2", title: "tit", nextPage: 'p4') {${validSection}}
}

def p4()
{
    dynamicPage(name: "p4", title: "tit") {${validSection}}
}

"""]
    }

    @Unroll
    def "install needs to be specified for mult-ipage settings"(String script) {
        when:
            new HubitatAppSandbox(script).readPreferences()

        then:
            AssertionError e = thrown()
            e.message.contains("install: true")

        where:
            script << ["""
preferences{
    page("p1", "tit") {${validSection}}
    page("p2", "tit") {${validSection}}
}
"""
                       ,
                       """
preferences{
    page("p1", "tit") {${validSection}}
}
"""
                       ,
                       """
preferences{page(name: "p1")}

def p1() { dynamicPage(name: "p1", title: "t1", install:false) {${validSection}}}
"""
                       ,
                       """
preferences{page(name: "p1")}

def p1() { dynamicPage(name: "p1", title: "t1", install:false) {${validSection}}}
"""]
    }

    @Unroll
    def "dynamicPage() name needs to match method it was created by"(String script) {
        when:
            new HubitatAppSandbox(script).readPreferences()

        then:
            AssertionError e = thrown()
            e.message.contains("makePage2Method")
            e.message.contains("makePage2")
            e.message.contains("match")

        where:
            script << ["""
preferences{ page(name: "makePage2Method") }

def makePage2Method() { 
    dynamicPage(name: "makePage2", title: "tit2"){ ${validSection}} 
}""",
                       """
preferences{ page(name: "makePage2Method") }

def foo() { bar() }
def bar() { baz() }
def baz() { dynamicPage(name: "makePage2", title: "tit2"){ ${validSection}} }

def makePage2Method() { foo() }"""]
    }

    def "Only one dynamicPage() can be eventually created by page() with reference to method name"(String script) {
        when:
            new HubitatAppSandbox(script).readPreferences()

        then:
            AssertionError e = thrown()
            e.message.contains("makePage2")

        where:
            script << ["""
preferences{ page(name: "makePage2") }

def makePage2() { 
    dynamicPage(name: "makePage2", title: "tit2"){ ${validSection}} 
    dynamicPage(name: "makePage2", title: "tit2"){ ${validSection}}
}""",
                       """
preferences{ page(name: "makePage2") }

def foo() { bar() }
def bar() { baz(); dynamicPage(name: "makePage2", title: "tit2"){ ${validSection}} }
def baz() { dynamicPage(name: "makePage2", title: "tit2"){ ${validSection}} }

def makePage2() { foo() }"""]
    }

    def "href() with all supported parameters works"(String script, Map expectedValues) {
        when:
            def href = new HubitatAppSandbox(script).run(
                    validationFlags: [ValidationFlags.DontValidateDefinition, ValidationFlags.AllowUnreachablePages]).getProducedPreferences().pages[
                    0].sections[0].children[0]

        then:
            expectedValues.each {
                href."${it.key}" == it.value
            }

        where:
            script                                                                                                                                 | expectedValues
            pageWith(
                    "href('SomePage')")                                                                                                            | [nextPageName: "SomePage", title: null, options: null]
            pageWith(
                    "href(title: 'tit', required: false, description: 'desc', style:'page', url: 'http://a', page: 'somePage', image: 'someImg')") | [nextPageName: null, title: null, options: [title: 'tit', description: 'desc', style: 'page', url: 'http://a', page: 'somePage', image: 'someImg']]
            pageWith(
                    "href('tit', required: false, description: 'desc', style:'page', url: 'http://a', page: 'somePage', image: 'someImg')")        | [nextPageName: null, title: 'tit', options: [description: 'desc', style: 'page', url: 'http://a', page: 'somePage', image: 'someImg']]
    }

    def "href() 'page' option is not compatible with external style"() {
        when:
            new HubitatAppSandbox(pageWith("href(page: 'somePage', style: 'external')")).run(
                    validationFlags: [ValidationFlags.DontValidateDefinition])

        then:
            AssertionError e = thrown()
            e.message.contains("page")
            e.message.contains("incompatible")
            e.message.contains("style == external")
    }

    def "href() with unsupported parameter fails"() {
        when:
            new HubitatAppSandbox(pageWith("href(someBadVal: 'val')")).run(
                    validationFlags: [ValidationFlags.DontValidateDefinition])

        then:
            AssertionError e = thrown()
            e.message.contains("someBadVal")
            e.message.contains("not supported")
    }

    def "href() with unsupported style fails"() {
        when:
            new HubitatAppSandbox(pageWith("href(style: 'badStyle')")).run(
                    validationFlags: [ValidationFlags.DontValidateDefinition])

        then:
            AssertionError e = thrown()
            e.message.contains("not supported")
            e.message.contains("badStyle")
            e.message.contains("external, page, embedded")
    }

    def "href() with unsupported parameter succeeds, if validation is disabled"() {
        expect:
            new HubitatAppSandbox(pageWith("href(someBadVal: 'val')")).run(
                    validationFlags: [ValidationFlags.DontValidatePreferences, ValidationFlags.DontValidateDefinition])
    }

    def "mode() with all valid options"()
    {
        setup:
            def mode = new HubitatAppSandbox(pageWith("mode (title: 'tit', required: false, multiple: true, image: 'someImg')")).readPreferences(
                    validationFlags: [ValidationFlags.DontValidateDefinition]).pages[0].sections[0].children[0] as Mode

        expect:
            mode.options.title == 'tit'
            mode.options.required == false
            mode.options.multiple == true
            mode.options.image == 'someImg'
    }

    def "mode() with invalid option"()
    {
        when:
            new HubitatAppSandbox(pageWith("mode (title: 'tit', badOption: 'bad')")).run(
                    validationFlags: [ValidationFlags.DontValidateDefinition])

        then:
            AssertionError e = thrown()
            e.message.contains("badOption")
            e.message.contains("not supported")
            e.message.contains("image, multiple, title, required")
    }

    def "mode()'s title is required"()
    {
        when:
            new HubitatAppSandbox(pageWith("mode (required: false)")).run(
                    validationFlags: [ValidationFlags.DontValidateDefinition])

        then:
            AssertionError e = thrown()
            e.message.contains("title")
            e.message.contains("required")
    }
}