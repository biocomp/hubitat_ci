package me.biocomp.hubitat_ci

import groovy.time.*

import me.biocomp.hubitat_ci.util.integration.TimeKeeper
import me.biocomp.hubitat_ci.util.integration.TimeChangedEvent
import me.biocomp.hubitat_ci.util.integration.TimeChangedListener

import spock.lang.Specification

class TimeKeeperTests extends Specification {
    def currentDate = new Date()

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))

        TimeKeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
        TimeKeeper.removeAllListeners()
    }

    def cleanup() {
        TimeKeeper.removeAllListeners()
    }

    void "Can use the TimeKeeper class to get now"() {
        when:
            Date date = TimeKeeper.now()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"
    }

    void "TimeKeeper doesn't affect creation of Date instances"() {
        when:
            def date = new Date()

        then:
            date.toString() == currentDate.toString()
    }

    void "Can advance the minutes of the TimeKeeper"() {
        when:
            def date = TimeKeeper.now()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when:
            TimeKeeper.advanceMinutes(5)
            def date2 = TimeKeeper.now()

        then:
            date2.toString() != currentDate.toString()
            date2.toString() == "Sun Aug 31 08:28:45 UTC 2014"
    }

    void "Can advance the seconds of the TimeKeeper"() {
        when:
            def date = TimeKeeper.now()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when:
            TimeKeeper.advanceSeconds(5)
            def date2 = TimeKeeper.now()

        then:
            date2.toString() != currentDate.toString()
            date2.toString() == "Sun Aug 31 08:23:50 UTC 2014"
    }

    void "Can advance the hours of the TimeKeeper"() {
        when:
            def date = TimeKeeper.now()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when:
            TimeKeeper.advanceHours(5)
            def date2 = TimeKeeper.now()

        then:
            date2.toString() != currentDate.toString()
            date2.toString() == "Sun Aug 31 13:23:45 UTC 2014"
    }

    void "Can advance the days of the TimeKeeper"() {
        when:
            def date = TimeKeeper.now()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when:
            TimeKeeper.advanceDays(5)
            def date2 = TimeKeeper.now()

        then:
            date2.toString() != currentDate.toString()
            date2.toString() == "Fri Sep 05 08:23:45 UTC 2014"
    }

    void "TimeKeeper will raise events to listeners"() {
        given: "A TimeKeeper with a listener registered"
            def listener = Mock(TimeChangedListener)
            TimeKeeper.addListener(listener)

        when: "A date is created from the TimeKeeper"
            def date = TimeKeeper.now()

        then: "It should be the date we set"
            date != currentDate
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when: "We advance time"
            TimeKeeper.advanceDays(5)

        then: "The listener should have received an event"
            1 * listener.timeChangedEventReceived(_) >> {
                TimeChangedEvent e  ->
                    e.source == TimeKeeper.class
                    e.oldTime.toString() == "Sun Aug 31 08:23:45 UTC 2014"
                    e.newTime.toString() == "Fri Sep 05 08:23:45 UTC 2014"
            }
    }

    void "Listeners can be unregistered from TimeKeeper"() {
        given: "A TimeKeeper with a registered listener"
            def listener = Mock(TimeChangedListener)
            TimeKeeper.addListener(listener)

        when: "A date is created from the TimeKeeper"
            def date = TimeKeeper.now()

        then: "It should be the date we set"
            date != currentDate
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when: "We unregister the listener"
            TimeKeeper.removeListener(listener)

        and: "We advance time"
            TimeKeeper.advanceDays(5)

        then: "The listener should not have received an event"
            0 * listener.timeChangedEventReceived(_)
    }

    void "TimeKeeper can have multiple listeners"() {
        given: "A TimeKeeper with two registered listeners"
            def listener1 = Mock(TimeChangedListener)
            def listener2 = Mock(TimeChangedListener)
            TimeKeeper.addListener(listener1)
            TimeKeeper.addListener(listener2)

        when: "A date is created from the TimeKeeper"
            def date = TimeKeeper.now()

        then: "It should be the date we set"
            date != currentDate
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when: "We advance time"
            TimeKeeper.advanceDays(5)

        then: "Both listeners should have received an event"
            1 * listener1.timeChangedEventReceived(_) >> {
                TimeChangedEvent e  ->
                    e.source == TimeKeeper.class
                    e.oldTime.toString() == "Sun Aug 31 08:23:45 UTC 2014"
                    e.newTime.toString() == "Fri Sep 05 08:23:45 UTC 2014"
            }
            1 * listener2.timeChangedEventReceived(_) >> {
                TimeChangedEvent e  ->
                    e.source == TimeKeeper.class
                    e.oldTime.toString() == "Sun Aug 31 08:23:45 UTC 2014"
                    e.newTime.toString() == "Fri Sep 05 08:23:45 UTC 2014"
            }
    }

    void "TimeKeeper will raise events to listeners"() {
        given: "A TimeKeeper with a listener registered"
            def listener = Mock(TimeChangedListener)
            TimeKeeper.addListener(listener)

        when: "A date is created from the TimeKeeper"
            def date = TimeKeeper.now()

        then: "It should be the date we set"
            date != currentDate
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when: "We advance time"
            TimeKeeper.advanceDays(5)

        then: "The listener should have received an event"
            1 * listener.timeChangedEventReceived(_) >> {
                TimeChangedEvent e  ->
                    e.source == TimeKeeper.class
                    e.oldTime.toString() == "Sun Aug 31 08:23:45 UTC 2014"
                    e.newTime.toString() == "Fri Sep 05 08:23:45 UTC 2014"
            }
    }

    void "Listeners can be unregistered from TimeKeeper"() {
        given: "A TimeKeeper with a registered listener"
            def listener = Mock(TimeChangedListener)
            TimeKeeper.addListener(listener)

        when: "A date is created from the TimeKeeper"
            def date = TimeKeeper.now()

        then: "It should be the date we set"
            date != currentDate
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when: "We unregister the listener"
            TimeKeeper.removeListener(listener)

        and: "We advance time"
            TimeKeeper.advanceDays(5)

        then: "The listener should not have received an event"
            0 * listener.timeChangedEventReceived(_)
    }

    void "TimeKeeper can have multiple listeners"() {
        given: "A TimeKeeper with two registered listeners"
            def listener1 = Mock(TimeChangedListener)
            def listener2 = Mock(TimeChangedListener)
            TimeKeeper.addListener(listener1)
            TimeKeeper.addListener(listener2)

        when: "A date is created from the TimeKeeper"
            def date = TimeKeeper.now()

        then: "It should be the date we set"
            date != currentDate
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when: "We advance time"
            TimeKeeper.advanceDays(5)

        then: "Both listeners should have received an event"
            1 * listener1.timeChangedEventReceived(_) >> {
                TimeChangedEvent e  ->
                    e.source == TimeKeeper.class
                    e.oldTime.toString() == "Sun Aug 31 08:23:45 UTC 2014"
                    e.newTime.toString() == "Fri Sep 05 08:23:45 UTC 2014"
            }
            1 * listener2.timeChangedEventReceived(_) >> {
                TimeChangedEvent e  ->
                    e.source == TimeKeeper.class
                    e.oldTime.toString() == "Sun Aug 31 08:23:45 UTC 2014"
                    e.newTime.toString() == "Fri Sep 05 08:23:45 UTC 2014"
            }
    }
}
