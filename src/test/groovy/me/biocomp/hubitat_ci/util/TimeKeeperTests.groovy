package me.biocomp.hubitat_ci

import groovy.time.*

import me.biocomp.hubitat_ci.util.TimeKeeper
import me.biocomp.hubitat_ci.util.TimeKeeperDate

import spock.lang.Specification

class TimeKeeperTests extends Specification {
    def currentDate = new Date()

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
    }

    void "Can use the TimeKeeper class to override TimeKeeperDate constructor"() {
        given:
            def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            timekeeper.install()

        when:
            Date date = new TimeKeeperDate()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        cleanup:
            timekeeper.uninstall()
    }

    void "TimeKeeper installation doesn't affect creation of Date instances"() {
        given:
            def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            timekeeper.install()

        when:
            def date = new Date()

        then:
            date.toString() == currentDate.toString()

        cleanup:
            timekeeper.uninstall()
    }

    void "If TimeKeeper is not installed, then TimeKeeperDate is just a Date"() {
        given:
            def date = new Date()

        when:
            def date2 = new TimeKeeperDate()

        then:
            date.toString() == date2.toString()
    }

    void "By default, TimeKeeper will start at current time"() {
        given:
            def timekeeper = new TimeKeeper()
            timekeeper.install()

        when:
            def date = new TimeKeeperDate()

        then:
            date.toString() == currentDate.toString()

        cleanup:
            timekeeper.uninstall()
    }

    void "Can set the TimeKeeper after it's already constructed"() {
        given:
            def timekeeper = new TimeKeeper()
            timekeeper.install()

        when:
            def date = new TimeKeeperDate()

        then:
            date.toString() == currentDate.toString()

        when:
            timekeeper.set(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            def date2 = new TimeKeeperDate()

        then:
            date2.toString() != currentDate.toString()
            date2.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        cleanup:
            timekeeper.uninstall()
    }

    void "Can advance the minutes of the TimeKeeper"() {
        given:
            def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            timekeeper.install()

        when:
            def date = new TimeKeeperDate()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when:
            timekeeper.advanceMinutes(5)
            def date2 = new TimeKeeperDate()

        then:
            date2.toString() != currentDate.toString()
            date2.toString() == "Sun Aug 31 08:28:45 UTC 2014"

        cleanup:
            timekeeper.uninstall()
    }

    void "Can advance the seconds of the TimeKeeper"() {
        given:
            def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            timekeeper.install()

        when:
            def date = new TimeKeeperDate()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when:
            timekeeper.advanceSeconds(5)
            def date2 = new TimeKeeperDate()

        then:
            date2.toString() != currentDate.toString()
            date2.toString() == "Sun Aug 31 08:23:50 UTC 2014"

        cleanup:
            timekeeper.uninstall()
    }

    void "Can advance the hours of the TimeKeeper"() {
        given:
            def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            timekeeper.install()

        when:
            def date = new TimeKeeperDate()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when:
            timekeeper.advanceHours(5)
            def date2 = new TimeKeeperDate()

        then:
            date2.toString() != currentDate.toString()
            date2.toString() == "Sun Aug 31 13:23:45 UTC 2014"

        cleanup:
            timekeeper.uninstall()
    }

    void "Can advance the days of the TimeKeeper"() {
        given:
            def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            timekeeper.install()

        when:
            def date = new TimeKeeperDate()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when:
            timekeeper.advanceDays(5)
            def date2 = new TimeKeeperDate()

        then:
            date2.toString() != currentDate.toString()
            date2.toString() == "Fri Sep 05 08:23:45 UTC 2014"

        cleanup:
            timekeeper.uninstall()
    }

    void "Uninstalling the TimeKeeper makes the TimeKeeperDate class return current date/time again"() {
        given:
            def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            timekeeper.install()

        when:
            def date = new TimeKeeperDate()

        then:
            date.toString() != currentDate.toString()
            date.toString() == "Sun Aug 31 08:23:45 UTC 2014"

        when:
            timekeeper.uninstall()
            def date2 = new TimeKeeperDate()

        then:
            date2.toString() == currentDate.toString()
    }
}