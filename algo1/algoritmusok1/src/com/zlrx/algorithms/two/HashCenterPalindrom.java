package com.zlrx.algorithms.two;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HashCenterPalindrom {

    public static void main(String[] args) {
        System.out.println(hashCenterPalindrom("ab#ba"));
        System.out.println(hashCenterPalindrom("#"));
        System.out.println(hashCenterPalindrom("####"));
        System.out.println(hashCenterPalindrom("abc#cbaa#a"));
        System.out.println(hashCenterPalindrom("abc"));
        System.out.println(hashCenterPalindrom("ab#baa"));
        System.out.println(hashCenterPalindrom("ab#baa##a"));
        System.out.println(hashCenterPalindrom("abcd#dcbaa#abb#bbasf#fsa"));
    }


    static boolean hashCenterPalindrom(String str) {
        char[] chars = str.toCharArray();
        List<String> character = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            String c = String.valueOf(chars[i]);
            character.add(c);
        }

        Stack<String> stack = new Stack<>();
        boolean shouldPop = false;
        for (String s : character) {
            if ("#".equals(s) && (!shouldPop || stack.isEmpty())) {
                shouldPop = true;
            } else if (shouldPop) {
                String popped = stack.pop();
                if (!popped.equals(s)) {
                    return false;
                }
                shouldPop = !stack.isEmpty();
            } else {
                stack.push(s);
            }
        }
        return stack.isEmpty();
    }


}
