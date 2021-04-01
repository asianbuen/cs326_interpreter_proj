//import java.util.*;
import System;

class A {
  public void callme() {
    System.out.println("Inside A's callme method");
  }
}

class B extends A {
  public void callme() {
    // Override callme()
    System.out.println("Inside B's callme method");
  }
}

class C extends A {
  // Override callme()
  public void callme() {
    System.out.println("Inside C's callme method");
  }
}

class D extends A {
  // Override callme()
  public void callme() {
    System.out.println("Inside D's callme method");
  }
}

public class MyClass {
  public static void main(String args[]) {
    Object obj = null;
    A      r   = null;
    A      a   = new A();
    B      b   = new B();
    C      c   = new C();
    D      d   = new D();

    // Normal invocation
    a.callme();
    b.callme();
    c.callme();
    d.callme();

    // Dynamic dispatch
    r = a;
    r.callme();

    r = b;
    r.callme();

    r = c;
    r.callme();

    // Casting objects
    obj = new A();
    r   = (A)obj;
    r.callme();
  }
}
