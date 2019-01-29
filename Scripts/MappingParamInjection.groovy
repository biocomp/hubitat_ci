mappings{
    path('/p') {
        action: [
                'GET': 'handlerForGet',
                'PUT': 'handlerForPut'
        ]
    }
    path('/p/null') {
        action: [
                'PUT': 'handlerForPut2'
        ]
    }
}

private static String foo()
{
    return "String from foo()"
}

def bar()
{
    return "String from bar() [params = ${params}, request = ${request}]"
}

private handlerForGet()
{
    log.debug "handlerForGet foo(): ${foo()}"
    log.debug "handlerForGet bar(): ${bar()}"
    log.debug "state.blah = ${state.blah}"
    log.debug "setting state.blah = 42"
    state.blah = 42
    log.debug "someInputName = ${someInputName}"
    log.debug "settings.someOtherInput = ${settings.someOtherInput}"
    log.debug "params = ${params}"
    log.debug "request = ${request}"
    log.debug "myExistingMetaMethod() = ${myExistingMetaMethod()}"
    log.debug "myNewMetaMethod() = ${myNewMetaMethod()}"
}

private handlerForPut()
{
    log.debug "handlerForPut foo(): ${foo()}"
    log.debug "handlerForPut bar(): ${bar()}"
    log.debug "state.blah = ${state.blah}"
    log.debug "someInputName = ${someInputName}"
    log.debug "settings.someOtherInput = ${settings.someOtherInput}"
    log.debug "params = ${params}"
    log.debug "request = ${request}"
    log.debug "myNewMetaMethod() #2 = ${myNewMetaMethod()}"
}

private handlerForPut2()
{
    log.debug "handlerForPut2 bar(): ${bar()}"
    log.debug "state.blah = ${state.blah}"
    log.debug "params = ${params}"
    log.debug "request = ${request}"
    log.debug "myNewMetaMethod() #3 = ${myNewMetaMethod()}"
}

private String myExistingMetaMethod()
{
    return "existing method, NOT OVERRIDDEN!"
}

static private String myStaticExistingMetaMethod()
{
    return "static existing method, NOT OVERRIDDEN!"
}
