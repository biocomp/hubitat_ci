package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.CommonApiImpl
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CommonImplTest extends Specification {
    class Impl implements CommonApiImpl {}
    def commonApi = new Impl()

    @Shared
    def myTimeZone = new GregorianCalendar().getTimeZone()

    @Shared
    Date currentDate = new Date()

    @Unroll
    def "Map parsing tests"(String input, Map expectedResult) {
        expect:
        commonApi.stringToMap(input) == expectedResult

        where:
        input         | expectedResult
        ""            | [:]
        "a:b"         | ["a":"b"]
        "a: b, c:  d" | [a:"b", c:"d"]
    }

    @Unroll
    def "TimeOfDayInBetween test with no timezone"(Date start, Date end, Date toCheck, boolean isBetween)
    {
        expect:
        commonApi.timeOfDayIsBetween(start, end, toCheck, new GregorianCalendar().timeZone) == isBetween

        where:
        start           | end             | toCheck     | isBetween
        currentDate - 1 | currentDate + 1 | currentDate | true
        currentDate + 1 | currentDate + 2 | currentDate | false
        currentDate - 2 | currentDate - 1 | currentDate | false
    }

    @Unroll
    def "timeToday properly appends time"(String timeString, TimeZone tz, int expectedHr, int expectedMin, int expectedSec)
    {
        expect:
        Date today = commonApi.timeToday(timeString, tz)
        today.hours == expectedHr
        today.minutes == expectedMin
        today.seconds == expectedSec

        today.year > 2017
        today.month > 0
        today.month <= 12
        today.day > 0
        today.day <= 31

        where:
        timeString             | tz              || expectedHr || expectedMin || expectedSec
        "00:00"                | myTimeZone      || 0          || 0           || 0
        "10:20"                | myTimeZone      || 10         || 20          || 0
        "2015-08-18T11:22:33Z" | myTimeZone      || 11         || 22          || 33
    }
}