import java.io.IOException;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
import java.util.*;

public class NumbersMain {
	
	static Map<String, Integer> dict = null; 
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws IOException{
		dict = readFile("numbers.txt");
		
		while(sc.hasNextLine()) {
			boolean first = true;
			String line = sc.nextLine().replace("-", " ");
			Scanner sc2 = new Scanner(line);
			while(sc2.hasNext()){
				String word = sc2.next();
				String str = word.toLowerCase();
				if(dict.containsKey(str))
					System.out.print((first? "" : " ") + dict.get(str));
				else
					System.out.print((first? "" : " ") + str);
				first = false;
			}
			System.out.println();
		}
	}
	
	static Map<String, Integer> readFile(String fileName) throws IOException{
		try {
			// create a CharStream that reads from standard input:
			CharStream input = CharStreams.fromFileName(fileName);
			// create a lexer that feeds off of input CharStream:
			NumbersLexer lexer = new NumbersLexer(input);
			// create a buffer of tokens pulled from the lexer:
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			// create a parser that feeds off the tokens buffer:
			NumbersParser parser = new NumbersParser(tokens);
			// replace error listener:
			//parser.removeErrorListeners(); // remove ConsoleErrorListener
			//parser.addErrorListener(new ErrorHandlingListener());
			// begin parsing at program rule:
			ParseTree tree = parser.program();
			if (parser.getNumberOfSyntaxErrors() == 0) {
				// print LISP-style tree:
				// System.out.println(tree.toStringTree(parser));
				Execute visitor0 = new Execute();
				visitor0.visit(tree);
				return visitor0.getMap();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		catch(RecognitionException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
