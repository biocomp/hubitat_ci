package me.biocomp.hubitat_ci.util

import groovy.time.*

import groovy.transform.Synchronized
import java.util.concurrent.CopyOnWriteArrayList

/**
* TimeKeeperDate is just a Date that we can override the constructor for, without impacting
* the base Date constructor.
*/
class TimeKeeperDate extends Date {
}

/**
* The TimeKeeper is a class that intercepts and overrides the TimeKeeperDate constructor.
* This allows us to control the current time in tests, without having to build anything special into the app script itself.
* When parsing an app/device script, we'll replace references to "new Date()" with "new TimeKeeperDate()".
* As long as an app uses "new Date()" to get the current time, this class will control what time the app sees.
* Note that you must call install() before the app script is tested, and uninstall() after.
*/
class TimeKeeper {
    private Date internalDate = null

    private final CopyOnWriteArrayList<TimeChangedListener> timeChangedListeners

    private final timekeeperLock = new Object()

    TimeKeeper() {
        internalDate = new Date()
        timeChangedListeners = new CopyOnWriteArrayList<TimeChangedListener>()
    }

    TimeKeeper(Date startingDate) {
        internalDate = startingDate
        timeChangedListeners = new CopyOnWriteArrayList<TimeChangedListener>()
    }

    /**
    * Install the override of the default Date constructor
    */
    def install() {
        TimeKeeperDate.metaClass.constructor = { -> return this.now() }
    }

    /**
    * Uninstall the override of the default Date constructor, so that "new Date()"
    * will return the current time again.
    */
    def uninstall() {
        TimeKeeperDate.metaClass = null
    }

    def now() {
        return internalDate
    }

    def set(Date newDate) {
        internalDate = newDate
    }

    def advanceMillis(int millis) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, 0, 0, 0, millis))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    def advanceSeconds(int seconds) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, 0, 0, seconds, 0))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    def advanceMinutes(int minutes) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, 0, minutes, 0, 0))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    def advanceHours(int hours) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(0, hours, 0, 0, 0))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    def advanceDays(int days) {
        def oldDate = internalDate
        internalDate = groovy.time.TimeCategory.plus(internalDate, new groovy.time.TimeDuration(days, 0, 0, 0, 0))
        fireTimeChangedEvent(oldDate, internalDate)
    }

    @Synchronized("timekeeperLock")
    def addListener(TimeChangedListener listener) {
        timeChangedListeners.add(listener)
    }

    @Synchronized("timekeeperLock")
    def removeListener(TimeChangedListener listener) {
        timeChangedListeners.remove(listener)
    }

    @Synchronized("timekeeperLock")
    def fireTimeChangedEvent(Date oldTime, Date newTime) {
        TimeChangedEvent event = new TimeChangedEvent(this, oldTime, newTime)
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