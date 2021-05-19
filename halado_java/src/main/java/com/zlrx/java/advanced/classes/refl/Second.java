package com.zlrx.java.advanced.classes.refl;


import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.stream.Collectors;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface Authors {
    Author[] value();
}

@Repeatable(Authors.class)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@interface Author {

    String value() default "";

}

@Author("Zalan")
@Author("Zalval")
class C {

    @Author("Zlaval")
    public void printName() {
        System.out.println("Some name");
    }

    @Author("Zalan")
    public void sameAuthor() {

    }

    @Author("Zed")
    private void thirdAuthor() {

    }

    public void notAnnotated() {
    }

}


public class Second {

    public static void main(String[] args) throws ClassNotFoundException {
        printAuthor("com.zlrx.java.advanced.classes.refl.C");
    }

    static void printDifferentAuthor() {
        //same as prev but + 1 if
    }

    static void printAuthor(String className) throws ClassNotFoundException {
        var clazz = Class.forName(className);
        if (clazz.isAnnotationPresent(Authors.class)) {
            var annotation = clazz.getAnnotation(Authors.class);
            System.out.println("Class " + clazz.getName() + " created by " + Arrays.stream(annotation.value()).map(Author::value).collect(Collectors.joining(", ")));
        } else if (clazz.isAnnotationPresent(Author.class)) {
            var annotation = clazz.getAnnotation(Author.class);
            System.out.println("Method " + clazz.getName() + " creeated by " + annotation.value());
        }

        var methods = clazz.getDeclaredMethods();
        Arrays.stream(methods).forEach(m -> {
            if (m.isAnnotationPresent(Authors.class)) {
                var annotation = m.getAnnotation(Authors.class);
                System.out.println("Method " + m.getName() + " creeated by " + Arrays.stream(annotation.value()).map(Author::value).collect(Collectors.joining()));
            } else if (m.isAnnotationPresent(Author.class)) {
                var annotation = m.getAnnotation(Author.class);
                System.out.println("Method " + m.getName() + " creeated by " + annotation.value());
            }


        });

    }
}
