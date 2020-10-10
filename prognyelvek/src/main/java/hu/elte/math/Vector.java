package hu.elte.math;

import hu.elte.exceptions.NegativeVectorSizeException;
import hu.elte.exceptions.SmallerDestinationVectorException;

public class Vector {

    private final int[] elements;

    /**
     * Creates a fixed sizes int Vector.
     *
     * @param numberOfElements represents the size of the backing array
     */
    public Vector(int numberOfElements) {
        if (numberOfElements < 0) {
            throw new NegativeVectorSizeException("Vector size must be at least 0");
        }
        this.elements = new int[numberOfElements];
    }

    /**
     * Copy the elements from the given Vector into this instance.
     *
     * @param other Vector to copy elements from. Size must be less or equal to this Vector.
     * @throws SmallerDestinationVectorException when the size of the input Vector is greater than this Vector.
     */
    public void add(Vector other) {
        if (this.getLength() < other.getLength()) {
            throw new SmallerDestinationVectorException("Cannot copy the elements into a smaller vector.");
        }
        for (int i = 0; i < elements.length; i++) {
            elements[i] += other.elements[i];
        }
    }

    public int getElement(int index) {
        return elements[index];
    }

    public void setElement(int index, int element) {
        elements[index] = element;
    }

    public int getLength() {
        return elements.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i]);
            if (i != elements.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}