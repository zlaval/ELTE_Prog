package hu.elte;

class Foo {
    private int x;
    public Foo(int x) {
        this.x = x;
    }
}

public class Main {
    public static void main(String[] args) {
        int counter = 0; //stack -> 0

        Foo obj = new Foo(5); //heap Foo(5), stacken egy referencia rá (obj)
        counter = 10; //stack -> 10

        obj = new Foo(7); //heap Foo(7) létrejön, obj referencia átköt rá, előző Foo(5) gc-zhető

        // 1. Mely objektumokat törölheti a garbage collector ezen a ponton?

        Foo obj2; //null ref stacken
        new Foo(20); //egyből gc-zhető
        obj2 = obj; //stacken obj2 ref az obj-ra referál, ami a (Foo(7))

        // 2. Mely objektumokat törölheti a garbage collector ezen a ponton?

        obj2 = new Foo(30);//heapen Foo(30) létrejön, stacken obj2 átköt, Foo(7)-re mutat az obj így az nem gc-zhető
        obj2 = new Foo(20);//heapen Foo(20) létrejön, obj2 átköt, Foo(30) gc-zhető
    }
}