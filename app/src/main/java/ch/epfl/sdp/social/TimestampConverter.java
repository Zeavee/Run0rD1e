package ch.epfl.sdp.social;

import androidx.room.TypeConverter;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimestampConverter {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}