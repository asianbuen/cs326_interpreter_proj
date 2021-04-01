//(3)
//
//Hello
//We are running witout a main!
//Not really the right main
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
    static public void println(boolean b){ }

    static public int readInt() { }
    static public long readLong() { }
    static public float readFloat() { }
    static public double readDouble() { }
    static public String readString() { }
}

public class LookNoMain {
    public static void main(String[] args) { }
    
    public void fakeMain() { 
	Io.println("Not really the right main");
    }

    public static void notMain() {
	Io.println("We are running witout a main!");
	new LookNoMain().fakeMain();
//	System.exit();
    }


    static {
	Io.println("Hello");
	notMain();
    }
}
