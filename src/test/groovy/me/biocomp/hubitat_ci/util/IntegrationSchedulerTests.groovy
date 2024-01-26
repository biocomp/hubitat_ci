package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.util.IntegrationScheduler
import me.biocomp.hubitat_ci.util.TimeKeeper

 //import groovy.time.*
// import org.quartz.CronExpression

import spock.lang.Specification

interface TestListener {
    void handler(Map data)
}


class IntegrationSchedulerTests extends Specification {

    def scheduler = new IntegrationScheduler()
    def listener = Mock(TestListener)

    String ISO_8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone('UTC'))
    }

    // def "test2"() {
    //     given:
    //     def cronExpression = "0 15 10 * * ?"    // Fire at 10:15am every day

    //     expect:
    //         Date now = new Date()
    //         use (groovy.time.TimeCategory) {
    //             now = new Date() - 24.hours
    //         }
    //         def exp = new CronExpression(cronExpression)
    //         def nextFireAt = exp.getNextValidTimeAfter(now)
    //         nextFireAt == null
    // }

    def "Can schedule with cron expression and method name"() {
        given:
            def cronExpression = "0 15 10 * * ?"    // Fire at 10:15am every day

        when:
            scheduler.schedule(cronExpression, "handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == cronExpression
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
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == cronExpression
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
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == cronExpression1
            scheduler._scheduleRequests[0].handlerMethod == "handler1"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[1].cronExpressionOrIsoDate == cronExpression2
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
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == cronExpression2
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
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == "0 * * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery5Minutes generates a valid cron expression"() {
        when:
            scheduler.runEvery5Minutes("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == "0 */5 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery10Minutes generates a valid cron expression"() {
        when:
            scheduler.runEvery10Minutes("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == "0 */10 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery15Minutes generates a valid cron expression"() {
        when:
            scheduler.runEvery15Minutes("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == "0 */15 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery30Minutes generates a valid cron expression"() {
        when:
            scheduler.runEvery30Minutes("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == "0 */30 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery1Hour generates a valid cron expression"() {
        when:
            scheduler.runEvery1Hour("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == "0 0 * * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "runEvery3Hours generates a valid cron expression"() {
        when:
            scheduler.runEvery3Hours("handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == "0 0 */3 * * ?"
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
    }

    def "schedule with a Date generates a request with the ISO date"() {
        when:
            def date = new Date()
            scheduler.schedule(date, "handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == date.format(ISO_8601_FORMAT)
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[0].deleteAfterSingleRun == false
    }

    def "runOnce with a Date generates a request with the ISO date"() {
        when:
            def date = new Date()
            scheduler.runOnce(date, "handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == date.format(ISO_8601_FORMAT)
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[0].deleteAfterSingleRun == true
    }

    def "runIn generates a request with an ISO date that is the amount of seconds in the future"() {
        when:
            def seconds = 60
            def now = new Date()
            def expectedDate = new Date(now.getTime() + (seconds * 1000))
            scheduler.runIn(seconds, "handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == expectedDate.format(ISO_8601_FORMAT)
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[0].deleteAfterSingleRun == true
    }

    def "runInMillis generates a request with an ISO date that is the amount of millis in the future"() {
        when:
            def millis = 50
            def now = new Date()
            def expectedDate = new Date(now.getTime() + millis)
            scheduler.runInMillis(millis, "handler")

        then:
            scheduler._scheduleRequests.size() == 1
            scheduler._scheduleRequests[0].cronExpressionOrIsoDate == expectedDate.format(ISO_8601_FORMAT)
            scheduler._scheduleRequests[0].handlerMethod == "handler"
            scheduler._scheduleRequests[0].options == null
            scheduler._scheduleRequests[0].deleteAfterSingleRun == true
    }

    def "runIn also interoperates with the TimeKeeper class"() {
        given:
            def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            timekeeper.install()
            def tkScheduler = new IntegrationScheduler(timekeeper)

        when:
            def seconds = 60
            def now = timekeeper.now()
            def expectedDate = new Date(now.getTime() + (seconds * 1000))
            tkScheduler.runIn(seconds, "handler")

        then:
            tkScheduler._scheduleRequests.size() == 1
            tkScheduler._scheduleRequests[0].cronExpressionOrIsoDate == expectedDate.format(ISO_8601_FORMAT)
            tkScheduler._scheduleRequests[0].handlerMethod == "handler"
            tkScheduler._scheduleRequests[0].options == null
            tkScheduler._scheduleRequests[0].deleteAfterSingleRun == true

        cleanup:
            timekeeper.uninstall()
    }

    def "runInMillis also interoperates with the TimeKeeper class"() {
        given:
            def timekeeper = new TimeKeeper(Date.parse("yyyy-MM-dd hh:mm:ss", "2014-08-31 8:23:45"))
            timekeeper.install()
            def tkScheduler = new IntegrationScheduler(timekeeper)

        when:
            def millis = 50
            def now = timekeeper.now()
            def expectedDate = new Date(now.getTime() + millis)
            tkScheduler.runInMillis(millis, "handler")

        then:
            tkScheduler._scheduleRequests.size() == 1
            tkScheduler._scheduleRequests[0].cronExpressionOrIsoDate == expectedDate.format(ISO_8601_FORMAT)
            tkScheduler._scheduleRequests[0].handlerMethod == "handler"
            tkScheduler._scheduleRequests[0].options == null
            tkScheduler._scheduleRequests[0].deleteAfterSingleRun == true

        cleanup:
            timekeeper.uninstall()
    }
}