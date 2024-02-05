package me.biocomp.hubitat_ci

import groovy.time.*

import me.biocomp.hubitat_ci.util.TimeKeeper

import spock.lang.Specification

class TimeKeeperTests extends Specification {
    def currentDate = new Date()

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))

        TimeKeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
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
}
