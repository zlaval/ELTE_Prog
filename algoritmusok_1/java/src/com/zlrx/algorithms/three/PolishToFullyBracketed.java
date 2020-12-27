package com.zlrx.algorithms.three;

import java.util.Stack;

public class PolishToFullyBracketed {

    public static void main(String[] args) {
        PolishToFullyBracketed p = new PolishToFullyBracketed();
        p.convert("24+3/1-");
        p.convert("xab+cd-*fgh-^/j+1-i-=");
        p.convert("yza2^x5ea3k^^l/f-*-/+d-==");
    }

    public void convert(String s) {
        Stack<String> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            String x = s.substring(i, i + 1);
            if (isOperator(x)) {
                String b = stack.pop();
                String a = stack.pop();
                stack.push("(" + a + x + b + ")");
            } else {
                stack.push(x);
            }

        }
        System.out.println(stack.pop());

    }

    boolean isOperator(String ch) {
        return "+-*/^=".contains(ch);
    }

}
