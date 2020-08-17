import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class b1_4 {
    private static Scanner a;
    
    public static void main(final String[] array) {
        final Map a = a("numbers.txt");
        while (b1_4.a.hasNextLine()) {
            long n = 0L;
            long n2 = 0L;
            final String nextLine;
            final Scanner scanner = new Scanner((nextLine = b1_4.a.nextLine()).replace('-', ' ').replace(',', ' ').toLowerCase());
            while (scanner.hasNext()) {
                final String next;
                if (!(next = scanner.next()).equals("and")) {
                    if (!a.containsKey(next)) {
                        System.err.println("Number text \"" + next + "\" does not exist in table!");
                        System.exit(1);
                    }
                    final long longValue;
                    if ((longValue = (long)a.get(next)) <= n) {
                        n2 += n;
                        n = 0L;
                    }
                    if (n != 0L && longValue > n) {
                        n *= longValue;
                    }
                    else {
                        n = longValue;
                    }
                }
            }
            System.out.println(nextLine + " -> " + (n2 + n));
        }
    }
    
    private static Map a(String pathname) {
        final HashMap<String, Long> hashMap = new HashMap<String, Long>();
        try {
            Scanner pathname1 = new Scanner(new File(pathname));
            while (pathname1.hasNextLine()) {
                final String trim;
                if ((trim = pathname1.nextLine().trim()).length() > 0) {
                    final String[] split;
                    if ((split = trim.split(" - ")).length != 2) {
                        System.err.println("ERROR: syntax error in number file!");
                        System.exit(1);
                    }
                    final String lowerCase = split[1].toLowerCase();
                    if (hashMap.containsKey(lowerCase)) {
                        System.err.println("ERROR: repeated number \"" + lowerCase + "\" in number file!");
                        System.exit(1);
                    }
                    try {
                        hashMap.put(lowerCase, Long.parseLong(split[0]));
                    }
                    catch (NumberFormatException ex) {
                        System.err.println("ERROR: invalid number \"" + split[0] + "\" in number file!");
                        System.exit(1);
                    }
                }
            }
            pathname1.close();
        }
        catch (IOException ex2) {
            System.err.println("ERROR: reading number file!");
            System.exit(2);
        }
        return hashMap;
    }
    
    static {
        b1_4.a = new Scanner(System.in);
    }
}