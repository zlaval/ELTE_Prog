package com.zlrx.java.advanced.classes.refl;

import java.lang.reflect.Modifier;
import java.util.Arrays;

class A {
    private int a;
    private String b;
}

class B {
    int a;
    public int b;
}


public class First {

    public static void main(String[] args) {
        System.out.println("A all field private: " + isAllPrivate(A.class));
        System.out.println("B all field private: " + isAllPrivate(B.class));
    }

    static <T> boolean isAllPrivate(Class<T> clzz) {
        var fields = clzz.getDeclaredFields();
        return Arrays.stream(fields).allMatch(f -> Modifier.isPrivate(f.getModifiers()));
    }


}
