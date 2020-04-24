package ch.epfl.sdp.SocialUnitTests;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch.epfl.sdp.social.socialDatabase.TimestampConverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimestampConvertTest {

    @Test
    public void timestampReturnsNullOnArgumentNull() {

        assertEquals(null, TimestampConverter.fromTimestamp(null));
        assertEquals(null, TimestampConverter.dateToTimestamp(null));
    }

    @Test
    public void timestampConvertsNonNullCorrectly() {

        assertEquals(new Long(0), TimestampConverter.dateToTimestamp(new Date(0)));
        assertEquals(0, TimestampConverter.fromTimestamp((long) 0).getTime());
    }

    @Test
    public void timestampHasCorrectTimeFormat() {

        assertTrue(((SimpleDateFormat)TimestampConverter.df).toPattern().equals("yyyy-MM-dd HH:mm:ss"));
    }

}
