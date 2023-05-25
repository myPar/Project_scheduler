package com.scheduler.project.tools;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeConverter {
    public static final String timeFormat = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);

    public static Long getTimeLongValue(String time_string) throws DateTimeParseException {
        if (time_string == null) {
            return null;
        }
        LocalDateTime time = LocalDateTime.parse(time_string, formatter);
        ZonedDateTime zdt = ZonedDateTime.of(time, ZoneId.systemDefault());

        return zdt.toInstant().toEpochMilli();
    }

    public static String getTimeStringValue(Long timeValue) throws DateTimeException {
        if (timeValue == null) {
            return null;
        }
        Instant timeInstant = Instant.ofEpochMilli(timeValue);
        LocalDateTime dateTimeInstant = LocalDateTime.ofInstant(timeInstant, ZoneId.systemDefault());

        return dateTimeInstant.format(formatter);
    }
}
