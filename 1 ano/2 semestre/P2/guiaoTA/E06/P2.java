import static java.lang.System.*;

public class P2 {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.print(countpairs(args[i]) + "pares consecutivos");
        }
    }

    static int countpairs(String str) {
        int i = 0;
        if (str.length() >= 2) {
            i = (str.charAt(0) == str.charAt(1) ? 1 : 0) + countpairs(str.substring(1));
        }
        return i;
    }
}

