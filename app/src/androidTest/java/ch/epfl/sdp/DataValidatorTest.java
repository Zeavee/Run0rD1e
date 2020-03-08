package ch.epfl.sdp;

import org.junit.Assert;
import org.junit.Test;

public class DataValidatorTest {

    @Test
    public void isEmailValid_CorrectEmail_ReturnsTrue() {
        Assert.assertTrue(DataValidator.isEmailValid("shinvara@gmail.com"));
    }

    @Test
    public void isEmailValid_EmailHasNoAtSign_ReturnsFalse() {
        Assert.assertFalse(DataValidator.isEmailValid("shinvara-gmail.com"));
    }
}
