package com.zlrx.algorithms.four;

import java.util.Stack;

public class EvaluateFullyBracedExpression {


    public static void main(String[] args) {
        EvaluateFullyBracedExpression e = new EvaluateFullyBracedExpression();
        e.evaluate("(((2+4)/(3-6))-(1*(4+1)))");

    }


    public double evaluate(String s) {
        Stack<String> op = new Stack<>();
        Stack<String> num = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            String x = s.substring(i, i + 1);
            if (x.equals("(")) {
            } else if (isOperator(x)) {
                //TODO
                op.push(x);
            } else if (x.equals(")")) {
                String b = num.pop();
                String j = num.pop();
                String muv = op.pop();
                num.push(j + muv + b);
                System.out.println(j + muv + b);
            } else {
                num.push(x);
            }

        }

        return 0d;

    }


    boolean isOperator(String ch) {
        return "+-*/^=".contains(ch);
    }
}
