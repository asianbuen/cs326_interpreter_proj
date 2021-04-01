import java.util.*;
import Utilities.Error;
import OperandStack.*;
import Value.*;
public class EVM {
  public static void main(String argv[]) {

      // This is a small example of how to use the stack


      OperandStack operandStack = new OperandStack(100, "OperandStack Test");



      operandStack.push(new IntegerValue(100));
      operandStack.push(Value.makeDefaultValueFromSignature("Z"));
      operandStack.push(Value.makeValue("Hello"));

      System.out.println(operandStack.peek(1));
      System.out.println(operandStack.peek(2));
        
      // if we have an expression line 1 + 2 we can translate that into postfix 
      // like this: 1 2 +
      // so it becomes: push(1), push (2), pop() twice and add and push the result.
      operandStack.push(new IntegerValue(1));
      operandStack.push(new IntegerValue(2));
      Value v1, v2;
      v2 = operandStack.pop();
      v1 = operandStack.pop();
      // Note, since the stack holds 'Value' values we have to cast the 
      // values popped to the right kind.
      IntegerValue v3, v4;
      v4 = (IntegerValue)v2;
      v3 = (IntegerValue)v1;


      operandStack.push(new IntegerValue(v3.getValue() + v4.getValue()));
      System.out.println(operandStack.pop()); // ===> 11
      System.out.println(operandStack.pop()); // ===> Hello
      System.out.println(operandStack.pop()); // ===> true
      System.out.println(operandStack.pop()); // ===> 100
      System.out.println(operandStack.pop()); // ===> Error message & program termination
      System.out.println("This line should never be printed out");


      // Remove the code above and replace it by your own tests from the assignment




      // Operand Stack Test 2: Evaluating Postfix Expression: 32 7 54 32 + * 2 /

/*
      operandStack.push(new IntegerValue(32));
      operandStack.push(new IntegerValue(7));
      operandStack.push(new IntegerValue(54));
      operandStack.push(new IntegerValue(32));

      System.out.println(operandStack.toString());

      IntegerValue num1 = (IntegerValue)operandStack.pop();
      IntegerValue num2 = (IntegerValue)operandStack.pop();

      System.out.println(operandStack.toString());

      operandStack.push(new IntegerValue(num1.getValue() + num2.getValue()));   // 54 + 32

      System.out.println(operandStack.toString());

      num1 = (IntegerValue)operandStack.pop();
      num2 = (IntegerValue)operandStack.pop();

      operandStack.push(new IntegerValue(num1.getValue() * num2.getValue()));   // 7 * (54 + 32)

      System.out.println(operandStack.toString());

      operandStack.push(new IntegerValue(2));

      System.out.println(operandStack.toString());

      num2 = (IntegerValue)operandStack.pop();
      num1 = (IntegerValue)operandStack.pop();

      operandStack.push(new IntegerValue(num1.getValue() / num2.getValue()));   // 7 * (54 + 32) / 2

      System.out.println(operandStack.toString());

      num2 = (IntegerValue)operandStack.pop();
      num1 = (IntegerValue)operandStack.pop();

      operandStack.push(new IntegerValue(num1.getValue() - num2.getValue()));   // 32 - 7 * (54 + 32) / 2

      System.out.println(operandStack.toString());


 */

  }

}
