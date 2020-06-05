package ch.epfl.sdp.SocialUnitTests;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import ch.epfl.sdp.social.socialDatabase.TimestampConverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TimestampConvertTest {

    @Test
    public void timestampReturnsNullOnArgumentNull() {

        assertNull(TimestampConverter.fromTimestamp(null));
        assertNull(TimestampConverter.dateToTimestamp(null));
    }

    @Test
    public void timestampConvertsNonNullCorrectly() {

        assertEquals(new Long(0), TimestampConverter.dateToTimestamp(new Date(0)));
        assertEquals(0, TimestampConverter.fromTimestamp((long) 0).getTime());
    }

    @Test
    public void timestampHasCorrectTimeFormat() {

        assertEquals("yyyy-MM-dd HH:mm:ss", ((SimpleDateFormat) TimestampConverter.df).toPattern());
    }

}
