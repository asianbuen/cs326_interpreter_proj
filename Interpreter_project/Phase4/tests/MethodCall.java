import System;

public class MethodCall {
    public static void printMessage(int a, float b) {
        System.out.println("Hello from printMessage(" + a + "," + b + ")");
    }

    public static void main(String[] args) {
        System.out.println("Hello from Main");
        printMessage(45, 98.5f);
    }
}
