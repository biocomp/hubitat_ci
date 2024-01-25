package me.biocomp.hubitat_ci

import me.biocomp.hubitat_ci.util.IntegrationScheduler

// import groovy.time.*
// import org.quartz.CronExpression

import spock.lang.Specification

interface TestListener {
    void handler(Map data)
}


class IntegrationSchedulerTests extends Specification {

    def scheduler = new IntegrationScheduler()
    def listener = Mock(TestListener)

    def setup() {
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
}