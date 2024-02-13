package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.util.integration.IntegrationScheduler
import me.biocomp.hubitat_ci.util.integration.TimeKeeper
import me.biocomp.hubitat_ci.util.integration.TimeChangedEvent
import me.biocomp.hubitat_ci.util.integration.TimeChangedListener

import spock.lang.Specification

interface TestListener {
    void handler(Map data)
}

class IntegrationSchedulerTriggeringTests extends Specification {
    IntegrationScheduler scheduler
    TestListener listener

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
        TimeKeeper.removeAllListeners()
        TimeKeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:46"))    // This time is chosen to not be on a minute boundary

        listener = Mock(TestListener)
        scheduler = new IntegrationScheduler()
        scheduler.setHandlingObject(listener)
    }

    def cleanup() {
        TimeKeeper.removeAllListeners()
    }

    def "runEvery1Minute will cause the handler to trigger once if you advance the timekeeper across a minute boundary"() {
        given:
            scheduler.runEvery1Minute("handler")
        when:
            TimeKeeper.advanceMinutes(1)
        then:
            1 * listener.handler(_ as Map)
    }

    def "runEvery1Minute will not cause the handler to trigger if you advance the timekeeper a tiny amount that doesn't cross a minute boundary"() {
        given:
            scheduler.runEvery1Minute("handler")
        when:
            TimeKeeper.advanceSeconds(1)
        then:
            0 * listener.handler(_ as Map)
    }

    def "runEvery1Minute will cause the handler to trigger twice if you advance the timekeeper across two minute boundaries"() {
        given:
            scheduler.runEvery1Minute("handler")
        when:
            TimeKeeper.advanceMinutes(2)
        then:
            2 * listener.handler(_ as Map)
    }

    def "runEvery1Minute will cause the handler to trigger ten times if you advance the timekeeper across ten minute boundaries"() {
        given:
            scheduler.runEvery1Minute("handler")
        when:
            TimeKeeper.advanceMinutes(10)
        then:
            10 * listener.handler(_ as Map)
    }

    def "runEvery5Minutes will cause the handler to trigger once if you advance the timekeeper across a five minute boundary"() {
        given:
            scheduler.runEvery5Minutes("handler")
        when:
            TimeKeeper.advanceMinutes(5)
        then:
            1 * listener.handler(_ as Map)
    }

    def "runEvery5Minutes will not cause the handler to trigger if you advance the timekeeper a tiny amount that doesn't cross a five minute boundary"() {
        given:
            scheduler.runEvery5Minutes("handler")
        when:
            TimeKeeper.advanceMinutes(1)
        then:
            0 * listener.handler(_ as Map)
    }

    def "runEvery5Minutes will cause the handler to trigger twice if you advance the timekeeper across two five minute boundaries"() {
        given:
            scheduler.runEvery5Minutes("handler")
        when:
            TimeKeeper.advanceMinutes(10)
        then:
            2 * listener.handler(_ as Map)
    }

    def "runEvery1Hour will cause the handler to trigger once if you advance the timekeeper across a one hour boundary"() {
        given:
            scheduler.runEvery1Hour("handler")
        when:
            TimeKeeper.advanceHours(1)
        then:
            1 * listener.handler(_ as Map)
    }

    def "runInMillis will cause the handler to trigger once if you advance the timekeeper across the specified number of milliseconds"() {
        given:
            scheduler.runInMillis(10, "handler")
        when:
            TimeKeeper.advanceMillis(11)
        then:
            1 * listener.handler(_ as Map)
    }

    def "runInMillis will not trigger a second time if you advance double the millis.  Should still just be once."() {
        given:
            scheduler.runInMillis(10, "handler")
        when:
            TimeKeeper.advanceMillis(11)
            TimeKeeper.advanceMillis(11)
        then:
            1 * listener.handler(_ as Map)
    }

    def "runInMillis will not trigger if you advance the timekeeper a tiny amount that doesn't cross the millis boundary"() {
        given:
            scheduler.runInMillis(10, "handler")
        when:
            TimeKeeper.advanceMillis(1)
        then:
            0 * listener.handler(_ as Map)
    }

    def "runIn will trigger once if you advance the timekeeper across the specified number of seconds"() {
        given:
            scheduler.runIn(10, "handler")
        when:
            TimeKeeper.advanceSeconds(11)
        then:
            1 * listener.handler(_ as Map)
    }

    def "runIn will not trigger a second time if you advance double the seconds.  Should still just be once."() {
        given:
            scheduler.runIn(10, "handler")
        when:
            TimeKeeper.advanceSeconds(11)
            TimeKeeper.advanceSeconds(11)
        then:
            1 * listener.handler(_ as Map)
    }

    def "runIn will not trigger if you advance the timekeeper a tiny amount that doesn't cross the seconds boundary"() {
        given:
            scheduler.runIn(10, "handler")
        when:
            TimeKeeper.advanceSeconds(1)
        then:
            0 * listener.handler(_ as Map)
    }

    def "schedule will trigger once if you advance TimeKeeper past the specified time"() {
        given:
            TimeKeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:46"))
            scheduler.schedule(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:25:00"), "handler")
        when:
            TimeKeeper.advanceMinutes(2)
        then:
            1 * listener.handler(_ as Map)
    }

    def "schedule will not trigger if you advance TimeKeeper to just before the specified time"() {
        given:
            TimeKeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:46"))
            scheduler.schedule(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:25:00"), "handler")
        when:
            TimeKeeper.advanceMinutes(1)
        then:
            0 * listener.handler(_ as Map)
    }

    def "schedule will trigger again at the same time the next day, so you'll get two triggers if you go past two boundaries"() {
        given:
            TimeKeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:46"))
            scheduler.schedule(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:25:00"), "handler")
        when:
            TimeKeeper.advanceMinutes(2)
            TimeKeeper.advanceDays(1)
        then:
            2 * listener.handler(_ as Map)
    }

}