
//import System;

class Io {
    static public void print(byte b) { }
    static public void print(short s) { }
    static public void print(char c) { }
    static public void print(int i) { }
    static public void print(long l) { }
    static public void print(float f) { }
    static public void print(double d){ }
    static public void print(String s){ }
    static public void print(boolean b){ }
    static public void println(byte b) { }
    static public void println(short s){ }
    static public void println(char c) { }
    static public void println(int i) { }
    static public void println(long l) { }
    static public void println(float f){ }
    static public void println(double d){ }
    static public void println(String s){ }
    static public void println(boolean b){ } //println

    static public int readInt() { }
    static public long readLong() { }
    static public float readFloat() { }
    static public double readDouble() { }
    static public String readString() { }
}

class Box {
    double width;
    double height;
    double depth;

    // Clone object
    Box(Box ob) {
        width = ob.width;
        height = ob.height;
        depth  = ob.depth;
    }

    // Initialize dimensions
    Box(double w, double h, double d) {
        width = w;
        height = h;
        depth = d;
    }

    // Default constructor
    Box() {
        width = -1;
        height = width;
        depth = height;
    }

    // Create cube
    Box(double len) {
        width = height = depth = len;
    }

    // Compute and return volume
    double volume() {
        return width * height * depth;
    }
}

class BoxWeight extends Box {
    double weight;

    BoxWeight(double w, double h, double d, double m) {
        width = w;
        height = h;
        depth = d;
        weight = w;
    }
}

public class DemoBox {
    public static void main(String[] args) {
        BoxWeight myBox1 = new BoxWeight(10.0, 20.0, 15.0, 34.3);
        BoxWeight myBox2 = new BoxWeight(2.0, 3.0, 4.0, 0.76);
        double vol;

        vol = myBox1.volume();
        Io.print("Volume of my myBox1 is "); Io.println(vol);
        Io.print("Weight of my myBox1 is "); Io.println(myBox1.weight);

        vol = myBox2.volume();
        Io.print("Volume of my myBox2 is "); Io.println(vol);
        Io.print("Weight of my myBox2 is "); Io.println(myBox2.weight);
    }
}