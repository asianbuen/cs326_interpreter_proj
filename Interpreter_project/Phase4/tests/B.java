//import java.util.*;
import System;

public class B {

    public int b;

    B() {
        b = 100;
    }

    public void modifyB(int x) {
        b = b + x;
    }

    public static void main(String[] args) {
        B myB = new B();

        myB.modifyB(23);

        System.out.println(myB.b);

        B myC = new B();

        myC.modifyB(345);

        System.out.println(myC.b);
    }
}
