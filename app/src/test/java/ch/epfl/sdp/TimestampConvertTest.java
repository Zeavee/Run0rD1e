package ch.epfl.sdp;

import org.junit.Test;

import java.util.Date;

import ch.epfl.sdp.social.TimestampConverter;

import static org.junit.Assert.assertEquals;

public class TimestampConvertTest {


    @Test
    public void testFromTimestamp()
    {
        assertEquals((long)0, TimestampConverter.fromTimestamp((long) 0).getTime());
        assertEquals(null, TimestampConverter.fromTimestamp(null));
    }

    @Test
    public void testFromDate()
    {
        assertEquals(new Long(0), TimestampConverter.dateToTimestamp(new Date(0)));
        assertEquals(null, TimestampConverter.dateToTimestamp(null));
    }




}
