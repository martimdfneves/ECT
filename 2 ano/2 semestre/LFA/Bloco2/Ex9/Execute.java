import java.util.*;
import org.antlr.v4.runtime.*;

public class Execute extends QuizBaseVisitor<Object> {
	private static HashMap<String, HashMap<String, Question>> quiz = new HashMap<>();
	
	@Override public Object visitProgram(QuizParser.ProgramContext ctx) {
		return visitChildren(ctx);
	}

	@Override public Object visitQuestion(QuizParser.QuestionContext ctx) {
		ArrayList<Answer> answers = new ArrayList<>();
		Iterator<QuizParser.AnswerContext> iter = ctx.answer().iterator();
		
		while(iter.hasNext()){
			answers.add((Answer)visit(iter.next()));
		}
		
		if(quiz.containsKey(ctx.ID(0).getText())){
			HashMap<String, Question> map = quiz.get(ctx.ID(0).getText());
			map.put(ctx.ID(1).getText(), new Question(ctx.String().getText(), answers));
			quiz.put(ctx.ID(0).getText(), map);
		}
		else{
			HashMap<String, Question> map = new HashMap<>();
			map.put(ctx.ID(1).getText(), new Question(ctx.String().getText(), answers));
			quiz.put(ctx.ID(0).getText(), map);
		}
		return "";
	}

	@Override public Object visitAnswer(QuizParser.AnswerContext ctx) {
		return new Answer(ctx.String().getText(), Integer.parseInt(ctx.Number().getText()));
	}
	
	public static HashMap<String, HashMap<String, Question>> getQuiz(){
		return quiz;
	}
}
