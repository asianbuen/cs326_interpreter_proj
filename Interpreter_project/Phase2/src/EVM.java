import EVM.*;
import Utilities.*;
import Utilities.Error;
import Value.*;
import OperandStack.*;
import Instruction.*;
import java.util.*;
import java.io.*;

public class EVM {

    static private void binOp(int opcode, int type, OperandStack operandStack) {
        Value o1, o2;

        o1 = operandStack.pop();
        o2 = operandStack.pop();

        // Check that the operands have the right type
        if (!(o1.getType() == type && o2.getType() == type))
            Error.error("Error: Type mismatch - operands do not match operator.");

        switch (opcode) {
            // type: double
            case RuntimeConstants.opc_dadd: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() + ((DoubleValue)o1).getValue())); break;
            case RuntimeConstants.opc_dsub: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() - ((DoubleValue)o1).getValue())); break;
            case RuntimeConstants.opc_dmul: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() * ((DoubleValue)o1).getValue())); break;
            case RuntimeConstants.opc_ddiv: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() / ((DoubleValue)o1).getValue())); break;
            case RuntimeConstants.opc_drem: operandStack.push(new DoubleValue(((DoubleValue)o2).getValue() % ((DoubleValue)o1).getValue())); break;

            // type: float
            case RuntimeConstants.opc_fadd: operandStack.push(new FloatValue(((FloatValue)o2).getValue() + ((FloatValue)o1).getValue())); break;
            case RuntimeConstants.opc_fsub: operandStack.push(new FloatValue(((FloatValue)o2).getValue() - ((FloatValue)o1).getValue())); break;
            case RuntimeConstants.opc_fmul: operandStack.push(new FloatValue(((FloatValue)o2).getValue() * ((FloatValue)o1).getValue())); break;
            case RuntimeConstants.opc_fdiv: operandStack.push(new FloatValue(((FloatValue)o2).getValue() / ((FloatValue)o1).getValue())); break;
            case RuntimeConstants.opc_frem: operandStack.push(new FloatValue(((FloatValue)o2).getValue() % ((FloatValue)o1).getValue())); break;

            // type: long
            case RuntimeConstants.opc_ladd: operandStack.push(new LongValue(((LongValue)o2).getValue() + ((LongValue)o1).getValue())); break;
            case RuntimeConstants.opc_lsub: operandStack.push(new LongValue(((LongValue)o2).getValue() - ((LongValue)o1).getValue())); break;
            case RuntimeConstants.opc_lmul: operandStack.push(new LongValue(((LongValue)o2).getValue() * ((LongValue)o1).getValue())); break;
            case RuntimeConstants.opc_ldiv: operandStack.push(new LongValue(((LongValue)o2).getValue() / ((LongValue)o1).getValue())); break;
            case RuntimeConstants.opc_lrem: operandStack.push(new LongValue(((LongValue)o2).getValue() % ((LongValue)o1).getValue())); break;

            // type: integer
            case RuntimeConstants.opc_iadd: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() + ((IntegerValue)o1).getValue())); break;
            case RuntimeConstants.opc_isub: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() - ((IntegerValue)o1).getValue())); break;
            case RuntimeConstants.opc_imul: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() * ((IntegerValue)o1).getValue())); break;
            case RuntimeConstants.opc_idiv: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() / ((IntegerValue)o1).getValue())); break;
            case RuntimeConstants.opc_irem: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() % ((IntegerValue)o1).getValue())); break;

            default:
                Error.error("invalid opcode.");


                // Finish this method

        }

    }

    static private void swap(OperandStack operandStack) {
        Value o1, o2;

        o1 = operandStack.pop();
        o2 = operandStack.pop();

        if (o1.getType() == Value.s_long || o1.getType() == Value.s_double ||
                o2.getType() == Value.s_long || o2.getType() == Value.s_double){
            Error.error("type(s) invalid for this function - types must either be an integer or float");
        }

        // swap the objects..
        operandStack.push(o1);
        operandStack.push(o2);

    }

    static private void negate(int type, OperandStack operandStack) {

        // Yours code goes here.
        Value o1;

        o1 = operandStack.pop();

        switch (type){
                // opcode: ineg
            case Value.s_integer:
                operandStack.push(new IntegerValue(-1*((IntegerValue)o1).getValue())); break;
                // opcode: lneg
            case Value.s_long:
                operandStack.push(new LongValue(-1*((LongValue)o1).getValue())); break;
                // opcode: fneg
            case Value.s_float:
                operandStack.push(new FloatValue(-1*((FloatValue)o1).getValue())); break;
                // opcode: dneg
            case Value.s_double:
                operandStack.push(new DoubleValue(-1.0*((DoubleValue)o1).getValue())); break;
            default:
                Error.error("invalid type - integer, long, float, and double types only");
        }


    }

    static private void cmp(int type, OperandStack operandStack) {
        Value o1, o2;

        o1 = operandStack.pop();
        o2 = operandStack.pop();

        // Perform assertions:
        // 1. check if the type is an integer
        // 2. check if they are of the same type

        if (type == Value.s_integer) {
            Error.error("cannot compare integer values for this function");
        }

        if (!(o1.getType() == type && o2.getType() == type)) {
            Error.error("the types of either (or both) of the 2 Value objects do not match the given type.");
        }

        switch(type) {
            // instructions: fcmpl/fcmpg (float comparison)
            case Value.s_float:
                if (((FloatValue)o1).getValue() > ((FloatValue)o2).getValue()) {
                    operandStack.push(new IntegerValue(1));
                }
                else if (((FloatValue)o1).getValue() == ((FloatValue)o2).getValue()) {
                    operandStack.push(new IntegerValue(0));
                }
                else if (((FloatValue)o1).getValue() < ((FloatValue)o2).getValue()) {
                    operandStack.push(new IntegerValue(-1));
                }
                break;
            // instructions: dcmpl/dcmpg (double comparison)
            case Value.s_double:
                if (((DoubleValue)o1).getValue() > ((DoubleValue)o2).getValue()) {
                    operandStack.push(new IntegerValue(1));
                }
                else if (((DoubleValue)o1).getValue() == ((DoubleValue)o2).getValue()) {
                    operandStack.push(new IntegerValue(0));
                }
                else if (((DoubleValue)o1).getValue() < ((DoubleValue)o2).getValue()) {
                    operandStack.push(new IntegerValue(-1));
                }
                break;
            // instruction: lcmp (long comparison)
            case Value.s_long:
                if (((LongValue)o1).getValue() > ((LongValue)o2).getValue()) {
                    operandStack.push(new IntegerValue(1));
                }
                else if (((LongValue)o1).getValue() == ((LongValue)o2).getValue()) {
                    operandStack.push(new IntegerValue(0));
                }
                else if (((LongValue)o1).getValue() < ((LongValue)o2).getValue()) {
                    operandStack.push(new IntegerValue(-1));
                }
                break;

            default: Error.error("invalid type - float, double, or long only");

        }

    }

    static private void two(int from, int to, OperandStack operandStack) {

        Value e = operandStack.pop();
        if (e.getType() != from)
            Error.error("OperandStack.two: Type mismatch.");

        switch (from) {
            case Value.s_integer:
                int iv = ((IntegerValue)e).getValue();
                switch (to) {
                    case Value.s_byte:   operandStack.push(new IntegerValue((int)((byte) iv))); break;
                    case Value.s_char:   operandStack.push(new IntegerValue((int)((char) iv))); break;
                    case Value.s_short:  operandStack.push(new IntegerValue((int)((short)iv))); break;
                    case Value.s_double: operandStack.push(new DoubleValue((double)iv)); break;
                    case Value.s_float:  operandStack.push(new FloatValue((float)iv)); break;
                    case Value.s_long:   operandStack.push(new LongValue((long)iv)); break;
                    default: Error.error("invalid type");
                }
                break;

            case Value.s_float:
                float fv = ((FloatValue)e).getValue();
                switch(to) {
                    case Value.s_integer:  operandStack.push(new IntegerValue((int) fv)); break;
                    case Value.s_double:   operandStack.push(new DoubleValue((double) fv)); break;
                    case Value.s_long:     operandStack.push(new LongValue((long) fv)); break;
                    default: Error.error("invalid type");
                }
                break;

            case Value.s_double:
                double dv = ((DoubleValue)e).getValue();
                switch(to) {
                    case Value.s_integer:  operandStack.push(new IntegerValue((int) dv)); break;
                    case Value.s_float:    operandStack.push(new FloatValue((float) dv)); break;
                    case Value.s_long:     operandStack.push(new LongValue((long) dv)); break;
                    default: Error.error("invalid type");
                }
                break;

            case Value.s_long:
                long lv = ((LongValue)e).getValue();
                switch(to) {
                    case Value.s_integer:  operandStack.push(new IntegerValue((int) lv)); break;
                    case Value.s_float:    operandStack.push(new FloatValue((float) lv)); break;
                    case Value.s_double:   operandStack.push(new DoubleValue((double) lv)); break;
                    default: Error.error("invalid type");
                }
                break;

            default: Error.error("invalid type");
        }
    }


    static private void dup(int opCode, OperandStack operandStack) {
        // In real JVM a Double or a Long take up 2 stack words, but EVM Doubles and Longs
        // do not, so since dup2 can be used to either duplicate 2 single word values or
        // 1 double word value, we need to check the type of what is on the stack before
        // we decide if we should duplicate just one value or two.
        switch (opCode) {
            case RuntimeConstants.opc_dup:   operandStack.push(operandStack.peek(1)); break;
            case RuntimeConstants.opc_dup2: {
                Value o1 = operandStack.peek(1);
                Value o2;
                if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue))
                    operandStack.push(o1);
                else {
                    o2 = operandStack.peek(2);
                    operandStack.push(o2);
                    operandStack.push(o1);
                }
            }
            break;
            case RuntimeConstants.opc_dup_x1: {
                Value o1 = operandStack.pop();
                Value o2 = operandStack.pop();
                if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue) ||
                        (o2 instanceof DoubleValue) || (o2 instanceof LongValue))
                    Error.error("Error: dup_x1 cannot be used on value of type Double or Long.");
                operandStack.push(o1);
                operandStack.push(o2);
                operandStack.push(o1);
            }
            break;
            case RuntimeConstants.opc_dup_x2: {
                Value o1 = operandStack.pop();
                Value o2 = operandStack.pop();
                if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue))
                    Error.error("Error: dup_x2 cannot be used on value of type Double or Long.");
                if ((o2 instanceof DoubleValue) || (o2 instanceof LongValue)) {
                    operandStack.push(o1);
                    operandStack.push(o2);
                    operandStack.push(o1);
                } else {
                    Value o3 = operandStack.pop();
                    if ((o3 instanceof DoubleValue) || (o3 instanceof LongValue))
                        Error.error("Error: word3 of dup_x2 cannot be  of type Double or Long.");
                    operandStack.push(o1);
                    operandStack.push(o3);
                    operandStack.push(o2);
                    operandStack.push(o1);
                }
            }
            break;
            case RuntimeConstants.opc_dup2_x1: {
                Value o1 = operandStack.pop();
                if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue)) {
                    Value o2 = operandStack.pop();
                    if ((o2 instanceof DoubleValue) || (o2 instanceof LongValue))
                        Error.error("Error: word3 of dup2_x1 cannot be of type Double or Long.");
                    operandStack.push(o1);
                    operandStack.push(o2);
                    operandStack.push(o1);
                } else {
                    Value o2 = operandStack.pop();
                    if ((o2 instanceof DoubleValue) || (o2 instanceof LongValue))
                        Error.error("Error: word2 of dup2_x1 cannot be of type Double or Long when word1 is not.");
                    Value o3 = operandStack.pop();
                    if ((o3 instanceof DoubleValue) || (o3 instanceof LongValue))
                        Error.error("Error: word3 of dup2_x1 cannot be of type Double or Long.");
                    operandStack.push(o2);
                    operandStack.push(o1);
                    operandStack.push(o3);
                    operandStack.push(o2);
                    operandStack.push(o1);
                }
            }
            break;
            case RuntimeConstants.opc_dup2_x2: {
                Value o1 = operandStack.pop();
                if ((o1 instanceof DoubleValue) || (o1 instanceof LongValue)) {
                    Value o2 = operandStack.pop();
                    if (!((o2 instanceof DoubleValue) || (o2 instanceof LongValue)))
                        Error.error("Error: word3 of dup2_x2 must be of type Double or Long.");
                    operandStack.push(o1);
                    operandStack.push(o2);
                    operandStack.push(o1);
                } else {
                    Value o2 = operandStack.pop();
                    if ((o2 instanceof DoubleValue) || (o2 instanceof LongValue))
                        Error.error("Error: word2 of dup2_x2 cannot be of type Double or Long when word1 is not.");
                    Value o3 = operandStack.pop();
                    if (!((o3 instanceof DoubleValue) || (o3 instanceof LongValue)))
                        Error.error("Error: word3/4 of dup2_x2 must be of type Double or Long.");
                    operandStack.push(o2);
                    operandStack.push(o1);
                    operandStack.push(o3);
                    operandStack.push(o2);
                    operandStack.push(o1);
                }
            }
            break;
        }
    }


    static private void logic(int inst, OperandStack operandStack) {

        Value o1, o2;
        o1 = operandStack.pop();
        o2 = operandStack.pop();

        // check if the types are not integer or long values --> invalid types for this function, throw error and kill
        if (o1.getType() == Value.s_float || o1.getType() == Value.s_double ||
                o2.getType() == Value.s_float || o2.getType() == Value.s_double) {
            Error.error("type(s) invalid for this function - types must either be an integer or long values");
        }

        // check if the types are the same, if not throw error and kill the program
        if (o1.getType() != o2.getType()) {
            Error.error("types do not match");
        }

        switch(inst) {
            // type: integer value
            case RuntimeConstants.opc_iand: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() & ((IntegerValue)o1).getValue())); break;
            case RuntimeConstants.opc_ior: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() | ((IntegerValue)o1).getValue())); break;
            case RuntimeConstants.opc_ixor: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() ^ ((IntegerValue)o1).getValue())); break;

            // type: long value
            case RuntimeConstants.opc_land: operandStack.push(new LongValue(((LongValue)o2).getValue() & ((LongValue)o1).getValue())); break;
            case RuntimeConstants.opc_lor: operandStack.push(new LongValue(((LongValue)o2).getValue() | ((LongValue)o1).getValue())); break;
            case RuntimeConstants.opc_lxor: operandStack.push(new LongValue(((LongValue)o2).getValue() ^ ((LongValue)o1).getValue())); break;

            default: Error.error("invalid instruction");
        }

    }

    static private void shift(int opCode, OperandStack operandStack) {

        // Your code goes here

        Value o1, o2;
        o1 = operandStack.pop();
        o2 = operandStack.pop();

        // check if the types are not integer or long values --> invalid types for this function, throw error and kill
        if (o1.getType() == Value.s_float || o1.getType() == Value.s_double ||
                o2.getType() == Value.s_float || o2.getType() == Value.s_double) {
            Error.error("type(s) invalid for this function - types must either be an integer or long values");
        }

        switch (opCode){
                // shift left
            case RuntimeConstants.opc_lshl: operandStack.push(new LongValue(((LongValue)o2).getValue() << (63 & ((IntegerValue)o1).getValue()))); break;
            case RuntimeConstants.opc_ishl: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() << (31 & ((IntegerValue)o1).getValue()))); break;

                // shift right
            case RuntimeConstants.opc_ishr: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() >> (31 & ((IntegerValue)o1).getValue()))); break;
            case RuntimeConstants.opc_lshr: operandStack.push(new LongValue(((LongValue)o2).getValue() >> (63 & ((IntegerValue)o1).getValue()))); break;

                // shift right --> signs (+/-) are ignored
            case RuntimeConstants.opc_iushr: operandStack.push(new IntegerValue(((IntegerValue)o2).getValue() >>> (31 & ((IntegerValue)o1).getValue()))); break;
            case RuntimeConstants.opc_lushr: operandStack.push(new LongValue(((LongValue)o2).getValue() >>> (63 & ((IntegerValue)o1).getValue()))); break;

            default: Error.error("invalid opcode"); //default case
        }
    }


    public static void main(String argv[]) {
        OperandStack operandStack = new OperandStack(100, "Phase 2");


        // TEST binOp(...) HERE ////////////////////////////////////////////////////////////////////////
        Value v1, v2;
        IntegerValue v3, v4;

        operandStack.push(new IntegerValue(100)); // used later when testing the integers

        System.out.println("binOp() Test Cases:");  // Prompt and display

        v1 = Value.makeValue((double)5.7);
        v2 = new DoubleValue(6);
        operandStack.push(v1);
        operandStack.push(v2);

        System.out.println(operandStack);

        /* uncomment to use
        // Error checking: Test with different types --> should yield an error msg and kills the program
        operandStack.push(new DoubleValue(5.5));
        operandStack.push(new IntegerValue(3));
        binOp(RuntimeConstants.opc_iadd, Value.s_integer, operandStack);
        System.out.println(operandStack.pop());
        */

        // type: Double
        System.out.println("type -> Double:");
        binOp(RuntimeConstants.opc_dadd, Value.s_double, operandStack);
        System.out.println(operandStack.pop()); // ==> 11.7

        operandStack.push(new DoubleValue(14.00));
        operandStack.push(new DoubleValue(13.75));
        binOp(RuntimeConstants.opc_dsub, Value.s_double, operandStack); // 14.00 - 13.75
        System.out.println(operandStack.pop()); // ==> 0.25

        operandStack.push(new DoubleValue(25.7543));
        operandStack.push(new DoubleValue(5.778));
        binOp(RuntimeConstants.opc_ddiv, Value.s_double, operandStack);
        System.out.println(operandStack.pop());

        operandStack.push(new DoubleValue(6.3));
        operandStack.push(new DoubleValue(7.2));
        binOp(RuntimeConstants.opc_dmul, Value.s_double, operandStack);
        System.out.println(operandStack.pop());

        operandStack.push(new DoubleValue(16.0));
        operandStack.push(new DoubleValue(7.0));
        binOp(RuntimeConstants.opc_drem, Value.s_double, operandStack);
        System.out.println(operandStack.pop());


        // type: Float
        System.out.println("type -> Float:");
        operandStack.push(new FloatValue(12));
        operandStack.push(new FloatValue(12));
        binOp(RuntimeConstants.opc_fadd, Value.s_float, operandStack); // 12 + 12
        System.out.println(operandStack.pop()); // ==> 24.0

        operandStack.push(new FloatValue(13));
        operandStack.push(new FloatValue(14));
        binOp(RuntimeConstants.opc_fsub, Value.s_float, operandStack); // 13 - 14
        System.out.println(operandStack.pop()); // ==> -1.0

        operandStack.push(new FloatValue(25));
        operandStack.push(new FloatValue(-5));
        binOp(RuntimeConstants.opc_fdiv, Value.s_float, operandStack);
        System.out.println(operandStack.pop());

        operandStack.push(new FloatValue(6));
        operandStack.push(new FloatValue(7));
        binOp(RuntimeConstants.opc_fmul, Value.s_float, operandStack);
        System.out.println(operandStack.pop());

        operandStack.push(new FloatValue(20));
        operandStack.push(new FloatValue(8));
        binOp(RuntimeConstants.opc_frem, Value.s_float, operandStack);
        System.out.println(operandStack.pop()); // ==> 4.0


        // type: Long
        System.out.println("type -> Long:");
        operandStack.push(new LongValue(5));
        operandStack.push(new LongValue(-5));
        binOp(RuntimeConstants.opc_ladd, Value.s_long, operandStack); // -5 + 5
        System.out.println(operandStack.pop()); // ==> 0

        operandStack.push(new LongValue(80));
        operandStack.push(new LongValue(40));
        binOp(RuntimeConstants.opc_lsub, Value.s_long, operandStack); // 80 - 40
        System.out.println(operandStack.pop()); // ==> 40

        operandStack.push(new LongValue(72));
        operandStack.push(new LongValue(8));
        binOp(RuntimeConstants.opc_ldiv, Value.s_long, operandStack);
        System.out.println(operandStack.pop());

        operandStack.push(new LongValue(6));
        operandStack.push(new LongValue(7));
        binOp(RuntimeConstants.opc_lmul, Value.s_long, operandStack);
        System.out.println(operandStack.pop());

        operandStack.push(new LongValue(20));
        operandStack.push(new LongValue(8));
        binOp(RuntimeConstants.opc_lrem, Value.s_long, operandStack);
        System.out.println(operandStack.pop()); // ==> 4.0


        // type: Integer
        // using (integer) 100 that was pushed into the stack earlier..
        System.out.println("type -> Integer:");
        v3 = new IntegerValue(50);
        operandStack.push(v3);
        binOp(RuntimeConstants.opc_iadd, Value.s_integer, operandStack);    // 100 + 50
        System.out.println(operandStack.pop()); // ==> 150

        v4 = new IntegerValue(-5);
        operandStack.push(v4);
        operandStack.push(new IntegerValue(2));
        binOp(RuntimeConstants.opc_isub, Value.s_integer, operandStack); // -5 - 2
        System.out.println(operandStack.pop()); // ==> -7

        operandStack.push(new IntegerValue(14));
        operandStack.push(new IntegerValue(2));
        binOp(RuntimeConstants.opc_idiv, Value.s_integer, operandStack);
        System.out.println(operandStack.pop());

        operandStack.push(new IntegerValue(-6));
        operandStack.push(new IntegerValue(-6));
        binOp(RuntimeConstants.opc_imul, Value.s_integer, operandStack);
        System.out.println(operandStack.pop());

        operandStack.push(new IntegerValue(20));
        operandStack.push(new IntegerValue(3));
        binOp(RuntimeConstants.opc_irem, Value.s_integer, operandStack);
        System.out.println(operandStack.pop()); // ==> 2


        // TEST swap(...) HERE  //////////////////////////////////////////////////////////////////////
        System.out.println("\nswap() Test Cases:");

        /* Uncomment to use
        // Error checking: Testing different types - double and long --> should yield error and kills program
        operandStack.push(new DoubleValue(7.5));
        operandStack.push(new LongValue(3));
        swap(operandStack);
         */

        operandStack.push(new IntegerValue(12));
        operandStack.push(new IntegerValue(14));
        System.out.println(operandStack); // print initial operandStack
        swap(operandStack); //swap elements
        System.out.println(operandStack); // print final operandStack after swap
        operandStack.pop(); // pop off the two switched elements for next method
        operandStack.pop();

        operandStack.push(new FloatValue(9));
        operandStack.push(new FloatValue(5));
        System.out.println(operandStack);
        swap(operandStack); //swap elements
        System.out.println(operandStack);
        operandStack.pop();
        operandStack.pop();

        operandStack.push(new FloatValue(8));
        operandStack.push(new IntegerValue(1));
        System.out.println(operandStack);
        swap(operandStack); //swap elements
        System.out.println(operandStack);
        operandStack.pop();
        operandStack.pop();


        // TEST negate(...) HERE //////////////////////////////////////////////////////////////////////
        System.out.println("\nnegate() Test Cases:");

        operandStack.push(new IntegerValue(12));
        negate(Value.s_integer, operandStack); // -12
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new LongValue(-13));
        negate(Value.s_long, operandStack); // 13
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new FloatValue(-10));
        negate(Value.s_float, operandStack); // 10
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new DoubleValue(12.5));
        negate(Value.s_double, operandStack); // -12.5
        System.out.println(operandStack);
        operandStack.pop();



        // TEST cmp(...) HERE /////////////////////////////////////////////////////////////////////
        System.out.println("\ncmp() Test Cases:");  // Prompt and display

        /* Uncomment to use
        //Test to compare IntegerValues --> should throw an error and kills program
        operandStack.push(new IntegerValue(1));
        operandStack.push(new IntegerValue(2));
        cmp(Value.s_integer, operandStack);
        // should not print out
        System.out.println(operandStack.pop());
         */

        operandStack.push(new FloatValue(8));   // 1st obj
        operandStack.push(new FloatValue(7));   // 2nd obj

        cmp(Value.s_float, operandStack);
        System.out.println(operandStack); // top: -1
        operandStack.pop();

        operandStack.push(new DoubleValue(8.0));
        operandStack.push(new DoubleValue(8.0));

        cmp(Value.s_double, operandStack);
        System.out.println(operandStack); // top: 0
        operandStack.pop();

        operandStack.push(new LongValue(8));
        operandStack.push(new LongValue(9));

        cmp(Value.s_long, operandStack);
        System.out.println(operandStack); // top: 1
        operandStack.pop();


        // TEST two(...) HERE  /////////////////////////////////////////////////////////////////////
        System.out.println("\ntwo() Test Cases:");

        /*
        // Error checking: test for type mismatch
        operandStack.push(new IntegerValue(12));
        two(Value.s_double, Value.s_float, operandStack); // throws an error and kills program
         */


        System.out.println("i2b:");
        operandStack.push(new IntegerValue(-128));
        System.out.println(operandStack);
        two(Value.s_integer, Value.s_byte, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\ni2c:");
        operandStack.push(new IntegerValue(12));
        System.out.println(operandStack);
        two(Value.s_integer, Value.s_char, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\ni2s:");
        operandStack.push(new IntegerValue(12));
        System.out.println(operandStack);
        two(Value.s_integer, Value.s_short, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\ni2f:");
        operandStack.push(new IntegerValue(12));
        System.out.println(operandStack);
        two(Value.s_integer, Value.s_float, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\ni2d:");
        operandStack.push(new IntegerValue(12));
        System.out.println(operandStack);
        two(Value.s_integer, Value.s_double, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\ni2l:");
        operandStack.push(new IntegerValue(12));
        System.out.println(operandStack);
        two(Value.s_integer, Value.s_long, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\nf2i:");
        operandStack.push(new FloatValue(-6));
        System.out.println(operandStack);
        two(Value.s_float, Value.s_integer, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\nf2l:");
        operandStack.push(new FloatValue(-6));
        System.out.println(operandStack);
        two(Value.s_float, Value.s_long, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\nf2d:");
        operandStack.push(new FloatValue(-6));
        System.out.println(operandStack);
        two(Value.s_float, Value.s_double, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\nd2i:");
        operandStack.push(new DoubleValue(7.5));
        System.out.println(operandStack);
        two(Value.s_double, Value.s_integer, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\nd2f:");
        operandStack.push(new DoubleValue(7.5));
        System.out.println(operandStack);
        two(Value.s_double, Value.s_float, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\nd2l:");
        operandStack.push(new DoubleValue(7.5));
        System.out.println(operandStack);
        two(Value.s_double, Value.s_long, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\nl2i:");
        operandStack.push(new LongValue(10));
        System.out.println(operandStack);
        two(Value.s_long, Value.s_integer, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\nl2f:");
        operandStack.push(new LongValue(10));
        System.out.println(operandStack);
        two(Value.s_long, Value.s_float, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        System.out.println("\nl2d:");
        operandStack.push(new LongValue(10));
        System.out.println(operandStack);
        two(Value.s_long, Value.s_double, operandStack);
        System.out.println(operandStack);
        operandStack.pop();



        // TEST dup(...) HERE  /////////////////////////////////////////////////////////////////////
        System.out.println("\ndup() Test Cases:");

        /* Uncomment to use
        // Error checking - test if an error yields when duplicating a double using opc_dup_x1
        operandStack.push(new DoubleValue(4.5));
        dup(RuntimeConstants.opc_dup_x1, operandStack);
         */

        operandStack.push(new IntegerValue(3));
        System.out.println(operandStack);
        dup(RuntimeConstants.opc_dup, operandStack);
        System.out.println(operandStack);
        operandStack.pop();
        operandStack.pop();

        operandStack.push(new DoubleValue(3.5));
        System.out.println(operandStack);
        dup(RuntimeConstants.opc_dup2, operandStack);
        System.out.println(operandStack);
        operandStack.pop();
        operandStack.pop();

        operandStack.push(new IntegerValue(10));
        operandStack.push(new FloatValue(5));
        System.out.println(operandStack);
        dup(RuntimeConstants.opc_dup_x1, operandStack);
        System.out.println(operandStack);
        operandStack.pop();
        operandStack.pop();
        operandStack.pop();

        operandStack.push(new IntegerValue(12));
        operandStack.push(new IntegerValue(10));
        operandStack.push(new FloatValue(5));
        System.out.println(operandStack);
        dup(RuntimeConstants.opc_dup_x2, operandStack);
        System.out.println(operandStack);
        operandStack.pop();
        operandStack.pop();
        operandStack.pop();
        operandStack.pop();

        operandStack.push(new IntegerValue(9));
        operandStack.push(new IntegerValue(9));
        operandStack.push(new FloatValue(9));
        System.out.println(operandStack);
        dup(RuntimeConstants.opc_dup2_x1, operandStack);
        System.out.println(operandStack);
        operandStack.pop();
        operandStack.pop();
        operandStack.pop();
        operandStack.pop();
        operandStack.pop();

        operandStack.push(new IntegerValue(6));
        operandStack.push(new LongValue(6));
        operandStack.push(new DoubleValue(6.0));
        System.out.println(operandStack);
        dup(RuntimeConstants.opc_dup2_x2, operandStack);
        System.out.println(operandStack);
        operandStack.pop();
        operandStack.pop();
        operandStack.pop();
        operandStack.pop();


        // TEST logic(...) HERE  /////////////////////////////////////////////////////////////////////
        System.out.println("\nlogic() Test Cases:");

        /* Uncomment to use
        // Error checking: Testing with different types -> double and floats, should lead to an error/program termination
        operandStack.push(new FloatValue(4));
        operandStack.push(new FloatValue(4));
        logic(RuntimeConstants.opc_iand, operandStack);
         */

        operandStack.push(new IntegerValue(12)); // ....1100
        operandStack.push(new IntegerValue(15)); // ....1111
        logic(RuntimeConstants.opc_iand, operandStack); // ....1100 --> 12
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new IntegerValue(12)); // ....1100
        operandStack.push(new IntegerValue(15)); // ....1111
        logic(RuntimeConstants.opc_ior, operandStack); // ....1111 --> 15
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new IntegerValue(12)); // ....1100
        operandStack.push(new IntegerValue(15)); // ....1111
        logic(RuntimeConstants.opc_ixor, operandStack); // ....0011 --> 3
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new LongValue(10)); // ....1010
        operandStack.push(new LongValue(5)); // ....0101
        logic(RuntimeConstants.opc_land, operandStack); // ....0000 --> 0
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new LongValue(10)); // ....1010
        operandStack.push(new LongValue(5)); // ....0101
        logic(RuntimeConstants.opc_lor, operandStack); // ....1111 --> 15
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new LongValue(11)); // ....1011
        operandStack.push(new LongValue(15)); // ....1111
        logic(RuntimeConstants.opc_lxor, operandStack); // ....0100 --> 4
        System.out.println(operandStack);
        operandStack.pop();


        // TEST shift(...) HERE  /////////////////////////////////////////////////////////////////////
        System.out.println("\nshift() Test Cases:");

        /* Uncomment to use
        // Error checking: test for double and float types --> should lead to an error
        operandStack.push(new DoubleValue(7));
        operandStack.push(new FloatValue(3));
        shift(RuntimeConstants.opc_lshr, operandStack);
         */

        operandStack.push(new IntegerValue(12));
        operandStack.push(new IntegerValue(2));
        shift(RuntimeConstants.opc_ishl, operandStack); // 12 * 2^2 = 48
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new IntegerValue(12));
        operandStack.push(new IntegerValue(1));
        shift(RuntimeConstants.opc_ishr, operandStack); // 12 / 2^1 = 6
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new IntegerValue(-48));
        operandStack.push(new IntegerValue(3));
        shift(RuntimeConstants.opc_iushr, operandStack); // results in a big number because sign is ignored
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new LongValue(-10));
        operandStack.push(new IntegerValue(5));
        shift(RuntimeConstants.opc_lshl, operandStack); // -10 * 2^5 = -320
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new LongValue(12));
        operandStack.push(new IntegerValue(2));
        shift(RuntimeConstants.opc_lshr, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

        operandStack.push(new LongValue(11));
        operandStack.push(new IntegerValue(3));
        shift(RuntimeConstants.opc_lushr, operandStack);
        System.out.println(operandStack);
        operandStack.pop();

    }
}
