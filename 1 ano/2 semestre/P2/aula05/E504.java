import java.util.*;
import java.io.*;

public class E504 {

    public static void main(String[] args) {
        if (args.length > 1) {
            System.err.println("Usage: java -ea p53 <directory-path>");
            System.exit(1);
        }

        File fix;
        if (args.length == 1) {
            fix = new File(args[0]);
        } else {
            fix = new File(".");
        }

        if (!fix.exists()) {
            System.err.println("ERROR: input directory \"" + args[0] + "\" does not exist!");
            System.exit(2);
        }

        if (!fix.isDirectory()) {
            System.err.println("ERROR: input \"" + args[0] + "\" is not a directory!");
            System.exit(3);
        }

        if (!fix.canRead()) {
            System.err.println("ERROR: cannot read from input directory \"" + args[0] + "\"!");
            System.exit(4);
        }

        listDirectory(fix);
    }

    public static void listDirectory(File ficheiro) {
        assert ficheiro != null;

        File[] filesInDir = ficheiro.listFiles();

        for (int i = 0; i < filesInDir.length; i++) {
            String direito = "";
            String dirParent = filesInDir[i].getParent();
            if (dirParent != null && !dirParent.equals("..")) {
                direito = direito + dirParent + "/";
            }

            direito = direito + filesInDir[i].getName();
            System.out.printf("%-19s (", new Object[]{direito});
            if (filesInDir[i].isDirectory()) {
                System.out.print("D");
            } else {
                System.out.print("F");
            }


            if (filesInDir[i].canRead()) {
                System.out.print("R");
            } else {
                System.out.print("-");
            }

            if (filesInDir[i].canWrite()) {
                System.out.print("W");
            } else {
                System.out.print("-");
            }

            System.out.println(")");
        }
    }
}
