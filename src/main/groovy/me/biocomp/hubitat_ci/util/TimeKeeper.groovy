package me.biocomp.hubitat_ci.util

import groovy.time.*

import groovy.transform.Synchronized
import java.util.concurrent.CopyOnWriteArrayList

import groovy.transform.Synchronized
import java.util.concurrent.CopyOnWriteArrayList

/**
* The TimeKeeper is a class that allows us to control the current time in tests, without having to build anything special into the app script itself.
* When parsing an app/device script, we'll replace references to "new Date()" with "TimeKeeper.now()".
* As long as an app uses "new Date()" to get the current time, this class will control what time the app sees.
*/
class TimeKeeper {
    private static Date internalDate = new Date()

    private static CopyOnWriteArrayList<TimeChangedListener> timeChangedListeners = new CopyOnWriteArrayList<TimeChangedListener>()

    private static timekeeperLock = new Object()

    TimeKeeper() {
        throw new Exception("TimeKeeper is a static class and should not be instantiated.")
    }

    static Date now() {
        return internalDate
    }

    static void set(Date newDate) {
        internalDate = newDate
    }

    @Synchronized("timekeeperLock")
    static void advanceMillis(int millis) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, 0, 0, 0, millis))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    @Synchronized("timekeeperLock")
    static void advanceSeconds(int seconds) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, 0, 0, seconds, 0))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    @Synchronized("timekeeperLock")
    static void advanceMinutes(int minutes) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, 0, minutes, 0, 0))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    @Synchronized("timekeeperLock")
    static void advanceHours(int hours) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, hours, 0, 0, 0))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    @Synchronized("timekeeperLock")
    static void advanceDays(int days) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(days, 0, 0, 0, 0))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    @Synchronized("timekeeperLock")
    static void addListener(TimeChangedListener listener) {
        timeChangedListeners.add(listener)
    }

    @Synchronized("timekeeperLock")
    static void removeListener(TimeChangedListener listener) {
        timeChangedListeners.remove(listener)
    }

    @Synchronized("timekeeperLock")
    static void removeAllListeners() {
        timeChangedListeners.clear()
    }

    @Synchronized("timekeeperLock")
    static void fireTimeChangedEvent(Date oldTime, Date newTime) {
        TimeChangedEvent event = new TimeChangedEvent(TimeKeeper.class, oldTime, newTime)
        timeChangedListeners.each { listener ->
            listener.timeChangedEventReceived(event)
        }
    }
}

class TimeChangedEvent extends EventObject {
    Date oldTime
    Date newTime

    TimeChangedEvent(Object source, Date oldTime, Date newTime) {
        super(source)
        this.oldTime = oldTime
        this.newTime = newTime
    }
}

interface TimeChangedListener {
    void timeChangedEventReceived(TimeChangedEvent event)
}