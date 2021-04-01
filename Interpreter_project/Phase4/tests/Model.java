//import java.util.*;
import System;

public class Model {
    private int val;

    public Model()
    {
        val = 0;
    }

    public void set(int w)
    {
        Model x = new Model();
        val = w;
    }

    public int get()
    {
        return val;
    }

    public static void main(String[] args) {
        int x = 2;
        Model m = new Model();
        m.set(x + 1);
        System.out.println(m.get());
        x = 3;
    }
}
