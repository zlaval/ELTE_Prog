// figyelem: HIBAS feladat, ki kell javitani

package roo;

public class Kangaroo {
    String name;
    int age;

    public Kangaroo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Kangaroo(int age) {
        this.age = age;
    }

    public void Kangaroo(int legs) {
        System.out.println("A kengurunak " + legs + " laba van.");
    }

    public void display(String country) {
        System.out.println("A kenguru neve: " + name + ", lakohelye: " + country);
        System.out.println("A kenguru kora: " + age++);
    }
}
