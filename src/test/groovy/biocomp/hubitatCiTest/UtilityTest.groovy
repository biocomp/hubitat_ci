package biocomp.hubitatCiTest

import biocomp.hubitatCiTest.util.Utility
import spock.lang.Specification
import spock.lang.Unroll

class UtilityTest extends Specification {
    @Unroll
    def "Parsing time strings successfully"(String input, int hours, int minutes, int seconds) {
        expect:
        Utility.parseTimeString(input) == [hours: hours, minutes: minutes, seconds: seconds]

        where:
        input                    || hours | minutes | seconds
        "10:34"                  || 10    | 34      | 0
        "00:00"                  || 0     | 0       | 0
        "19:00"                  || 19    | 0       | 0
        "2015-08-18T00:00+01"    || 0     | 00      | 0 // Not using zone offset.
        "2015-08-18T10:20+01"    || 10    | 20      | 0 // Not using zone offset.
        "2015-08-18T19:23Z"      || 19    | 23      | 0 // Not using zone offset.
        "2015-08-18T11:22+01:02" || 11    | 22      | 0 // Not using zone offset.
        "2015-08-18T11:22:33Z"   || 11    | 22      | 33
    }
}
