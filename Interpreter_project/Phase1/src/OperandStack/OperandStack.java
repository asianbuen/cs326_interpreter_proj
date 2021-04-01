package OperandStack;
import Value.*;
import java.io.*;

/**
 * A stack for doing arithmetic and logic operations. Stack elements are of type {@link Value}.
 * @author   Matt B. Pedersen
 * @version  1.0
*/
public class OperandStack {

    /**
     * The size of the operand stack. We could have made this dynamic, but as long as we choose a number
     * large enough it should be fine with a fixed sized stack
     */
    private int stackSize;
    /** 
     * The internal stack holding the actual values. These values are of type {@link Value}.
     */
    // Hint, if you don't make it an array the toString() method wont work as it is ;-)
    // YOUR CODE HERE
	private Value[] stack;	// stack array declaration

    /**
     * The stack pointer. The stack pointer always points to the next free location in the <a href="#stack">stack<a> array.
     */
    // YOUR CODE HERE
	private int sp = 0;

    /**
     * Just a name to give to the stack - helps with debugging later.
     */
    private String name;
    /**
     * We keep track of the numbers of stacks created - just for statistics later.
     */
    private static int stackNo = 0;
    public int stackNumber;

    /**
     * Creates a new operand stack of size <b>size</b> and sets the stack pointer to 0.
     * @param size The size of the newly created operand stack.
     * @see #stack
     * @see #sp 
     */
    public OperandStack(int size, String name) {
		this.name = name;	// name of newly created stack
		stackNumber = stackNo;	// stack number
		stackNo++;

	// YOUR CODE HERE
		stackSize = size;	// set the parameter 'size' value into 'stackSize'
		stack = new Value[stackSize]; // stack array allocation
    }

    /**
     * Pushes one element of type {@link Value} on to the operand stack and increments the stack pointer (sp) by one.
     * <p>
     * stack before push: .... X<br>
     * push(Y);<br>
     * stack after push:  .... X Y
     * <p>
     * An error is signaled if no more room is available on the stack.
     * @param e An object of the {@link Value} type to be placed on the stack.
     */
    public void push(Value e) {
	// YOUR CODE HERE
		if (sp >= stackSize) {
			System.out.println("Error: no more space on stack");    // display error msg
			System.exit(1);    // terminate execution
		}

		stack[sp] = e;	// assign parameter 'e' to the stack
		sp++; 			// increment the stack pointer to point to the next open spot
    }

    /**
     * Pops one element of type {@link Value} off the operand stack and decrements the stack pointer (sp) by one.
     * <p>
     * stack before pop: .... X Y<br>
     * Z = pop();<br>
     * stack after pop:  .... X<br>
     * and Z = Y
     * <p>
     * An error is signaled if the stack is empty.
     * @return Returns an object of type {@link Value}.
     */
    public Value pop() {
	// YOUR CODE HERE
		/*
		algorithm:
		 - if sp <= 0:
		 			- display error message (use System.out.println("msg goes in here.."))
		 			- System.exit(1) // terminate execution
		 - else:
		 			- decrement sp
		 			- create a temporary Value object and store current value that sp is pointing to in the temp
		 			  variable
		 			- set the current value that sp is pointing to to 'null' (remove)
		 			- return the temp Value object that holds the top of the stack
		 */

		if (sp <= 0) {
			System.out.println("Error: no more elements in the stack");
			System.exit(1);
		}

		sp--;
		Value temp = stack[sp];
		stack[sp] = null;
		return temp;
    }
    
    /** 
     * Returns the n'th element on the stack (counted from the top)
     * without removing it.
     *
     * @param n The index (counting from the top of the stack) of the 
     * element to be returned. The top element is at index 1.
     */
    public Value peek(int n) {
	// YOUR CODE HERE
		/*
		algorithm:
			- if sp <= 0:
						- display error message
						- System.exit(1) // terminate execution
			- if n >= stackSize:
						- display error message
						- terminate execution
			- if stack[n] == null:
						- display error message
						- terminate execution
			- default:
						- return opStack[n];
		 */

		if (sp <= 0) {
			System.out.println("Error: no more elements in the stack");
			System.exit(1);
		}

		if (n >= stackSize) {
			System.out.println("Error: n'th element is beyond the stack size");
			System.exit(1);
		}

		if (stack[n] == null) {
			System.out.println("Error: null element");
			System.exit(1);
		}

		return stack[n];
    }

    /**
	 * A method that checks if the stack is empty or not. Returns true if stack is empty,
	 * or returns false if the stack is NOT empty.
     */

    public boolean isEmpty() {
		if (sp <= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	    
    /**
     * Prints out the operand stack with information about every elements type.
     */
    public void dump(PrintWriter out) {
	out.println(toString());
    }

    public String toString() {
	String s = "";
	s = "| Operand Stack " + stackNumber + " - "+ name + " (size = "+sp+") --------------- \n| ";
        for (int i=0;i<sp;i++) {
            if (stack[i] == null)
                s = s + "null ";
            else
                s = s + stack[i] + "{" + stack[i].type2String()+ "} ";
        }
	s = s + "\n+----------------------------------------------------------------";
	return s;
    }


    public void dump_(PrintWriter out) {
	int max = 20;
	// compute the max width of any element
	for (int i=0;i<sp;i++) {
	    if (stack[i] == null)
		max = 6 > max ? 6 : max; // ' null '
	    else {
		int l;
		l  = (stack[i] + " {" + stack[i].type2String()+ "}").length();
		max = l > max ? l : max;
	    }   
	}
	
	int left, right, spcs;

	for (int i=0;i<sp;i++) {
	    if (stack[sp-i-1] == null) {
		spcs = max - 6;
		left = spcs/2;
		right = spcs - left;
		out.println("|" + spaces(left) + " null " + spaces(right) + "|");
	    } else {
		String st = "" + stack[sp-i-1] + " {" + stack[sp-i-1].type2String()+ "}";
		spcs = max - st.length();
		left = spcs / 2;
		right = spcs - left;
		out.println("|" + spaces(left) + st + spaces(right) + "|");
	    }
	}
    }
    
    private String spaces(int n) {
	String s = "";
	for (int i =0; i<n; i++) 
	    s += " ";
	return s;
    }


}


	    
