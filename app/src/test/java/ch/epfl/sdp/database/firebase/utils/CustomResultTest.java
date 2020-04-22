package ch.epfl.sdp.database.firebase.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CustomResultTest {
    @Test
    public void CustomResultTest() {
        CustomResult<String> customResultTest = new CustomResult<>("", true, null);
        customResultTest.setResult("test");
        assertEquals("test", customResultTest.getResult());

        customResultTest.setSuccessful(false);
        assertEquals(false, customResultTest.isSuccessful());

        customResultTest.setException(null);
        assertNull(customResultTest.getException());
    }
}
