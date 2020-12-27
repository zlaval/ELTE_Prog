package preexam.delivery.test;

import org.junit.Test;
import preexam.delivery.DeliveryService;
import preexam.delivery.ProductState;
import preexam.delivery.RangedDelivery;
import preexam.delivery.exceptions.WrongZipCodeException;

import java.net.URL;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeliveryTest {


    @Test
    public void emptyDS() {
        var empty = new DeliveryService("d1", 123);
        assertEquals("d1", empty.getName());
        assertEquals(123, empty.getZipCode());
        assertTrue(empty.getServedZipCodes().isEmpty());
    }


    @Test
    public void simpleDS() {
        var testList = Arrays.asList(1, 2, 3, 4);
        var full = new DeliveryService("d1", 123, 1, 2, 3, 4);
        assertEquals("d1", full.getName());
        assertEquals(123, full.getZipCode());
        assertEquals(testList, full.getServedZipCodes());
    }

    @Test(expected = WrongZipCodeException.class)
    public void nonServerOrder() {
        var full = new DeliveryService("d1", 123, 1, 2, 3, 4);
        full.prepareOrder(8, "alma");
    }

    @Test
    public void emptyOwnZip1Product() {
        var empty = new DeliveryService("d1", 123);
        empty.prepareOrder(123, "alma");
        var result = empty.makeDeliveries();
        assertTrue(result.containsKey(123));
        var orders = result.get(123);
        assertTrue(orders.containsKey("alma"));
        assertEquals(1, (int) orders.get("alma"));
    }

    @Test
    public void emptyOwnZip2Products() {
        var empty = new DeliveryService("d1", 123);
        empty.prepareOrder(123, "alma", "körte");
        var result = empty.makeDeliveries();
        assertTrue(result.containsKey(123));
        var orders = result.get(123);
        assertTrue(orders.containsKey("alma"));
        assertEquals(1, (int) orders.get("alma"));
        assertTrue(orders.containsKey("körte"));
        assertEquals(1, (int) orders.get("körte"));
    }

    @Test
    public void emptyOwnZipRepeatedProducts() {
        var empty = new DeliveryService("d1", 123);
        empty.prepareOrder(123, "alma", "körte", "alma");
        var result = empty.makeDeliveries();
        assertTrue(result.containsKey(123));
        var orders = result.get(123);
        assertTrue(orders.containsKey("alma"));
        assertEquals(2, (int) orders.get("alma"));
        assertTrue(orders.containsKey("körte"));
        assertEquals(1, (int) orders.get("körte"));
    }

    @Test
    public void noProductOrder() {
        var empty = new DeliveryService("d1", 123);
        empty.prepareOrder(123);
        var result = empty.makeDeliveries();
        assertTrue(result.isEmpty());
    }

    @Test
    public void cancelledOrder() {
        var empty = new DeliveryService("d1", 123);
        var orderId = empty.prepareOrder(123, "alma");
        empty.cancelOrder(orderId);
        var result = empty.makeDeliveries();
        assertTrue(result.isEmpty());
    }

    @Test
    public void cancelledOrder2outOf3() {
        var empty = new DeliveryService("d1", 123);
        empty.prepareOrder(123, "alma");
        var orderId = empty.prepareOrder(123, "körte");
        empty.prepareOrder(123, "banán");
        empty.cancelOrder(orderId);
        var result = empty.makeDeliveries();

        var orders = result.get(123);
        assertTrue(orders.containsKey("alma"));
        assertEquals(1, (int) orders.get("alma"));
        assertTrue(orders.containsKey("banán"));
        assertEquals(1, (int) orders.get("banán"));
        assertFalse(orders.containsKey("körte"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cancelledWrongOrder() {
        var empty = new DeliveryService("d1", 123);
        empty.prepareOrder(123, "alma");
        empty.cancelOrder(777);
    }

    @Test
    public void afterOrder() {
        var empty = new DeliveryService("d1", 123);
        empty.prepareOrder(123, "banán");
        empty.makeDeliveries();
        var result = empty.makeDeliveries();
        assertTrue(result.isEmpty());
    }

    @Test
    public void order2() {
        var full = new DeliveryService("d1", 123, 1, 2, 3, 4);
        full.prepareOrder(123, "banán");
        full.prepareOrder(1, "alma");
        var result = full.makeDeliveries();
        assertEquals(2, result.size());
        assertTrue(result.containsKey(123));
        assertTrue(result.containsKey(1));
    }

    @Test
    public void repr1() {
        var full = new DeliveryService("d1", 123, 1, 2, 3, 4);
        full.prepareOrder(123, "banán");
        full.prepareOrder(1, "alma");
        assertEquals("DS[d1, 123, 1, 2, 3, 4, 2]", full.toString());
    }

    @Test
    public void repr2() {
        var empty = new DeliveryService("d1", 123);
        empty.prepareOrder(123, "banán");
        assertEquals("DS[d1, 123, 1]", empty.toString());
    }

    @Test
    public void repr3() {
        var full = new DeliveryService("d1", 123, 1, 2, 3, 4);
        assertEquals("DS[d1, 123, 1, 2, 3, 4, 0]", full.toString());
    }

    @Test
    public void testSimilarDS() {
        var d1 = new DeliveryService("d1", 123, 1, 2, 3, 4);
        var d2 = new DeliveryService("d1", 123, 2, 1, 3, 4);
        assertTrue(d1.equals(d2));
    }

    @Test
    public void testDifferentZipButSimilarDS() {
        var d1 = new DeliveryService("d1", 123, 1, 2, 3, 4);
        var d2 = new DeliveryService("d1", 124, 1, 2, 3, 4);
        assertFalse(d1.equals(d2));
    }

    @Test
    public void testDifferentDS() {
        var d1 = new DeliveryService("d1", 123, 1, 2, 3, 4);
        var d2 = new DeliveryService("d2", 678, 1, 2, 6, 7);
        assertFalse(d1.equals(d2));
    }

    @Test
    public void productState1() {
        var full = new DeliveryService("d1", 123, 1, 2, 3, 4);
        full.prepareOrder(123, "banán");
        var result=full.getProductState("banán");
        assertEquals(ProductState.SINGLE_ORDER,result);
    }

    @Test
    public void productState2() {
        var full = new DeliveryService("d1", 123, 1, 2, 3, 4);
        full.prepareOrder(123, "banán");
        full.prepareOrder(2, "banán");
        full.prepareOrder(2, "alma");
        var result=full.getProductState("banán");
        assertEquals(ProductState.MULTI_ORDER,result);
    }

    @Test
    public void productState3() {
        var full = new DeliveryService("d1", 123, 1, 2, 3, 4);
        full.prepareOrder(123, "alma");
        var result=full.getProductState("banán");
        assertEquals(ProductState.NO_ORDER,result);
    }

    @Test
    public void ranged() {
        DeliveryService rd=new RangedDelivery("r1",123, 110,113);
        rd.prepareOrder(117,"alma");
        var result = rd.makeDeliveries();
        assertEquals(1, result.size());
        assertTrue(result.containsKey(117));
    }

    @Test
    public void readFromFile() {
        String path=DeliveryTest.class.getResource("orders.txt").getPath();
        var d = new DeliveryService(path);
        var result=d.makeDeliveries();
        assertEquals(2, result.size());
        assertTrue(result.containsKey(1));
        assertTrue(result.containsKey(3));
        assertEquals(5, (int) result.get(1).get("korte"));
        assertEquals(1, (int) result.get(1).get("alma"));
        assertEquals(1, (int) result.get(1).get("cseresznye"));
        assertEquals(2, (int) result.get(3).get("korte"));
        assertEquals(1, (int) result.get(3).get("cseresznye"));
    }


}
