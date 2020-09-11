package com.zlrx.algorithms.three;

import java.util.Stack;

public class PolishNotation {

    public static void main(String[] args) {
        PolishNotation polishNotation = new PolishNotation();
        polishNotation.convertToPolishNotation("x=a+b*c-(d^(e*f-g)*h+i)/j+k*l^n-m");
        //polishNotation.convertToPolishNotation("y=z=a^2+x/(5-e*(a^3^k/l-f))-d");


    }


    public void convertToPolishNotation(String s) {
        Stack<String> stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            String x = s.substring(i, i + 1);
            if (isOperator(x)) {
                if (isLeftRightOperator(x)) {
                    while (!stack.isEmpty() && !stack.peek().equals("(") && getPriority(x) <= getPriority(stack.peek())) {
                        System.out.print(stack.pop());
                    }
                    stack.push(x);
                } else {
                    while (!stack.isEmpty() && !stack.peek().equals("(") && getPriority(x) < getPriority(stack.peek())) {
                        System.out.print(stack.pop());
                    }
                    stack.push(x);
                }
            } else {
                if (x.equals("(")) {
                    stack.push(x);
                } else if (x.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        System.out.print(stack.pop());
                    }
                    stack.pop();
                } else {
                    System.out.print(x);
                }
            }
        }
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
    }

    int getPriority(String ch) {
        switch (ch) {
            case "=":
                return 1;
            case "+":
            case "-":
                return 2;
            case "*":
            case "/":
                return 3;
            case "^":
                return 4;
            default:
                throw new RuntimeException(ch);
        }
    }

    boolean isLeftRightOperator(String ch) {
        return "+-*/".contains(ch);
    }

    boolean isOperator(String ch) {
        return "+-*/^=".contains(ch);
    }


}
