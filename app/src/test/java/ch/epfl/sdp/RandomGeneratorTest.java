package ch.epfl.sdp;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomGeneratorTest {
    private static RandomGenerator randGen = new RandomGenerator();

    @Test
    public void randomString_test(){
        for(int i = 0; i < 10; ++i){
            assertEquals(i, randGen.randomString(i).length());
        }
    }

    @Test
    public void randomValidString_test(){
        for(int i = 0; i < 10; ++i){
            assertEquals(i, randGen.randomValidString(i).length());
        }
    }

    @Test
    public void randomEmail_test(){
        assertNotNull(randGen.randomEmail());
    }
}
