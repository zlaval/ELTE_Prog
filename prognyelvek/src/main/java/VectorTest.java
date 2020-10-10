import hu.elte.exceptions.NegativeVectorSizeException;
import hu.elte.exceptions.SmallerDestinationVectorException;
import hu.elte.math.Vector;

public class VectorTest {

    public static void main(String[] args) {
        testAdd();
        System.out.println("testAdd passed");
        testIncompatibleVectors();
        System.out.println("testIncompatibleVectors passed");
        testConstructorFails();
        System.out.println("testConstructorFails passed");
        testToString();
        System.out.println("testToString passed");

        // :)
        System.out.println("All tests passed");
    }

    private static void testAdd() {
        Vector vector1 = new Vector(2);
        vector1.setElement(0, 1);
        vector1.setElement(1, 2);
        Vector vector2 = new Vector(2);
        vector2.setElement(0, 3);
        vector2.setElement(1, 4);

        vector1.add(vector2);
        assertEquals(vector1.getElement(0), 4, "wrong add or getElement implementation");
        assertEquals(vector1.getElement(1), 6, "wrong add or getElement implementation");
    }

    private static void testIncompatibleVectors() {
        try {
            Vector vector1 = new Vector(2);
            Vector vector2 = new Vector(3);
            vector1.add(vector2);
            throw new AssertionError("add operation accepted incompatible vectors");
        } catch (SmallerDestinationVectorException e) {
            // ok
        }
    }

    private static void testToString() {
        Vector vector = new Vector(3);
        vector.setElement(0, 1);
        vector.setElement(1, 2);
        vector.setElement(2, 3);
        String vectorRepresentation = vector.toString();

        assertEquals(vectorRepresentation, "[1, 2, 3]", "wrong toString representation");
    }

    private static void assertEquals(Object actual, Object expected, String message) {
        if (!actual.equals(expected)) {
            throw new AssertionError(
                    "Message: " + message + ". Expected was " + expected + " but actual was " + actual);
        }
    }

    private static void testConstructorFails() {
        try {
            new Vector(-1);
            throw new AssertionError("constructor accepted invalid length");
        } catch (NegativeVectorSizeException e) {
            // ok
        }
    }

}