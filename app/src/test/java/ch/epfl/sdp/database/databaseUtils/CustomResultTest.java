package ch.epfl.sdp.database.databaseUtils;

import org.junit.Test;

import ch.epfl.sdp.utils.CustomResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CustomResultTest {
    @Test
    public void testGettersAndSettersRetrieveAndStore() {
        CustomResult<String> customResultTest = new CustomResult<>("", true, null);

        assertEquals("", customResultTest.getResult());
        assertTrue(customResultTest.isSuccessful());
        assertNull(customResultTest.getException());

        customResultTest.setResult("test");
        customResultTest.setSuccessful(false);
        Exception exception = new Exception();
        customResultTest.setException(exception);

        assertEquals("test", customResultTest.getResult());
        assertFalse(customResultTest.isSuccessful());
        assertTrue(customResultTest.getException() == exception);
    }
}
