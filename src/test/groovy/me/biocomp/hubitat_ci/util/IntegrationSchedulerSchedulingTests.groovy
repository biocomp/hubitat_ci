package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.util.IntegrationScheduler
import me.biocomp.hubitat_ci.util.TimeKeeper

import spock.lang.Specification

class IntegrationSchedulerSchedulingTests extends Specification {
    def scheduler = new IntegrationScheduler()

    String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
    }

    def "Can schedule with cron expression and method name"() {
        given:
            def cronExpression = "0 15 10 * * ?"    // Fire at 10:15am every day

        when:
            scheduler.schedule(cronExpression, "handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == cronExpression
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "Can unschedule by method name"() {
        given:
            def cronExpression = "0 15 10 * * ?"
            scheduler.schedule(cronExpression, "handler")

        when:
            scheduler.unschedule("handler")

        then:
            scheduler._scheduleRequests.size() == 0
    }

    def "Can schedule by cronExpression and method name and options"() {
        given:
            def cronExpression = "0 15 10 * * ?"
            def options = [data: [a: 1, b: 2]]

        when:
            scheduler.schedule(cronExpression, "handler", options)

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == cronExpression
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == options
    }

    def "Can schedule multiple by different cron expressions and method names"() {
        given:
            def cronExpression1 = "0 15 10 * * ?"    // Fire at 10:15am every day
            def cronExpression2 = "0 30 10 * * ?"    // Fire at 10:30am every day

        when:
            scheduler.schedule(cronExpression1, "handler1")
            scheduler.schedule(cronExpression2, "handler2")

        then:
            scheduler._scheduleRequests.size() == 2
            scheduler._scheduleRequests[0].cronExpression == cronExpression1
            scheduler._scheduleRequests[0].handlerMethod == "handler1"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[1].cronExpression == cronExpression2
            scheduler._scheduleRequests[1].handlerMethod == "handler2"
            scheduler._scheduleRequests[1].options == null
    }

    def "Can schedule multiple and unschedule a single by method name"() {
        given:
            def cronExpression1 = "0 15 10 * * ?"    // Fire at 10:15am every day
            def cronExpression2 = "0 30 10 * * ?"    // Fire at 10:30am every day
            scheduler.schedule(cronExpression1, "handler1")
            scheduler.schedule(cronExpression2, "handler2")

        when:
            scheduler.unschedule("handler1")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == cronExpression2
            scheduler._scheduleRequests[0].handlerMethod == "handler2"
            scheduler._scheduleRequests[0].options == null
    }

    def "Can unschedule all"() {
        given:
            def cronExpression1 = "0 15 10 * * ?"    // Fire at 10:15am every day
            def cronExpression2 = "0 30 10 * * ?"    // Fire at 10:30am every day
            scheduler.schedule(cronExpression1, "handler1")
            scheduler.schedule(cronExpression2, "handler2")

        when:
            scheduler.unschedule()

        then:
            scheduler._scheduleRequests.size() == 0
    }

    def "runEvery1Minute generates a valid cron expression"() {
        when:
            scheduler.runEvery1Minute("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == "0 * * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery5Minutes generates a valid cron expression"() {
        when:
            scheduler.runEvery5Minutes("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == "0 */5 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery10Minutes generates a valid cron expression"() {
        when:
            scheduler.runEvery10Minutes("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == "0 */10 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery15Minutes generates a valid cron expression"() {
        when:
            scheduler.runEvery15Minutes("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == "0 */15 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery30Minutes generates a valid cron expression"() {
        when:
            scheduler.runEvery30Minutes("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == "0 */30 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery1Hour generates a valid cron expression"() {
        when:
            scheduler.runEvery1Hour("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == "0 0 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery3Hours generates a valid cron expression"() {
        when:
            scheduler.runEvery3Hours("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == "0 0 */3 * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "schedule with a Date generates a request with a CRON expression"() {
        when:
            def date1 = Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 1:23:46")
            scheduler.schedule(date1, "handler")

            def date2 = Date.parse("yyyy-MM-dd hh:mm:ss", "2014-09-1 14:45:46")
            scheduler.schedule(date2, "handler")

        then:
            scheduler._scheduleRequests.size() == 2

            scheduler._scheduleRequests[0].cronExpression == "0 23 1 * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[0].deleteAfterSingleRun == false

            scheduler._scheduleRequests[1].cronExpression == "0 45 14 * * ?"
            scheduler._scheduleRequests[1].handlerMethod == "handler"
            scheduler._scheduleRequests[1].options == null
            scheduler._scheduleRequests[1].deleteAfterSingleRun == false
    }

    def "runOnce with a Date generates a request with a nextFireAt, but no cron expression"() {
        when:
            def date1 = Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 1:23:46")
            scheduler.runOnce(date1, "handler")

            def date2 = Date.parse("yyyy-MM-dd hh:mm:ss", "2014-09-1 14:45:46")
            scheduler.runOnce(date2, "handler")

        then:
            scheduler._scheduleRequests.size() == 2

            scheduler._scheduleRequests[0].cronExpression == null
            scheduler._scheduleRequests[0].nextFireAt == date1
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[0].deleteAfterSingleRun == true

            scheduler._scheduleRequests[1].cronExpression == null
            scheduler._scheduleRequests[1].nextFireAt == date2
            scheduler._scheduleRequests[1].handlerMethod == "handler"
            scheduler._scheduleRequests[1].options == null
            scheduler._scheduleRequests[1].deleteAfterSingleRun == true
    }

    def "runIn generates a request with a nextFireAt that is the amount of seconds in the future"() {
        when:
            def seconds = 60
            scheduler = new IntegrationScheduler()    // Pass in a timekeeper to ensure millisecond precision in the comparison, because runIn generates a date internally.
            def now = TimeKeeper.now()
            def expectedDate = new Date(now.getTime() + (seconds * 1000))
            scheduler.runIn(seconds, "handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == null
            scheduler._scheduleRequests[0].nextFireAt.getTime() == expectedDate.getTime()
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[0].deleteAfterSingleRun == true
    }

    def "runInMillis generates a request with a nextFireAt"() {
        when:
            def millis = 50
            scheduler = new IntegrationScheduler()    // Pass in a timekeeper to ensure millisecond precision in the comparison, because runIn generates a date internally.
            def now = TimeKeeper.now()
            def expectedDate = new Date(now.getTime() + millis)
            scheduler.runInMillis(millis, "handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpression == null
            scheduler._scheduleRequests[0].nextFireAt.getTime() == expectedDate.getTime()
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[0].deleteAfterSingleRun == true
    }
}