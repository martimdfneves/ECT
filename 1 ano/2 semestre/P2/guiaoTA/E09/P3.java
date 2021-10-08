import static java.lang.System.*;

public class P3 {

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            System.out.println(prefix(args[i], args[i + 1]));
        }
    }

    static boolean prefix(String str1, String str2) {

        assert (str1 != null);
        assert (str2 != null);

        boolean bool = false;
        if (str2.length() == 0) {
            bool = true;
        } else if ((str1.length() >= str2.length()) && (str1.charAt(0) == str2.charAt(0))) {
            bool = prefix(str1.substring(1), str2.substring(1));
        }
        return bool;
    }

}

