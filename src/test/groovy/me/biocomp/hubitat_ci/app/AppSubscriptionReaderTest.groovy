package me.biocomp.hubitat_ci.app

import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.ChildDeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification
import spock.lang.Unroll

class AppSubscriptionReaderTest extends Specification {
    // Other tests are done in AppAndDeviceSandboxTest

    def api = Mock(AppExecutor) {
        _ * getApp() >> Mock(InstalledAppWrapper)
        _ * getLocation() >> Mock(Location)
        _ * getChildDevices() >> [Mock(ChildDeviceWrapper){

        }]
    }

    private void runScript(String subscribeCall, String handler, List<Flags> validationFlags = [], boolean multiple = false) {
        def flags = validationFlags.clone()
        flags << Flags.DontValidateDefinition

        new HubitatAppSandbox(makeScriptForSubscribeTest(subscribeCall, handler, multiple)).run(api: api, validationFlags: flags).installed()
    }

    private void runScript(String subscribeCall, List<Flags> validationFlags = [], boolean multiple = false) {
        runScript(subscribeCall, "def evtHandler(evt) {}", validationFlags, multiple)
    }

    static final private def validLocationEvents = ["mode", "position", "sunset", "sunrise", "sunriseTime", "sunsetTime"]

    static private final def badHandlerCases = [
            ["subscribe(location, evtHandler)"                               , ""                               , "can't be called with null event handler" ],
            ["subscribe(device1, 'coolingSetpoint', evtHandler, evtHandler)" , ""                               , "can't be called with null event handler" ],
            ["subscribe(location, evtHandler)"                               , "def evtHandler(def a, def b){}" , "refers to method 'evtHandler' which does not exist in the script (method must one arg)" ],
            ["subscribe(device1, 'coolingSetpoint', evtHandler)"             , "def evtHandler(def a, def b){}" , "refers to method 'evtHandler' which does not exist in the script (method must one arg)" ],
            ["subscribe(location, evtHandler)"                               , "def evtHandler(int a){}"        , "refers to method 'evtHandler' which does not exist in the script (method must one arg)" ],
            ["subscribe(location, null)"                                     , "def evtHandler(evt){}"          , "subscribe(Mock for type 'Location', null) can't be called with null event handler" ]
    ].collect{[subscribeCall: it[0], handler: it[1], expectedError: it[2]]}

    static private final def badSubscriptionsAndExpectedErrors = [
            ["subscribe(null, 'action1', evtHandler)", "object being subscribe()'d to is null", [Flags.DontValidateSubscriptions]],
            ["subscribe(null, evtHandler)", "object being subscribe()'d to is null", [Flags.DontValidateSubscriptions]],
            ["subscribe(number1, 'action1', evtHandler)", "object 0 is not a valid input (not a device) to subscribe to. Note: subscribe(app) is not supported", [Flags.DontValidateSubscriptions]],
            ["subscribe(number1, '', evtHandler)", "event/attribute/value name parameter can't be null or empty", [Flags.DontValidateSubscriptions]],
            ["subscribe(number1, null, evtHandler)", "event/attribute/value name parameter can't be null or empty", [Flags.DontValidateSubscriptions]],
            ["subscribe(device1, 'badEvent', evtHandler)", "device 'label_generated_from_device1' does not contain attribute 'badEvent'. Valid attributes are: [", [Flags.DontValidateSubscriptions, Flags.AllowAnyDeviceAttributeOrCapabilityInSubscribe]],
            ["subscribe(device1, 'voltage', evtHandler)", "device 'label_generated_from_device1' does not contain attribute 'voltage'. Valid attributes are: [", [Flags.DontValidateSubscriptions, Flags.AllowAnyDeviceAttributeOrCapabilityInSubscribe, Flags.AllowAnyExistingDeviceAttributeOrCapabilityInSubscribe]],
            ["subscribe(device1, 'thermostatMode.unsupportedMode', evtHandler)", "'thermostatMode' for device 'label_generated_from_device1' does not have value 'unsupportedMode'. Valid values are: [", [Flags.DontValidateSubscriptions, Flags.AllowAnyDeviceAttributeOrCapabilityInSubscribe, Flags.AllowAnyAttributeEnumValuesInSubscribe]],
            ["subscribe(location, 'badLocationEvent', evtHandler)", "'badLocationEvent' is not a valid event for location. Valid values are: ${(validLocationEvents as HashSet<String>).inspect()}", [Flags.DontValidateSubscriptions]],
            ["subscribe(app, evtHandler)", "'InstalledAppWrapper' is not a valid input (not a device) to subscribe to. Note: subscribe(app) is not supported", [Flags.DontValidateSubscriptions]],
            ["subscribe(device1, evtHandler)", "when subscribing to device event, you need to specify at least an event name", [Flags.DontValidateSubscriptions]]
    ].collect{[subscribeCall: it[0], expectedError: it[1], willNotFailWithFlags: (it[2] as HashSet)]}

    static final def successfulTestCases = [
           [ "subscribe(device1, 'coolingSetpoint', evtHandler)"            , "it's a device with correct event" ],
           [ "subscribe(device1, 'thermostatMode', evtHandler)"             , "it's a device with correct event" ],
           [ "subscribe(device1, 'thermostatMode.cool', evtHandler)"        , "it's a device with correct event and value" ],
           [ "subscribe(device1, 'thermostat', evtHandler)"                 , "it's a device and correct capability specified" ],
           [ "subscribe(device1, 'thermostatCoolingSetpoint', evtHandler)"  , "it's a device and correct sub-capability specified" ],
           [ "subscribe(device1, 'voltageMeasurement', evtHandler)"         , "it's a device and correct separate capability specified" ],
           [ "subscribe(device1, 'TherMOstatMode.CoOL', evtHandler)"        , "it's a device with correct event and value, even if case is different" ],
           [ "subscribe(location, evtHandler)"                              , "it's a location (with no event)" ],
           [ "subscribe(location, '${validLocationEvents[0]}', evtHandler)" , "it's a location with correct event '${validLocationEvents[0]}'" ],
           [ "subscribe(location, '${validLocationEvents[3]}', evtHandler)" , "it's a location with correct event '${validLocationEvents[3]}'" ],
           [ "subscribe(device1, 'thermostatMode', { evtHandler() } )"      , "Event handler being a closure also works" ],
           [ "subscribe(location, { log.info(evt.name) } )"                 , "Event handler being a closure also works for location" ],
           [ "subscribe(getChildDevices()[0], 'thermostat', evtHandler)"      , "it's a ChildDeviceWrapper and correct capability specified" ],
    ].collect{[subscribeCall: it[0], explanation: it[1]]}

    static private def makeScriptForSubscribeTest(String subscribeCall, String eventHandler, boolean multiple = false) {
        """
preferences {
    page(name:"mainPage", title:"Settings", install: true, uninstall: true) {
        section() {
            input (name:"device1", type: "capability.thermostat", title: "Thermostat", required: true, multiple: ${multiple})
            input (name:"number1", type: "number", title: "Number", required: true, multiple: ${multiple})
        }
    }
}

def installed() {
    ${subscribeCall}
    true
}

${eventHandler}
"""
    }

    @Unroll
    def "#subscribeCall will normally fail with '#expectedError'"() {
        when:
            runScript(subscribeCall)

        then:
            AssertionError e = thrown()
            e.message.contains(expectedError)

        where:
            subscribeCall << badSubscriptionsAndExpectedErrors.collect { it.subscribeCall }
            expectedError << badSubscriptionsAndExpectedErrors.collect { it.expectedError }
    }

    @Unroll
    def "#subscribeCall will normally fail with '#expectedError' when multiple=true"() {
        when:
            runScript(subscribeCall, [], true)

        then:
            AssertionError e = thrown()
            e.message.contains(expectedError)

        where: // Just choose a few cases that actually use inputs
            subscribeCall << [2,5,6,7].collect{badSubscriptionsAndExpectedErrors[it].subscribeCall}
            expectedError << [2,5,6,7].collect{badSubscriptionsAndExpectedErrors[it].expectedError}
    }

    private static List<Map<String, Object>> badCasesThatWorkWithFlag(Flags flag) {
        badSubscriptionsAndExpectedErrors.findAll{it.willNotFailWithFlags.contains(flag)}.collect{[subscribeCall: it.subscribeCall as String, flag: flag]}
    }

    @CompileStatic
    private static List<Map<String, Object>> makeBadCasesWorkingWithAllFlags() {
        List<Map<String, Object>> result = []

        result.addAll(badCasesThatWorkWithFlag(Flags.DontValidateSubscriptions))
        result.addAll(badCasesThatWorkWithFlag(Flags.AllowAnyAttributeEnumValuesInSubscribe))
        result.addAll(badCasesThatWorkWithFlag(Flags.AllowAnyDeviceAttributeOrCapabilityInSubscribe))
        result.addAll(badCasesThatWorkWithFlag(Flags.AllowAnyExistingDeviceAttributeOrCapabilityInSubscribe))

        result
    }

    @Unroll
    def "Bad #testCase.subscribeCall will be ignored with #testCase.flag"() {
        expect:
            runScript(testCase.subscribeCall, [testCase.flag])

        where:
            testCase << makeBadCasesWorkingWithAllFlags()
    }

    @Unroll
    def "subscribe() will succeed when called for #subscribeCall because #why"() {
        expect:
            runScript(subscribeCall)

        where:
            subscribeCall << successfulTestCases.collect{it.subscribeCall}
            why << successfulTestCases.collect{it.explanation}
    }

    @Unroll
    def "subscribe() will succeed when called for #subscribeCall because #why when device's multiple = true"() {
        expect:
            runScript(subscribeCall, [], true)

        where:
            subscribeCall << [0,2,10].collect{successfulTestCases[it].subscribeCall}
            why << [0,2,10].collect{successfulTestCases[it].explanation}
    }

    @Unroll
    def "subscribe() will succeed when called for #subscribeCall (#why) even when input validation disabled"() {
        expect:
            runScript(subscribeCall, [Flags.DontValidatePreferences])

        where:
            subscribeCall << successfulTestCases.collect{it.subscribeCall}
            why << successfulTestCases.collect{it.explanation}
    }

    @Unroll
    def "#subscribeCall will fail for '#handler' with '#expectedError'"() {
        when:
            runScript(subscribeCall, handler)

        then:
            AssertionError error = thrown()
            error.message.contains(expectedError)

        where:
            subscribeCall << badHandlerCases.collect{it.subscribeCall}
            handler << badHandlerCases.collect{it.handler}
            expectedError << badHandlerCases.collect{it.expectedError}
    }

    @Unroll
    def "Bad handlers will still fail even with #flag"() {
        when:
            // Just picking any representative failure case
            runScript(badHandlerCases[2].subscribeCall, badHandlerCases[2].handler)

        then:
            AssertionError error = thrown()
            error.message.contains(badHandlerCases[2].expectedError)

        where:
            flag << [
                Flags.AllowAnyExistingDeviceAttributeOrCapabilityInSubscribe,
                Flags.AllowAnyDeviceAttributeOrCapabilityInSubscribe,
                Flags.AllowAnyAttributeEnumValuesInSubscribe
            ]
    }
}