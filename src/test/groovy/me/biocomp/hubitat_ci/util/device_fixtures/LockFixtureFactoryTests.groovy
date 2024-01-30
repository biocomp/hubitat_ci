package me.biocomp.hubitat_ci.util.device_fixtures

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.DeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.util.device_fixtures.LockFixtureFactory

import spock.lang.Specification

class LockFixtureFactoryTests extends Specification {
    def log = Mock(Log)

    def appExecutor = Mock(AppExecutor) {
        _*getLog() >> log
    }

    void "Lock can lock"() {
        given:
        def lockFixture = LockFixtureFactory.create('n')
        lockFixture.initialize(appExecutor, [lock: "unlocked"])

        when:
        lockFixture.lock()

        then:
        1*appExecutor.sendEvent(lockFixture, [name: "lock", value: "locked"])
        lockFixture.state.lock == "locked"
    }

    void "Lock can unlock"() {
        given:
        def lockFixture = LockFixtureFactory.create('n')
        lockFixture.initialize(appExecutor, [lock: "locked"])

        when:
        lockFixture.unlock()

        then:
        1*appExecutor.sendEvent(lockFixture, [name: "lock", value: "unlocked"])
        lockFixture.state.lock == "unlocked"
        lockFixture.state.doubleTapped == null
    }

    void "We can set the lock fixture to be unresponsive for a bit"() {
        given:
        def lockFixture = LockFixtureFactory.create('n')
        lockFixture.initialize(appExecutor, [lock: "unlocked"])

        and: "We tell the fixture to ignore two commands"
        lockFixture.state.commandsToIgnore = 2

        when: "We try to lock it twice"
        lockFixture.lock()
        lockFixture.lock()

        then: "The lock should still be unlocked"
        0*appExecutor.sendEvent(lockFixture, [name: "lock", value: "locked"])
        lockFixture.state.lock == "unlocked"

        when: "We try a third time"
        lockFixture.lock()

        then: "The lock should be locked"
        1*appExecutor.sendEvent(lockFixture, [name: "lock", value: "locked"])
        lockFixture.state.lock == "locked"
    }

    void "Can call refresh"() {
        given:
        def lockFixture = LockFixtureFactory.create('n')
        lockFixture.initialize(appExecutor, [lock: "unlocked"])

        when:
        lockFixture.refresh()

        then:
        lockFixture.state.lock == "unlocked"
    }

    void "If requireRefresh is set, then commands don't report results immediately"() {
        given:
        def lockFixture = LockFixtureFactory.create('n')
        lockFixture.initialize(appExecutor, [lock: "unlocked"])

        and: "We set requireRefresh to true"
        lockFixture.state.requireRefresh = true

        when: "We send the lock a lock command"
        lockFixture.lock()

        then: "The lock does not report back its results immediately"
        0*appExecutor.sendEvent(lockFixture, [name: "lock", value: "locked"])
        lockFixture.state.lock == "unlocked"

        when: "We send the lock a refresh command"
        lockFixture.refresh()

        then: "The lock reports back its results"
        1*appExecutor.sendEvent(lockFixture, [name: "lock", value: "locked"])
        lockFixture.state.lock == "locked"
    }
}