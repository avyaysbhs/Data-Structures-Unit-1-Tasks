package codewars;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

public class RobotTest {
    private Robot vicky;

    @Before
    public void setUp() {
        vicky = new Robot();
    }

    @After
    public void tearDown() {
        vicky = null;
    }

    @Test
    public void simpleTest() {
        assertEquals("Thank you for teaching me hello", vicky.learnWord("hello"));
        assertEquals("Thank you for teaching me world", vicky.learnWord("world"));
        assertEquals("Thank you for teaching me goodbye", vicky.learnWord("goodbye"));
        assertEquals("I already know the word world", vicky.learnWord("world"));
        assertEquals("I already know the word World", vicky.learnWord("World"));
        assertEquals("I do not understand the input", vicky.learnWord("W@orld"));
    }
}