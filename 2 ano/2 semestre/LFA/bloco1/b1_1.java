import java.util.Scanner;

public class b1_1 {
    private static Scanner a;

    public static void main(final String[] array) {
        double d = 0.0;
        System.out.printf("Operation (number op number): ", new Object[0]);
        final double a = a();
        if (!b1_1.a.hasNext()) {
            System.err.printf("ERROR: read operator failure\n", new Object[0]);
            System.exit(1);
        }
        final String next = b1_1.a.next();
        final double a2 = a();
        final String s = next;
        switch (s) {
            case "+": {
                d = a + a2;
                break;
            }
            case "-": {
                d = a - a2;
                break;
            }
            case "*": {
                d = a * a2;
                break;
            }
            case "/": {
                if (a2 == 0.0) {
                    System.err.printf("ERROR: divide by zero\n", new Object[0]);
                    System.exit(1);
                }
                d = a / a2;
                break;
            }
            default: {
                System.err.printf("ERROR: invalid operator \"%s\"\n", next);
                System.exit(1);
                break;
            }
        }
        System.out.printf("%g %s %g = %g\n", a, next, a2, d);
    }

    private static double a() {
        if (!b1_1.a.hasNextDouble()) {
            System.err.printf("ERROR: read number failure\n", new Object[0]);
            System.exit(1);
        }
        return b1_1.a.nextDouble();
    }

    static {
        b1_1.a = new Scanner(System.in);
    }
}