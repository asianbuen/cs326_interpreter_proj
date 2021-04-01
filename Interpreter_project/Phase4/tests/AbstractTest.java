import System;

abstract class A {
    abstract void callme();

    void solidCallme() {
        System.out.println("This a solid method");
    }
}

class B extends A {
    void callme() {
        System.out.println("B's implementation of callme()");
    }
}

class C extends A {
    void callme() {
        System.out.println("C's implementation of callme()");
    }
}

class AbstractTest {
    public static void main(String[] args) {
        B b = new B();
        C c = new C();

        b.callme();
        c.solidCallme();
    }
}