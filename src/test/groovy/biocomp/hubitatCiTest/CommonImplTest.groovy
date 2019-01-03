package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.emulation.CommonApiImpl
import spock.lang.Specification

class CommonImplTest extends Specification {
    class Impl implements CommonApiImpl {}
    def commonApi = new Impl()

    def "Map parsing tests"(String input, Map expectedResult) {
        expect:
        commonApi.stringToMap(input) == expectedResult

        where:
        input         | expectedResult
        ""            | [:]
        "a:b"         | ["a":"b"]
        "a: b, c:  d" | [a:"b", c:"d"]
    }
}