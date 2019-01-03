package biocomp.hubitatCiTest.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

final class Utility
{
    private static final DateTimeFormatter DATE_TIME_OFFSET_FORMATTER =
            new DateTimeFormatterBuilder().parseCaseInsensitive().append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .optionalStart().appendZoneOrOffsetId().optionalEnd()
                    .optionalStart().appendOffset("+HH:MM", "+00:00").optionalEnd()
                    .optionalStart().appendOffset("+HHMM", "+0000").optionalEnd()
                    .optionalStart().appendOffset("+HH", "+00").optionalEnd()
                    .toFormatter();

    /**
     * Parse time string to hour, minute and second (keys in a map)
     * @param string - of "10:23" format or ISO-8601 (but only time portion is used)
     * @note most likely some parsing is incorrect, but this is meant mostly for testing.
     */
    static Map<String, Integer> parseTimeString(String string)
    {
        def asHourAndMinute = string.split(":");
        if (asHourAndMinute.size() == 2)
        {
            try
            {
                return [hours: Integer.parseInt(asHourAndMinute[0]), minutes: Integer.parseInt(asHourAndMinute[1]), seconds: 0]
            }
            catch (NumberFormatException)
            {
                // It's fine, format is probably different
            }
        }

        def parsedIsoDate = java.time.ZonedDateTime.parse(string, DATE_TIME_OFFSET_FORMATTER)
        return [hours: parsedIsoDate.hour, minutes: parsedIsoDate.minute, seconds: parsedIsoDate.second]
    }
}

