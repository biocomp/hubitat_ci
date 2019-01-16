package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.apppreferences.ValidationFlags
import biocomp.hubitatCiTest.emulation.appApi.AppExecutor
import biocomp.hubitatCiTest.emulation.commonApi.Log
import groovy.transform.NotYetImplemented
import spock.lang.Specification
import spock.lang.Unroll

class AppMappingsReaderTest extends
        Specification
{

    def tryReadMappings(String fromScript) {
        return new HubitatAppSandbox(fromScript).run(
                validationFlags: [ValidationFlags.DontValidateDefinition, ValidationFlags.DontValidatePreferences]).getProducedMappings()
    }

    def "No mappings is OK"() {
        expect:
            tryReadMappings('').size() == 0
    }

    def "One mapping with all valid callbacks"() {
        setup:
            def mappings = tryReadMappings("""
mappings{
    path('/some/path') {
        action: [
            GET: "handlerGet",
            PUT: "handlerPut",
            DELETE: "handlerDelete",
            POST: "handlerPost"
        ]
    }
    
    path('/some/other/path') {
        action: [
            PUT: "handlerPut2"
        ]
    }
}
def handlerGet()
{
    return "get called"
}

def handlerPost()
{
    return "post called"
}

def handlerDelete()
{
    return "delete called"
}

def handlerPut()
{
    return "put with request = '\${request}' called"
}

def handlerPut2()
{
    return "put2 called"
}
""")

        expect:
            mappings['/some/path'].actionNames['GET'] == "handlerGet"
            mappings['/some/path'].actions.GET(null, null) == "get called"

            mappings['/some/path'].actionNames['PUT'] == "handlerPut"
            mappings['/some/path'].actions.PUT(null, 'req') == "put with request = 'req' called"

            mappings['/some/path'].actionNames['POST'] == "handlerPost"
            mappings['/some/path'].actions.POST(null, null) == "post called"

            mappings['/some/path'].actionNames['DELETE'] == "handlerDelete"
            mappings['/some/path'].actions.DELETE(null, null) == "delete called"

            mappings['/some/other/path'].actionNames['PUT'] == "handlerPut2"
            mappings['/some/other/path'].actions.PUT(null, null) == "put2 called"
    }


    def "mappings() Can't have repeated paths"() {
        when:
            tryReadMappings("""
mappings{
    path('/myPath') { actions: [ GET: "foo"] }
    path('/myPath') { actions: [ GET: "foo"] }
}

def foo() {}
""")
        then:
            AssertionError e = thrown()
            e.message.contains('myPath')
            e.message.contains('unique')
    }

    def "mappings() only supports GET, PUT, POST, DELETE"(String paramName) {
        when:
            tryReadMappings("""
mappings{
    path('/myPath') { actions: [ ${paramName}: "foo"] }  
}

def foo() {}
""")

        then:
            AssertionError e = thrown()
            e.message.contains(paramName)
            e.message.contains((['GET', 'PUT', 'POST', 'DELETE'] as HashSet).toString())

        where:
            paramName << ['get', 'GETT', 'PU']
    }

    def "mappings() fails for unsupported methods"() {
        when:
            tryReadMappings("""
mappings{
    path('/myPath') { actions: [ GET: "someMethod"] }  
}""")

        then:
            AssertionError e = thrown()
            e.message.contains("someMethod")
            e.message.contains("does not exist")
            e.message.contains("GET")
    }

    @NotYetImplemented
    def "mappings() method can itself reference 'params' and 'request'"() {
        def script = """
def renderConfig() {
    def configJson = new groovy.json.JsonOutput().toJson(location?.helloHome?.getPhrases().collect({
        [
            accessory: "SmartThingsHelloHome",
            name: it.label,
            appId: it.id,
            accessToken: state.accessToken
        ]
    }))

    def configString = new groovy.json.JsonOutput().prettyPrint(configJson)
    render contentType: "text/plain", data: configString
}

mappings {
    if (!params.access_token || (params.access_token && params.access_token != state.accessToken)) {
        path("/config") { action: [GET: "authError"] }
    } else {
        path("/config") { action: [GET: "renderConfig"]  }
    }
}

def authError() {
    [error: "Permission denied"]
}
"""
    }

    def "mappings() handlers can refer to implicit 'params' and 'request' args and still access the rest of script's methods and data"() {
        given:
            Map state = [:]
            Log log = Mock()
            AppExecutor api = Mock {
                _ * getLog() >> log
                _ * getState() >> state
            }

            def mappings = new HubitatAppSandbox("""
mappings{
    path('/p') {
        action: [
            'GET': 'handlerForGet',
            'PUT': 'handlerForPut'
        ]
    }
}

private static String foo()
{
    return "String from foo()"
}

def bar()
{
    return "String from bar()"
}

private handlerForGet()
{
    log.debug "handlerForGet foo(): \${foo()}"
    log.debug "handlerForGet bar(): \${bar()}"
    log.debug "state.blah = \${state.blah}"
    log.debug "setting state.blah = 42"
    state.blah = 42
    log.debug "someInputName = \${someInputName}"
    log.debug "settings.someOtherInput = \${settings.someOtherInput}"
    log.debug "params = \${params}"
    log.debug "request = \${request}"
}

private handlerForPut()
{
    log.debug "handlerForPut foo(): \${foo()}"
    log.debug "state.blah = \${state.blah}"
    log.debug "someInputName = \${someInputName}"
    log.debug "settings.someOtherInput = \${settings.someOtherInput}"
    log.debug "params = \${params}"
    log.debug "request = \${request}"
}

""").run(validationFlags: [ValidationFlags.DontValidatePreferences, ValidationFlags.DontValidateDefinition],
                    api: api,
                    userSettingValues: [someInputName : "some input value",
                                        someOtherInput: "some other input value"]).getProducedMappings()

        when:
            mappings['/p'].actions.GET(123, "I'm a request")
            mappings['/p'].actions.PUT("I'm params", "Another request")

        then: "GET called"
            with(log) {
                // GET
                1 * debug("handlerForGet foo(): String from foo()")
                1 * debug("handlerForGet bar(): String from bar()")
                1 * debug("state.blah = null")
                1 * debug("setting state.blah = 42")
                1 * debug("someInputName = some input value")
                1 * debug("settings.someOtherInput = some other input value")
            }

        then: "PUT called"
            with(log) {
                // PUT
                1 * debug("params = 123")
                1 * debug("request = I'm a request")
                1 * debug("handlerForPut foo(): String from foo()")
                1 * debug("state.blah = 42")
                1 * debug("someInputName = some input value")
                1 * debug("settings.someOtherInput = some other input value")
                1 * debug("params = I'm params")
                1 * debug("request = Another request")
            }

        then:
            state.blah == 42
    }
}
