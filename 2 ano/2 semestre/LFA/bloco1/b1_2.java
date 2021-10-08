import java.util.Stack;
import java.util.Scanner;

public class b1_2 {

    private static Scanner a;
    private static /* synthetic */ boolean b;

    public static void main(final String[] array) {
        final Stack<Double> obj = new Stack<Double>();
        while (b1_2.a.hasNext()) {
            if (b1_2.a.hasNextDouble()) {
                obj.push(b1_2.a.nextDouble());
            } else {
                double d = 0.0;
                final String next;
                final String s;
                if (!(s = (next = b1_2.a.next())).equals("+") && !s.equals("-") && !s.equals("*") && !s.equals("/")) {
                    System.err.println("ERROR: invalid operator!");
                    System.exit(1);
                }
                if (obj.isEmpty()) {
                    System.err.println("ERROR: two operands missing!");
                    System.exit(2);
                }
                final double doubleValue = obj.peek();
                obj.pop();
                if (obj.isEmpty()) {
                    System.err.println("ERROR: one operand missing!");
                    System.exit(3);
                }
                final double doubleValue2 = obj.peek();
                obj.pop();
                if (next.equals("+")) {
                    d = doubleValue2 + doubleValue;
                } else if (next.equals("-")) {
                    d = doubleValue2 - doubleValue;
                } else if (next.equals("*")) {
                    d = doubleValue2 * doubleValue;
                } else if (next.equals("/")) {
                    if (doubleValue == 0.0) {
                        System.err.println("ERROR: divide by zero!");
                        System.exit(4);
                    }
                    d = doubleValue2 / doubleValue;
                }
                obj.push(d);
            }
            if (!b1_2.b && obj.isEmpty()) {
                throw new AssertionError();
            }
            System.out.print("Stack: " + obj);
            System.out.println();
        }
    }

    static {
        b1_2.b = !b1_2.class.desiredAssertionStatus();
        b1_2.a = new Scanner(System.in);
    }
}