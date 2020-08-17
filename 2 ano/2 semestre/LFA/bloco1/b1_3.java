import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class b1_3 {
    
    private static Scanner a;
    
    public static void main(final String[] array) {
        final Map b = b("numbers.txt");
        while (b1_3.a.hasNextLine()) {
            final Scanner scanner = new Scanner(b1_3.a.nextLine().replace('-', ' '));
            int n = 1;
            while (scanner.hasNext()) {
                String s;
                final String lowerCase = (s = scanner.next()).toLowerCase();
                if (n == 0) {
                    System.out.print(" ");
                }
                if (b.containsKey(lowerCase)) {
                    s = b.get(lowerCase).toString();
                }
                System.out.print(s);
                n = 0;
            }
            System.out.println();
        }
    }
    
    private static Map b(String pathname) {
        final HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        try {
            Scanner pathname1 = new Scanner(new File(pathname));
            while (((Scanner)pathname1).hasNextLine()) {
                final String trim;
                if ((trim = ((Scanner)pathname1).nextLine().trim()).length() > 0) {
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
                        hashMap.put(lowerCase, Integer.parseInt(split[0]));
                    }
                    catch (NumberFormatException ex) {
                        System.err.println("ERROR: invalid number \"" + split[0] + "\" in number file!");
                        System.exit(1);
                    }
                }
            }
            ((Scanner)pathname1).close();
        }
        catch (IOException ex2) {
            System.err.println("ERROR: reading number file!");
            System.exit(2);
        }
        return hashMap;
    }
    
    static {
        b1_3.a = new Scanner(System.in);
    }
}