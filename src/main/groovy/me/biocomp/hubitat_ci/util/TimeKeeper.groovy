package me.biocomp.hubitat_ci.util

import groovy.time.*

import groovy.transform.Synchronized
import java.util.concurrent.CopyOnWriteArrayList

/**
* The TimeKeeper is a class that allows us to control the current time in tests, without having to build anything special into the app script itself.
* When parsing an app/device script, we'll replace references to "new Date()" with "TimeKeeper.now()".
* As long as an app uses "new Date()" to get the current time, this class will control what time the app sees.
*/
class TimeKeeper {
    private static Date internalDate = new Date()

    private static timekeeperLock = new Object()

    TimeKeeper() {
        throw new Exception("TimeKeeper is a static class and should not be instantiated.")
    }

    @Synchronized("timekeeperLock")
    static Date now() {
        return internalDate
    }

    @Synchronized("timekeeperLock")
    static void set(Date newDate) {
        internalDate = newDate
    }

    @Synchronized("timekeeperLock")
    static void advanceMillis(int millis) {
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, 0, 0, 0, millis))
    }

    @Synchronized("timekeeperLock")
    static void advanceSeconds(int seconds) {
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, 0, 0, seconds, 0))
    }

    @Synchronized("timekeeperLock")
    static void advanceMinutes(int minutes) {
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, 0, minutes, 0, 0))
    }

    @Synchronized("timekeeperLock")
    static void advanceHours(int hours) {
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, hours, 0, 0, 0))
    }

    @Synchronized("timekeeperLock")
    static void advanceDays(int days) {
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(days, 0, 0, 0, 0))
    }

}