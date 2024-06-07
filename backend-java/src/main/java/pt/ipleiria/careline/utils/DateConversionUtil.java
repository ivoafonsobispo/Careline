package pt.ipleiria.careline.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateConversionUtil {
    public Instant convertStringToStartOfDayInstant(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        return startOfDay.toInstant(ZoneOffset.UTC);
    }

    public Instant convertStringToEndOfDayInstant(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        return endOfDay.toInstant(ZoneOffset.UTC);
    }
}
