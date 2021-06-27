package hjava210520;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class MainTest {

    private ThirdMain sut = new ThirdMain();

    @Test
    public void testZero() throws IOException {
        var result = sut.getFirstMonthByYield(0);
        assertTrue(result.isPresent());
        assertEquals(Month.MAY, result.get().getMonth());
    }

    @Test
    public void test400() throws IOException {
        var result = sut.getFirstMonthByYield(400);
        assertTrue(result.isPresent());
        assertEquals(Month.NOV, result.get().getMonth());
        assertEquals(4.014755478320725, result.get().getValue(), 0.0000001);
    }

    @Test
    public void test1400() throws IOException {
        var result = sut.getFirstMonthByYield(1400);
        assertTrue(result.isEmpty());
    }
}
