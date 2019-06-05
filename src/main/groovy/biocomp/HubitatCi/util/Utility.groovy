package biocomp.hubitatCi.util

import com.sun.istack.internal.NotNull
import groovy.transform.TypeChecked

import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder


final class Utility
{
    static Map stringToMap(String map) {
        def result = [:]
        map.split(',').each {
            def keyValue = it.split(':')
            if (keyValue.size() == 2) {
                result[keyValue[0].trim()] = keyValue[1].trim()
            }
        }

        return result
    }

    

    /**
     * Return today's date object for given time.
     * @param timeString - Either an ISO-8601 date string as returned from time input preferences, or a simple time string in "hh:mm" format (“21:34”).
     * @param timeZone - current time zone. Please use it.
     * @note most likely some date calculations are incorrect in some cases, but this is meant mostly for testing.
     */
    static Date timeToday(String timeString, TimeZone timeZone = null) {
        assert timeZone != null
        def time = Utility.parseTimeString(timeString)
        def dateTime = ZonedDateTime.now(timeZone.toZoneId())
        def justDate = ZonedDateTime.of(dateTime.year, dateTime.monthValue, dateTime.dayOfMonth, 0, 0, 0, 0,
                timeZone.toZoneId())
        def updatedDate = justDate
                .plusHours(time.hours)
                .plusMinutes(time.minutes)
                .plusSeconds(time.seconds);

        return new Date(updatedDate.year, updatedDate.monthValue, updatedDate.dayOfMonth, updatedDate.hour,
                updatedDate.minute, updatedDate.second);
    }

    static boolean timeOfDayIsBetween(Date start, Date stop, Date value, TimeZone timeZone = null) {
        assert timeZone != null
        return value.after(start) && value.before(stop);
    }

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

