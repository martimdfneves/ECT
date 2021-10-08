import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class b1_5 {

    private static Scanner a;
    private static Map b;
    private static /* synthetic */ boolean c;

    public static void main(final String[] array) {
        while (b1_5.a.hasNextLine()) {
            final Scanner scanner = new Scanner(b1_5.a.nextLine());
            System.out.println("result = " + a);
            scanner.close();
        }
    }

    private static double e(final Scanner scanner) {
        if (!b1_5.c && scanner == null) {
            throw new AssertionError();
        }
        double d = 0.0;
        double n = 0.0;
        final String next;
        if (a(next = scanner.next())) {
            if (b1_5.b.containsKey(next)) {
                n = (double) b1_5.b.get(next);
            } else if (!scanner.hasNext()) {
                System.err.printf("ERROR: variable \"" + next + "\" not defined\n", new Object[0]);
                System.exit(1);
            }
        } else {
            final Scanner scanner2;
            final boolean hasNextDouble = (scanner2 = new Scanner(next)).hasNextDouble();
            scanner2.close();
            if (hasNextDouble) {
                n = Double.parseDouble(next);
            } else {
                System.err.printf("ERROR: invalid number \"%s\"\n", next);
                System.exit(1);
            }
        }
        if (scanner.hasNext()) {
            final String next2;
            if ((next2 = scanner.next()).equals("=")) {
                if (!a(next)) {
                    System.err.printf("ERROR: invalid assignment\n", new Object[0]);
                    System.exit(1);
                }

                b1_5.b.put(next, n);
            } else {
                final double a2 = d;
                final String s = next2;
                switch (s) {
                    case "+": {
                        d = n + a2;
                        break;
                    }
                    case "-": {
                        d = n - a2;
                        break;
                    }
                    case "*": {
                        d = n * a2;
                        break;
                    }
                    case "/": {
                        if (a2 == 0.0) {
                            System.err.printf("ERROR: divide by zero\n", new Object[0]);
                            System.exit(1);
                        }
                        d = n / a2;
                        break;
                    }
                    case "=": {
                        break;
                    }
                    default: {
                        System.err.printf("ERROR: invalid operator \"%s\"\n", next2);
                        System.exit(1);
                        break;
                    }
                }
            }
        } else {
            d = n;
        }
        return d;
    }

    private static boolean a(final String s) {
        if (!b1_5.c && s == null) {
            throw new AssertionError();
        }
        boolean letterOrDigit = s.length() > 0 && Character.isLetter(s.charAt(0));
        for (int index = 1; letterOrDigit && index < s.length(); letterOrDigit = Character.isLetterOrDigit(s.charAt(index)), ++index) {
        }
        return letterOrDigit;
    }

    static {
        b1_5.c = !b1_5.class.desiredAssertionStatus();
        b1_5.a = new Scanner(System.in);
        b1_5.b = new HashMap();
    }
}