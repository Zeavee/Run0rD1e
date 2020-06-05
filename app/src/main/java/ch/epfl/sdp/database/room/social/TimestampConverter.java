package ch.epfl.sdp.database.room.social;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class is used to convert Timestamps to date or long for the Message records to be store-able in the database
 */
public class TimestampConverter {

    /**
     * Format of the date (or timestamp) as returned from Firebase
     */
    public final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    /**
     * Gets a date object from milliseconds
     *
     * @param value the milliseconds since January 1 1970
     * @return the date given the milliseconds since  January 1 1970
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Gets milliseconds from date object
     *
     * @param date the date formatted according to df
     * @return the number of milliseconds from January 1 1970 to the given date specified by "Date"
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}