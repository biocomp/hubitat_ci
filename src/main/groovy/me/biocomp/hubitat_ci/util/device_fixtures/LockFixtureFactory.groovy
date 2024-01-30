package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.app.preferences.DeviceInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues

import me.biocomp.hubitat_ci.capabilities.Lock

/**
 * Factory for creating fixtures of smartlock devices that behave like real locks in Hubitat.
 * These devices can be used in testing of app scripts, by passing them as inputs.
 * They also maintain state, and send events when their state changes.
 */
class LockFixtureFactory {
    /**
    * Constructs a new instance of a lock device fixture.
    */
    static def create(String name) {
        def deviceInputValueFactory = new DeviceInputValueFactory([Lock])

        def lockDevice = deviceInputValueFactory.makeInputObject(name, 't',  DefaultAndUserValues.empty(), false)

        def lockMetaClass = lockDevice.getMetaClass()

        // Calling initialize attaches behavior involving commands, state maintenance, and sending events.
        lockMetaClass.initialize = { appExecutor, state ->
            lockMetaClass.state = state
            lockMetaClass.state.commandsToIgnore = 0

            // Note that in a real device, it takes time for the lock to move when you send any of these commands.
            // So the resulting attribute change events won't be sent out instantaneously.
            // However, for the purposes of app testing, we will approximate the behavior by reporting the results
            // instantaneously.
            lockMetaClass.lock = {
                if (state.commandsToIgnore > 0) {
                    state.commandsToIgnore--
                    return
                }

                state.lock = "locked"
                appExecutor.sendEvent(lockDevice, [name: "lock", value: "locked"])
            }
            lockMetaClass.unlock = {
                if (state.commandsToIgnore > 0) {
                    state.commandsToIgnore--
                    return
                }

                state.lock = "unlocked"
                appExecutor.sendEvent(lockDevice, [name: "lock", value: "unlocked"])
            }

            // For the purposes of testing, I'm adding a public method to the fixture.
            // It's not part of the Lock capability, but it allows us to control the
            // behavior of the fixture for testing purposes.
            lockMetaClass.setCommandsToIgnore = { int commandsToIgnore ->
                state.commandsToIgnore = commandsToIgnore < 0 ? 0 : commandsToIgnore
            }
        }

        return lockDevice
    }
}
