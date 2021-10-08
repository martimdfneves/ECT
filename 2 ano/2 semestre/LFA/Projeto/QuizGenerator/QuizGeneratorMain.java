import java.io.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.stringtemplate.v4.*;

import java.util.*;

public class QuizGeneratorMain {

    static boolean generatedCodeToFile = false;
    static boolean semanticCheckToFile = false;
    static String generatedCodeFileName;

    public static void main(String[] args) throws IOException {
        ArrayList<Object> info = run();

        if (info == null) {
            System.err.println("ERROR! Ups something went wrong!");
            System.exit(1);
        }

        if (args.length == 0) {    // print na consola apenas do código gerado
            printFunction(info, 1);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("-f")) {            // faz print para um ficheiro java (sem prints da análise semântica)
                generatedCodeToFile = true;
                printFunction(info, 1);
            } else if (args[0].equalsIgnoreCase("-s")) {    // faz print na consola da análise semântica e código gerado
                printFunction(info, -1);
            } else {
                System.err.println("ERROR! Flag not supported!\nSupported Flags: [-f] [-s] [-f -s]");
                System.exit(1);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("-f") && args[1].equalsIgnoreCase("-s")) {
                // faz print para um ficheiro java do código gerado
                generatedCodeToFile = true;
                // faz print para um ficheiro '.QG' da análise semântica
                semanticCheckToFile = true;
                printFunction(info, -1);
            } else {
                System.err.println("ERROR! Flags not supported!\nSupported Flags: [-f] [-s] [-f -s]");
                System.exit(1);
            }
        } else {
            System.err.println("ERROR! Too many arguments!\nMax Arguments: 2");
            System.exit(1);
        }
    }

    private static ArrayList<Object> run() {
        ArrayList<Object> info = new ArrayList<>();
        try {
            // create a CharStream that reads from standard input:
            CharStream input = CharStreams.fromStream(System.in);
            // create a lexer that feeds off of input CharStream:
            QuizGeneratorLexer lexer = new QuizGeneratorLexer(input);
            // create a buffer of tokens pulled from the lexer:
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer:
            QuizGeneratorParser parser = new QuizGeneratorParser(tokens);
            // replace error listener:
            //parser.removeErrorListeners(); // remove ConsoleErrorListener
            //parser.addErrorListener(new ErrorHandlingListener());
            // begin parsing at program rule:
            ParseTree tree = parser.program();
            info.add(parser);
            info.add(tree);
            if (parser.getNumberOfSyntaxErrors() == 0) {
                // print LISP-style tree:
                // System.out.println(tree.toStringTree(parser));
                return info;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (RecognitionException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private static void printFunction(ArrayList<Object> info, int num) throws IOException {
        QuizGeneratorParser parser = (QuizGeneratorParser) info.get(0);
        ParseTree tree = (ParseTree) info.get(1);
        semanticCheckQuiz semCheck = new semanticCheckQuiz();

        if (num != -1) {
            semCheck.errorHand.setNotPrintingNum(num);
        }
        if (semanticCheckToFile == true) {
            // imprime msg com o nome do ficheiro '.QG' para onde foram exportados
            System.out.println("Semantic check exported to the file \"semanticCheck.txt\"");
            PrintStream printToFile1 = new PrintStream(new File("semanticCheck.txt"));
            semCheck.errorHand.logFile = printToFile1;
            semCheck.visit(tree);
        } else {
            semCheck.visit(tree);
        }

        if (parser.getNumberOfSyntaxErrors() == 0 && semCheck.semantic_checked) {
            Compiler compiler = new Compiler();
            ST result = compiler.visit(tree);
            result.add("name", compiler.getQuizName());
            if (generatedCodeToFile == true) {
                generatedCodeFileName = compiler.getQuizName();
                // imprime msg com o nome do ficheiro '.java' para onde foi exportado
                System.out.println("Generated code exported to the file \"" + generatedCodeFileName + ".java\"");
                PrintStream printToFile2 = new PrintStream(new File(generatedCodeFileName + ".java"));
                System.setOut(printToFile2);
                System.out.println(result.render());
            } else {
                System.out.println(result.render());
            }
        }
    }
}
