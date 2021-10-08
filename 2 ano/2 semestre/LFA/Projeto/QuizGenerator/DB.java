import java.io.*;
import java.util.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class DB {
    private HashMap<String, HashMap<String, Question>> data = new HashMap<>();

    public DB(String fileName) throws IOException {
        this.data = load(fileName);
    }

    public HashMap<String, HashMap<String, Question>> load(String fileName) throws IOException {
        try {
            // create a CharStream that reads from standard input:
            CharStream input = CharStreams.fromFileName(fileName);
            // create a lexer that feeds off of input CharStream:
            ReadQuestionsLexer lexer = new ReadQuestionsLexer(input);
            // create a buffer of tokens pulled from the lexer:
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            // create a parser that feeds off the tokens buffer:
            ReadQuestionsParser parser = new ReadQuestionsParser(tokens);
            // replace error listener:
            //parser.removeErrorListeners(); // remove ConsoleErrorListener
            //parser.addErrorListener(new ErrorHandlingListener());
            // begin parsing at program rule:
            ParseTree tree = parser.program();
            if (parser.getNumberOfSyntaxErrors() == 0) {
                // print LISP-style tree:
                // System.out.println(tree.toStringTree(parser));
                loadDB visitor0 = new loadDB();
                visitor0.visit(tree);
                return loadDB.getQuiz();
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

    public void add(String theme, String name, Question newQuest) {
        if (this.data.containsKey(theme)) {
            HashMap<String, Question> themeQuestions = this.data.get(theme);
            if (themeQuestions.containsKey(name)) {
                System.err.println("ERROR! Question name \"" + name + "\" already exists in the database!");
                System.exit(1);
            } else {
                themeQuestions.put(name, newQuest);
                this.data.put(theme, themeQuestions);
            }
        } else {
            HashMap<String, Question> themeQuestions = new HashMap<>();
            themeQuestions.put(name, newQuest);
            this.data.put(theme, themeQuestions);
        }
    }

    public Question get(String difficulty, String theme) {
        Question quest = null;
        if (this.data.containsKey(theme)) {
            int count = 0;
            HashMap<String, Question> themeQuestions = this.data.get(theme);
            String[] keys = new String[themeQuestions.size()];
            themeQuestions.keySet().toArray(keys);
            for (String key : keys) {
                if (themeQuestions.get(key).getDifficulty().equals(difficulty)) {
                    if (count < 1) {
                        quest = themeQuestions.get(key);
                        count++;
                    }
                }
            }
            if (count < 1) {
                System.out.println("WARNING! There's not 1 question of the theme \"" + theme + "\" with difficulty " + difficulty);
            }
        } else {
            System.err.println("ERROR! The theme \"" + theme + "\" doesn't exists in the database!");
            System.exit(1);
        }
        return quest;
    }

    public ArrayList<Question> get(int num, String difficulty, String theme) {
        ArrayList<Question> quest = new ArrayList<>();
        if (this.data.containsKey(theme)) {
            int count = 0;
            HashMap<String, Question> themeQuestions = this.data.get(theme);
            String[] keys = new String[themeQuestions.size()];
            themeQuestions.keySet().toArray(keys);
            for (String key : keys) {
                if (themeQuestions.get(key).getDifficulty().equals(difficulty)) {
                    if (count < num) {
                        quest.add(themeQuestions.get(key));
                    }
                    count++;
                }
            }
            if (count < num - 1) {
                System.out.println("WARNING! There's not " + num + " questions of the theme \"" + theme + "\" with difficulty " + difficulty);
            }
        } else {
            System.err.println("ERROR! The theme \"" + theme + "\" doesn't exists in the database!");
            System.exit(1);
        }
        return quest;
    }

    public HashMap<String, HashMap<String, Question>> getData() {
        return this.data;
    }
}
