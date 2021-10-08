public class P2 {
    public P2() {
    }

    public static void main(String[] paramArrayOfString) {
        for (int i = 0; i < paramArrayOfString.length; i++) {
            System.out.println(paramArrayOfString[i] + " -> " + invertDigits(paramArrayOfString[i], ""));
        }
    }


    static String invertDigits(String paramString1, String paramString2) {
        assert (paramString1 != null);
        assert (paramString2 != null);

        String str = paramString2;
        if (paramString1.length() > 0) {
            if ((paramString1.charAt(0) >= '0') && (paramString1.charAt(0) <= '9')) {
                str = invertDigits(paramString1.substring(1), "" + paramString1.charAt(0) + paramString2);
                System.out.println(paramString1.substring(1));
            } else
                str = paramString2 + paramString1.charAt(0) + invertDigits(paramString1.substring(1), "");
        }
        return str;
    }
}
