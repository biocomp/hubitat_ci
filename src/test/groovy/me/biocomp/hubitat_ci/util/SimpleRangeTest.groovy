package me.biocomp.hubitat_ci


import biocomp.hubitatCi.util.SimpleRange
import groovy.transform.TypeChecked
import spock.lang.Specification
import spock.lang.Unroll

class SimpleRangeTest extends
        Specification
{
    @Unroll
    def "Simple range parses expected format and validates values"(String format, def value, boolean isWithin) {
        expect:
            SimpleRange.parse(format).isValueWithin(value) == isWithin

        where:
            format       | value          | isWithin
            "0..1"       | 1              | true
            "0..1"       | 0              | true
            "0..1"       | -1             | false
            "0..1"       | 2              | false
            "*..1"       | -100           | true
            "*..1"       | 100            | false
            "*..1"       | 1              | true
            "*..1"       | 0              | true
            "*..*"       | -100000.132434 | true
            "*..*"       | 100000.132434  | true
            "42.2..42.3" | 42.2           | true
            "42.2..42.3" | 42.3           | true
            "42.2..42.3" | 42.25          | true
            "42.2..42.3" | 42.35          | false
            "42.2..42.3" | 42.15          | false
            "42.2..*"    | 99             | true
            "42.2..*"    | 99.99          | true
            "42.2..*"    | 42.2           | true
            "42.2..*"    | 42.1           | false
    }

    @Unroll
    def "Invalid range format produces null range while parsing"(String badFormat) {
        when:
            SimpleRange.parse(badFormat)

        then:
            IllegalArgumentException e = thrown()

        where:
            badFormat << [null,
                          '',
                          'a',
                          '0',
                          '0000',
                          '0.0',
                          '0..-',
                          '+..-',
                          'a..b',
                          'a..234',
                          '234..b',
                          '1..2..3',
                          '1..2..3..4',
                          '..4',
                          '1..']
    }
}