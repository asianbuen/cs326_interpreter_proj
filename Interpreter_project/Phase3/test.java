public class test {
	public static void main(String args[]) {
		int a, b, c;
		double d;
		a = 10; // bipush and istore operations
		b = 90; // bipush and istore operation
		c = a * b + 8; // iload, imul, bipush, iadd
					   //,→ and istore operations
		a = a + 1; // iload, iconst, iadd and istore
					//,→ operations
		b = b * 2; // iload, iconst, imul and istore
					//,→ operations
		d = b / 6; // iload, bipush, idiv, i2d and
					//,→ dstore operations
		if (b > a) { // iload and if_icmpgt
					//,→ operations
			a = (int)(d - 1.5);
		}
		if (a < b) { // if_icmplt operation
			a = a + 20;
		}
		a = 250; // istore operation
		if (a == 250) { // if_icmpge operation
			a = a >> 2; // ishr operation
			a = a << 2; // ishl operation
		}
		++b; // iinc operation
		a = -a; // ineg operation
	}
}
