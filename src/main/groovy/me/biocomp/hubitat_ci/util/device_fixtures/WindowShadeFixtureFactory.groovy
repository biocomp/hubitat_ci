package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.app.preferences.DeviceInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues

import me.biocomp.hubitat_ci.capabilities.WindowShade
import me.biocomp.hubitat_ci.capabilities.SwitchLevel

/**
 * Factory for creating fixtures of window shade devices that behave like real shades in Hubitat.
 * These fixtures can be used in testing of app scripts, by passing them as inputs.
 */
class WindowShadeFixtureFactory {
    /**
    * Constructs a new instance of a dimmer device fixture.
    */
    static def create(String name) {
        def deviceInputValueFactory = new DeviceInputValueFactory([WindowShade])

        def fixture = deviceInputValueFactory.makeInputObject(name, 't',  DefaultAndUserValues.empty(), false)

        def fixtureMetaClass = fixture.getMetaClass()

        // Calling initialize attaches behavior involving commands, state maintenance, and sending events.
        fixtureMetaClass.initialize = { appExecutor, state ->
            fixtureMetaClass.state = state

            // Note that in a real device, it takes time for the shade to move when you send any of these commands.
            // So the resulting attribute change events won't be sent out instantaneously.
            // However, for the purposes of app testing, we will approximate the behavior by reporting the results
            // instantaneously.
            fixtureMetaClass.close = {
                state.position = 0
                appExecutor.sendEvent(fixture, [name: "position", value: 0])

                state.windowShade = "closed"
                appExecutor.sendEvent(fixture, [name: "windowShade", value: "closed"])
            }
            fixtureMetaClass.open = {
                state.position = 100
                appExecutor.sendEvent(fixture, [name: "position", value: 100])

                state.windowShade = "open"
                appExecutor.sendEvent(fixture, [name: "windowShade", value: "open"])
            }
            fixtureMetaClass.setPosition = { int position ->
                state.position = position
                appExecutor.sendEvent(fixture, [name: "position", value: position])

                if (position == 0) {
                    state.windowShade = "closed"
                    appExecutor.sendEvent(fixture, [name: "windowShade", value: "closed"])
                } else if (position == 100) {
                    state.windowShade = "open"
                    appExecutor.sendEvent(fixture, [name: "windowShade", value: "open"])
                } else {
                    state.windowShade = "partially open"
                    appExecutor.sendEvent(fixture, [name: "windowShade", value: "partially open"])
                }
            }
            fixtureMetaClass.startPositionChange = { direction ->
                if (direction == "open") {
                    open()
                } else if (direction == "close") {
                    close()
                }
            }
            fixtureMetaClass.stopPositionChange = {
                // Do nothing
            }
        }

        return fixture
    }
}
