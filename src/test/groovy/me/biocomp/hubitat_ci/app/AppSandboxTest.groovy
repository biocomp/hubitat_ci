package me.biocomp.hubitat_ci.app

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification
import spock.lang.Unroll

class AppSandboxTest extends Specification {
    // Other tests are done in AppAndDeviceSandboxTest

    static final private def validLocationEvents = ["mode", "position", "sunset", "sunrise", "sunriseTime", "sunsetTime"]

    static private final def badSubscriptionsAndExpectedErrors = [
            ["subscribe(null, 'action1', evtHandler)", "Object being subscribe()'d to is null"],
            ["subscribe(null, evtHandler)", "Object being subscribe()'d to is null"],
            ["subscribe(number1, 'action1', evtHandler)", "Object 0 is not a valid input (not a device) to subscribe to. Note: subscribe(app) is not supported"],
            ["subscribe(number1, '', evtHandler)", "event/attribute/value name parameter can't be null or empty"],
            ["subscribe(number1, null, evtHandler)", "event/attribute/value name parameter can't be null or empty"],
            ["subscribe(device1, 'badEvent', evtHandler)", "Device 'Thermostat' does not contain attribute 'badEvent'. Valid attributes are: ["],
            ["subscribe(device1, 'thermostatMode.unsupportedMode', evtHandler)", "'thermostatMode' for device 'Thermostat' does not have value 'unsupportedMode'. Valid values are: ["],
            ["subscribe(location, 'badLocationEvent', evtHandler)", "'badLocationEvent' is not a valid event for location. Valid values are: ${(validLocationEvents as HashSet<String>).inspect()}"],
            ["subscribe(app, evtHandler)", "'InstalledAppWrapper' is not a valid input (not a device) to subscribe to. Note: subscribe(app) is not supported"],
            ["subscribe(device1, evtHandler)", "when subscribing to device event, you need to specify at least the capability"]
    ];

    static private def makeScriptForSubscribeTest(String subscribeCall, String eventHandler = "def evtHandler(evt) {}") {
        """
preferences {
    page(name:"mainPage", title:"Settings", install: true, uninstall: true) {
        section() {
            input (name:"device1", type: "capability.thermostat", title: "Thermostat", required: true, multiple: false)
            input (name:"number1", type: "number", title: "Number", required: true, multiple: false)
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
    def "#subscribeCall will fail with '#message'"() {
        setup:
            final def script = makeScriptForSubscribeTest(subscribeCall)
            final def api = Mock(AppExecutor) {
                _ * getApp() >> Mock(InstalledAppWrapper)
                _ * getLocation() >> Mock(Location)
            }

        when:
            new HubitatAppSandbox(script).run(api: api, validationFlags: [Flags.DontValidateDefinition]).installed()

        then:
            AssertionError e = thrown()
            e.message.contains(message)

        where:
            subscribeCall << badSubscriptionsAndExpectedErrors.collect { it[0] }
            message << badSubscriptionsAndExpectedErrors.collect { it[1] }
    }

    @Unroll
    def "Bad #subscribeCall will be ignored with Flags.DontValidateSubscriptions"() {
        setup:
            final def script = makeScriptForSubscribeTest(subscribeCall)
            final def api = Mock(AppExecutor) {
                _ * getApp() >> Mock(InstalledAppWrapper)
            }

        expect:
            new HubitatAppSandbox(script).run(api: api, validationFlags: [Flags.DontValidateDefinition, Flags.DontValidateSubscriptions]).installed()

        where:
            subscribeCall << badSubscriptionsAndExpectedErrors.collect { it[0] }
    }

    @Unroll
    def "subscribe() will succeed when called for #subscribeCall because #why"() {
        setup:
            final def script = makeScriptForSubscribeTest(subscribeCall)
            final def api = Mock(AppExecutor) {
                _ * getLocation() >> Mock(Location)
            }

        expect:
            new HubitatAppSandbox(script).run(api: api, validationFlags: [Flags.DontValidateDefinition]).installed()

        where:
            subscribeCall                                                  | why
            "subscribe(device1, 'coolingSetpoint', evtHandler)"            | "it's a device with correct event"
            "subscribe(device1, 'thermostatMode', evtHandler)"             | "it's a device with correct event"
            "subscribe(device1, 'thermostatMode.cool', evtHandler)"        | "it's a device with correct event and value"
            "subscribe(location, evtHandler)"                              | "it's a location (with no event)"
            "subscribe(location, '${validLocationEvents[0]}', evtHandler)" | "it's a location with correct event '${validLocationEvents[0]}'"
            "subscribe(location, '${validLocationEvents[3]}', evtHandler)" | "it's a location with correct event '${validLocationEvents[3]}'"
            "subscribe(device1, 'thermostatMode', { evtHandler() } )"      | "Event handler being a closure also works"
            "subscribe(location, { log.info(evt.name) } )"                 | "Event handler being a closure also works for location"
    }

    @Unroll
    def "#subscribeCall will fail for '#handler' with '#errorText'"() {
        setup:
            final def script = makeScriptForSubscribeTest(subscribeCall, handler)
            final def api = Mock(AppExecutor) {
                _ * getLocation() >> Mock(Location)
            }

        when:
            new HubitatAppSandbox(script).run(api: api, validationFlags: [Flags.DontValidateDefinition]).installed()

        then:
            AssertionError error = thrown()
            error.message.contains(errorText)

        where:
            subscribeCall                                                   | handler                          || errorText
            "subscribe(location, evtHandler)"                               | ""                               || "can't be called with null event handler"
            "subscribe(device1, 'coolingSetpoint', evtHandler, evtHandler)" | ""                               || "can't be called with null event handler"
            "subscribe(location, evtHandler)"                               | "def evtHandler(def a, def b){}" || "refers to method 'evtHandler' which does not exist in the script (method must one arg)"
            "subscribe(device1, 'coolingSetpoint', evtHandler)"             | "def evtHandler(def a, def b){}" || "refers to method 'evtHandler' which does not exist in the script (method must one arg)"
            "subscribe(location, evtHandler)"                               | "def evtHandler(int a){}"        || "refers to method 'evtHandler' which does not exist in the script (method must one arg)"
            "subscribe(location, null)"                                     | "def evtHandler(evt){}"          || "subscribe(Mock for type 'Location', null) can't be called with null event handler"
    }
}