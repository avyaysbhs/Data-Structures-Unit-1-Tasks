package codewars;

import org.junit.Test;
import static org.junit.Assert.*;

public class DoubleLinearTest {
    @Test
    public void test() {
        System.out.println("Fixed Tests dblLinear");
        assertEquals(DoubleLinear.dblLinear(10), 22);
        assertEquals(DoubleLinear.dblLinear(20), 57);
        assertEquals(DoubleLinear.dblLinear(30), 91);
        assertEquals(DoubleLinear.dblLinear(50), 175);
    }
}
