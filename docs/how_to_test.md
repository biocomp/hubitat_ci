# How to test
## Spock framework
I developed hubitat_ci using [Spock](http://spockframework.org/) framework, and I'm going to be showing examples with it too.

It doesn't matter what you use for your testing though.

## Setting up the test
[Getting started instructions](getting_started.md)

## Testing an app
Test below parses [appscript.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/minimal/appscript.groovy) file, creates a script object out of it and performs basic validations.

This happens when `sandbox.run()` method is executed.
```groovy
package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import spock.lang.Specification

class Test extends Specification
{
    // Creating a sandbox object for device script from file.
    // At this point, script object is not created.
    // Using Hubitat**App**Sandbox for app scripts.
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("appscript.groovy"))

    def "Basic validation"() {
        expect:
            // Compile, construct script object, and validate definition() and preferences()
            sandbox.run()
    }
}
```

## Testing a device driver
It's the same as testing an app, but you need to use `HubitatDeviceSandbox`. 

See [HowToTestDevice.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/src/HowToTestDevice.groovy) and [device_script.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/device_script.groovy) for full source.
```groovy
class HowToTestDevice extends Specification
{
    // Creating a sandbox object for device script from file.
    // At this point, script object is not created.
    // Using Hubitat**Device**Sandbox for app scripts.
    HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox(new File("device_script.groovy"))

    def "Basic validation"() {
        expect:
            // Compile, construct script object, and initialize metadata
            final def script = sandbox.run()

            // Call method defined in the script
            script.parse("Message") == [source: "Message", result: 42]
    }
}
```
## Mocking calls to Hubitat APIs
`run()` method of `HubitatAppSandbox` and `HubitatDeviceSandbox` takes map of extra parameters. 

One of them is `api`. It should be implementation of `me.biocomp.hubitat_ci.api.app_api.AppExecutor` or `me.biocomp.hubitat_ci.api.device_api.DeviceExecutor` interfaces for app or device respectively. They contain all the supported Hubitat methods, and can be mocked via Spock standard methods. 

Produced script object will redirect calls to the unknown apis to this `api` object.

Given that [app_script.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/app_script.groovy) script has method (and `getHubUID()` is a method provided by Hubitat):
```groovy
def myHubUIdMethod()
{
    return getHubUID()
}
```

The test ([Mocking.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/src/Mocking.groovy)) can:
```groovy
def "Mocking calls to Hubitat APIs"()
{
    setup:
        AppExecutor executorApi = Mock{
            // Method will be called 1 time and will return 'Mocked UID'
            1*getHubUID() >> "Mocked UID" // Method mocked here
        }

        def script = new HubitatAppSandbox(new File("app_script.groovy"))
                .run(api: executorApi)

    expect:
        // Calling method defined in the script
        script.myHubUIdMethod() == "Mocked UID"
}
```

## Mocking calls to internal scripts methods
This can be done by updating script's object metaclass with mock implementation of the methods.

The script object is returned from `HubitatAppSandbox`/`HubitatAppSandbox`'s `run()` method.

In order to update its metaclass before returning from run(), use another `run()` parameter: `customizeScriptBeforeRun`. It takes a closure that receives a script object to be updated.

Given that [app_script.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/app_script.groovy) script has:
```groovy
private def scriptPrivateInternalMethod()
{
    return "Some real data"
}

def methodThatCallsPrivateMethod()
{
    return scriptPrivateInternalMethod()
}
```

You can test ([Mocking.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/src/Mocking.groovy)) it this way:
```groovy
def "Mocking calls to internal scripts methods"()
{
    setup:
        def script = new HubitatAppSandbox(new File("app_script.groovy"))
                .run(customizeScriptBeforeRun: {
                    script->
                        script.getMetaClass().scriptPrivateInternalMethod =
                                { return "Mocked data!" } // Method mocked here
                })

    expect:
        // Calling method defined in the script
        script.methodThatCallsPrivateMethod() == "Mocked data!"
}
```

Of course, this can also be used to mock API methods instead of the way mentioned above.
## Mocking app/device state
You can mock access to `state`.

Given an app or device method (example is for device here):
```groovy
def readSomeState(def name)
{
    return state."${name}"
}
```

You can mock the state ([Mocking.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/src/Mocking.groovy)):
```groovy
def "Mocking app/device state"()
{
    setup:
        // Defining state map
        def state = [val1:42, val2:"Some value2"] 

        // This is example for device
        DeviceExecutor executorApi = Mock{
            _*getState() >> state // State mocked here
        }

        def script = new HubitatDeviceSandbox(new File("device_script.groovy"))
                .run(api: executorApi)

    expect:
        // Calling methods defined in the script
        script.readSomeState("val1") == 42
        script.readSomeState("val2") == "Some value2"
        script.readSomeState("invalid") == null
}
```

## You can disable most built-in validations
Yet another parameter to `run()` method is `validationFlags` that takes list of `me.biocomp.hubitat_ci.validation.Flags`.

These flags can selectively disable many of the validations, including not running any of them, and not running script's initialization.

Built-in validations can be wrong, because I've mostly derived them from SmartThings, Hubitat documentation and browsing reflected Hubitat APIs. This is not nearly enough to know for sure what is right and wrong. I hope validations are good enough.

Here are examples of some flag usage. Source code here: [DisablingValidations.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/src/DisablingValidations.groovy), [bad_app_script.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/bad_app_script.groovy)
```groovy
def "You can disable most built-in validations"()
{
    expect:
        new HubitatAppSandbox(new File("bad_app_script.groovy"))
            .run(validationFlags:
            [Flags.AllowNullEnumInputOptions, // Enum should have 'options', but doesn't in this case
             Flags.AllowNullListOptions, // Same reason - for enum
             Flags.DontValidateDefinition]) // definition() is completely omitted in this script
}
```

```groovy
def "Don't run init at all"()
{
    expect:
        new HubitatAppSandbox(new File("bad_app_script.groovy"))
                .run(validationFlags: [Flags.DontRunScript])
}
```
## What happens when script uses things like `hubitat.device.HubResponse`
These classes are replaced at script compile time with mock ones like `me.biocomp.hubitat_ci.api.common_api.HubResponse`.

See replacement list [here](https://github.com/biocomp/hubitat_ci/blob/master/src/main/groovy/me/biocomp/hubitat_ci/util/SandboxClassLoader.groovy) (`mapClassName` method).

## You can validate data generated during app script's setup
When app is set up, its initialization is verified. But it's also captured and can be queried in your test.

Source code: [ValidatingInputsAndProperties.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/src/ValidatingInputsAndProperties.groovy), [more_complex_app.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/more_complex_app.groovy).

```groovy
def "You can validate data generated during app script's setup"()
{
    given:
        final def script = new HubitatAppSandbox(new File("more_complex_app.groovy")).run()

    expect:
        // Check definition
        script.getProducedDefinition().description == "A little more complex app"
        script.getProducedDefinition().name == "ComplexApp"

        // Call mapped actions
        script.getProducedMappings()['/p'].actions.GET(123, "I'm a request") == "handlerForGet called with params = '123', request = 'I'm a request'"
        script.getProducedMappings()['/p/null'].actions.PUT("I'm params", "Another request") == "handlerForPut2 called with params = 'I'm params', request = 'Another request'"

        // Check preferences
        // Using readName() which will return name regardless of whether it was passed separately or inside 'options'
        script.getProducedPreferences().pages[0].readName() == "page3" // The only non-dynamic page
        script.getProducedPreferences().dynamicPages[0].readName() == "main"
        script.getProducedPreferences().dynamicPages[1].readName() == "page2"


        // All pages in order of definition:
        script.getProducedPreferences().allPages[0].readName() == "main"
        script.getProducedPreferences().allPages[1].readName() == "page2"
        script.getProducedPreferences().allPages[2].readName() == "page3"


        // main page, section "About this app"
        script.getProducedPreferences().allPages[0].sections[0].title == "About This App"
        (script.getProducedPreferences().allPages[0].sections[0].children[0] as Paragraph).text == "Some huge help text"

        // main page, section "Menu"
        script.getProducedPreferences().allPages[0].sections[1].title == "Menu"

        // Note: nextPageName will be null, if it was put into 'options'. Then it would need to be read through options.
        (script.getProducedPreferences().allPages[0].sections[1].children[0] as HRef).nextPageName == "page2"
        (script.getProducedPreferences().allPages[0].sections[1].children[0] as HRef).options.description == "Tap to see page 2"
        (script.getProducedPreferences().allPages[0].sections[1].children[0] as HRef).options.params == [refresh: true]


        // page2, section "Timeout"
        script.getProducedPreferences().allPages[1].sections[0].title == "Timeout"

        // readName() and readType() will always return name and type whether or not they were put into a map or provided separately
        (script.getProducedPreferences().allPages[1].sections[0].children[0] as Input).readName() == "timer"
        (script.getProducedPreferences().allPages[1].sections[0].children[0] as Input).readType() == "number"
        (script.getProducedPreferences().allPages[1].sections[0].children[0] as Input).options.title == "A timeout"

        // page2, section "Times to check"
        script.getProducedPreferences().allPages[1].sections[1].title == "Times to check"
        (script.getProducedPreferences().allPages[1].sections[1].children[0] as Input).readName() == "checkFrequency"
        (script.getProducedPreferences().allPages[1].sections[1].children[0] as Input).options.required == false
        (script.getProducedPreferences().allPages[1].sections[1].children[0] as Input).options.options == [[1: "5 Minutes"],
                                                                                                            [2: "1 Hour"],
                                                                                                            [3: "3 Hours"]]

        // page3, unnamed section
        (script.getProducedPreferences().allPages[2].sections[0].children[0] as Input).readName() == "in31"
}
```

## You can validate data generated during app script's setup
When device is set up, its initialization is also verified. 
But it's too captured and can be queried in your test.

Source code: [ValidatingInputsAndProperties.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/src/ValidatingInputsAndProperties.groovy), [more_complex_device.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/more_complex_app.groovy)

```groovy
def "You can validate data generated during device script's setup"()
{
    given:
        final def script = new HubitatDeviceSandbox(new File("more_complex_device.groovy")).run()

    expect:
        script.getProducedPreferences()[0].readName() == "Input1"
        script.getProducedPreferences()[0].readType() == "text"

        script.getProducedPreferences()[1].readName() == "tempOffset"
        script.getProducedPreferences()[1].readType() == "number"
        script.getProducedPreferences()[1].options == [title: "Degrees", description: "Adjust temperature by this many degrees", range: "*..*", displayDuringSetup: false]

        script.getProducedDefinition().options.name == "My more complex driver"
        script.getProducedDefinition().options.namespace == "hubitat_ci_example"

        script.getProducedDefinition().capabilities == ["Actuator", "Button", "Switch"]
}
```
## You can also test inline script (not loaded from a file)
Although this is probably not useful for most script writers.
Source code here: [HowToTestDeviceWithInlineScript.groovy](https://github.com/biocomp/hubitat_ci_example/blob/master/how_to_test/src/HowToTestDeviceWithInlineScript.groovy).
```groovy
class HowToTestDeviceWithInlineScript extends
        Specification
{
    def "You can have script code inside the test"() {
        setup:
            // Creating sandbox object, but instead of loading script from a file, 
            // contents are directly provided.
            HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox("""
metadata {
    definition(name: "Min Driver", namespace: "hubitat_ci", author: "biocomp") {
        capability "Actuator"
    }

    preferences() {
        input "Input", "number", title: "Val"
    }
}

Map parse(String s) {}
            """)

        expect:
            // Compile, construct script object, and initialize metadata
            sandbox.run()
    }
}
```