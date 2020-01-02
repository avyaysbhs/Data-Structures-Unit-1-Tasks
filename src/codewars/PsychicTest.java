package codewars;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runners.JUnit4;


public class PsychicTest {
    @Test
    public void testRandom() {
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
    }
}