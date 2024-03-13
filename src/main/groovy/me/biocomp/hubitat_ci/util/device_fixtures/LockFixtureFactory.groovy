package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.app.preferences.DeviceInputValueFactory
import me.biocomp.hubitat_ci.validation.DefaultAndUserValues

import me.biocomp.hubitat_ci.capabilities.Lock
import me.biocomp.hubitat_ci.capabilities.Refresh

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
        def deviceInputValueFactory = new DeviceInputValueFactory([Lock, Refresh])

        def lockDevice = deviceInputValueFactory.makeInputObject(name)

        def lockMetaClass = lockDevice.getMetaClass()

        // Calling initialize attaches behavior involving commands, state maintenance, and sending events.
        lockMetaClass.initialize = { appExecutor, initialAttributeValues ->
            attributeValues = initialAttributeValues

            // A group of testing-only state variables, that allow us to simulate different aspects of real physical locks in our tests.
            //  - commandsToIgnore: This allows us to simulate a lock that is unresponsive for a bit.  It will ignore the specified number of commands before responding again.
            //  - requireRefresh: Some older smart locks don't immediately report back their state changes.  You have to give them a refresh command to get the latest state from them.
            //  - desiredState: If requireRefresh is set to true, this is the state we want the lock to be in after the refresh command is sent.
            lockMetaClass.state = [commandsToIgnore: 0, requireRefresh: false, desiredState: initialAttributeValues.lock]

            // Note that in a real device, it takes time for the lock to move when you send any of these commands.
            // So the resulting attribute change events won't be sent out instantaneously.
            // However, for the purposes of app testing, we will approximate the behavior by reporting the results
            // instantaneously.
            lockMetaClass.lock = {
                if (state.commandsToIgnore > 0) {
                    state.commandsToIgnore--
                    return
                }

                state.desiredState = "locked"

                if (!state.requireRefresh) {
                    attributeValues.lock = "locked"
                    appExecutor.sendEvent(lockDevice, [name: "lock", value: "locked"])
                }
            }
            lockMetaClass.unlock = {
                if (state.commandsToIgnore > 0) {
                    state.commandsToIgnore--
                    return
                }

                state.desiredState = "unlocked"

                if (!state.requireRefresh) {
                    attributeValues.lock = "unlocked"
                    appExecutor.sendEvent(lockDevice, [name: "lock", value: "unlocked"])
                }
            }
            lockMetaClass.refresh = { ->
                if (state.requireRefresh && state.desiredState != attributeValues.lock) {
                    attributeValues.lock = state.desiredState
                    appExecutor.sendEvent(lockDevice, [name: "lock", value: state.desiredState])
                }
            }

            // For the purposes of testing, I'm adding a few public methods to the fixture.
            // They are not actual commands in real Hubitat, but are useful for testing.
            lockMetaClass.setRequireRefresh = { boolean requireRefresh ->
                state.requireRefresh = requireRefresh
            }
            lockMetaClass.setCommandsToIgnore = { int commandsToIgnore ->
                state.commandsToIgnore = commandsToIgnore
            }
        }

        return lockDevice
    }
}
