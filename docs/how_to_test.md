# How to test
## Spock framework
I developed hubitat_ci using [Spock](http://spockframework.org/) framework, and I'm going to be showing examples with it too.

It doesn't matter what you use for your testing though.

## Setting up the test
[Getting started instructions](getting_started.md)

## Testing an app
Test below parses `appscript.groovy` file, creates a script object out of it and performs basic validations.

This happens when `sandbox.run()` method is executed.
```groovy
package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import spock.lang.Specification

class Test extends Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("appscript.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run() // Runs and validates definition() and preferences().
    }
}
```

## Testing a device driver
It's the same as testing an app, but you need to use `HubitatDeviceSandbox`:
```groovy
class HowToTestDevice extends Specification
{
    HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox(new File("device_script.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run()
    }
}
```
## Mocking calls to Hubitat APIs
`run()` method of `HubitatAppSandbox` and `HubitatDeviceSandbox` takes map of extra parameters. 

One of them is `api`. It should be implementation of `me.biocomp.hubitat_ci.api.app_api.AppExecutor` or `me.biocomp.hubitat_ci.api.device_api.DeviceExecutor` interfaces for app or device respectively. They contain all the supported Hubitat methods, and can be mocked via Spock standard methods. 

Produced script object will redirect calls to the unknown apis to this `api` object.

Given that `app_script.groovy` script has method (and `getHubUID()` is a method provided by Hubitat):
```groovy
def myHubUIdMethod()
{
    return getHubUID()
}
```

The test can:
```groovy
def "Mocking calls to Hubitat APIs"()
{
    setup:
        AppExecutor executorApi = Mock{
            1*getHubUID() >> "Mocked UID" // Method mocked here
        }

        def script = new HubitatAppSandbox(new File("app_script.groovy"))
            .run(api: executorApi)

    expect:
        script.myHubUIdMethod() == "Mocked UID"
}
```

## Mocking calls to internal scripts methods
This can be done by updating script's object metaclass with mock implementation of the methods.

The script object is returned from `HubitatAppSandbox`/`HubitatAppSandbox`'s `run()` method.

In order to update its metaclass before returning from run(), use another `run()` parameter: `customizeScriptBeforeRun`. It takes a closure that receives a script object to be updated.

Given that `app_script.groovy` script has:
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

You can test it this way:
```groovy
def "Mocking calls to internal scripts methods "()
{
    setup:
        def script = new HubitatAppSandbox(new File("app_script.groovy"))
                .run(customizeScriptBeforeRun: {
                    script->
                        script.getMetaClass().scriptPrivateInternalMethod = 
                            { return "Mocked data!" } // Method mocked here
                })

    expect:
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

You can mock the state:
```groovy
def "Mocking app/device state"()
{
    setup:
        def state = [val1:42, val2:"Some value2"] // Defining state map

        // This is example for device
        DeviceExecutor executorApi = Mock{
            _*getState() >> state // State mocked here
        }

        def script = new HubitatDeviceSandbox(new File("device_script.groovy"))
                .run(api: executorApi)

    expect:
        script.readSomeState("val1") == 42
        script.readSomeState("val2") == "Some value2"
        script.readSomeState("invalid") == null
}
```

## You can disable most built-in validations
Yet another parameter to `run()` method is `validationFlags` that takes list of `me.biocomp.hubitat_ci.validation.Flags`.

These flags can selectively disable many of the validations, including not running any of them, and not running script's initialization.

Built-in validations can be wrong, because I've mostly derived them from SmartThings, Hubitat documentation and browsing reflected Hubitat APIs. This is not nearly enough to know for sure what is right and wrong. I hope validations are good enough.

Here are examples of some flag usage:
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

## You can also test inline script (not loaded from a file)
Although this is probably not useful for most script writers.
```groovy
class HowToTestDeviceWithInlineScript extends
        Specification
{
    def "You can have script code inside the test"() {
        setup:
            HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox("""
metadata {
    definition(name: "Min Driver", namespace: "hubitat_ci", author: "biocomp") {
        capability "Actuator"
    }

    preferences() {
        input "Input", "number", title: "Val"
    }
}

def parse() {}
            """)

        expect:
            sandbox.run()
    }
}
```