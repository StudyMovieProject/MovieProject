package project.movie.common.util.date;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class DateFormatUtil {
    public static Date convertToLocalDateToDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    public static Time convertToLocalTimeToTime(LocalTime localDate) {
        return java.sql.Time.valueOf(localDate);
    }
}
