package aula9_1;

import static java.lang.System.*;

import java.io.*;

public class Test {

    public static void main(String[] args)
            throws IOException {

        ScannerAbeirense sc = new ScannerAbeirense(in);
        out.print("Test-Next: ");
        out.println(sc.next());
        out.print("Test-NextLine: ");
        sc.nextLine();
        out.println(sc.nextLine());
        sc.close();
    }
}