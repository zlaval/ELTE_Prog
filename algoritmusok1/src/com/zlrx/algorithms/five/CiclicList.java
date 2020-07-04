package com.zlrx.algorithms.five;

import java.util.Arrays;
import java.util.List;

public class CiclicList {

    class Node {
        Node previous;
        String value;
        Node next;

        public Node(Node previous, String value, Node next) {
            this.previous = previous;
            this.value = value;
            this.next = next;
        }

        public Node(String value) {
            this.value = value;
        }


    }

    Node first = null;

    void insertHead(String value) {
        Node node = new Node(value);
        if (first == null) {
            first = node;
            first.previous = node;
            first.next = node;
        } else {
            node.next = first;
            node.previous = first.previous;
            first = node;
        }
    }

    void insertAll(List<String> words) {
        for (int i = words.size() - 1; i >= 0; i--) {
            insertHead(words.get(i));
        }
    }

    void deleteActual() {
        Node actual = first;
        Node previous = actual.previous;
        previous.next = actual.next;
        previous.next.previous = previous;
        first = previous.next;

    }

    boolean stayOne() {
        return first.previous == first;
    }

    void stepFirst(int step) {
        while (step > 0) {
            first = first.next;
            step--;
        }
    }


    public static void main(String[] args) {
        CiclicList ciclicList = new CiclicList();
        ciclicList.insertAll(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"));
        int m = 3;
        while (!ciclicList.stayOne()) {
            ciclicList.stepFirst(m);
            ciclicList.deleteActual();
        }
        System.out.println(ciclicList.first.value);
    }


}
