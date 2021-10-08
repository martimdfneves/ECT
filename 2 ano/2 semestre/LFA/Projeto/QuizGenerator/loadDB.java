import java.util.*;

import org.antlr.v4.runtime.*;

public class loadDB extends ReadQuestionsBaseVisitor<Object> {
    private static HashMap<String, HashMap<String, Question>> quiz = new HashMap<>();

    @Override
    public Object visitProgram(ReadQuestionsParser.ProgramContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Object visitQuestion(ReadQuestionsParser.QuestionContext ctx) {
        ArrayList<Answer> answers = new ArrayList<>();
        Iterator<ReadQuestionsParser.AnswerContext> iter = ctx.answer().iterator();
        while (iter.hasNext()) {
            answers.add((Answer) visit(iter.next()));
        }
        String[] str = ctx.QID().getText().split("[.]");

        String theme = str[0];
        String question = str[1];
        String difficulty = str[2];

        if (quiz.containsKey(theme)) {
            HashMap<String, Question> map = quiz.get(theme);
            map.put(question, new Question(ctx.String().getText(), difficulty, answers));
            quiz.put(theme, map);
        } else {
            HashMap<String, Question> map = new HashMap<>();
            map.put(question, new Question(ctx.String().getText(), difficulty, answers));
            quiz.put(theme, map);
        }
        return "";
    }

    @Override
    public Object visitAnswer(ReadQuestionsParser.AnswerContext ctx) {
        return new Answer(ctx.String().getText(), Integer.parseInt(ctx.Number().getText()));
    }

    public static HashMap<String, HashMap<String, Question>> getQuiz() {
        return quiz;
    }
}
