import org.stringtemplate.v4.*;

import java.util.*;

public class Compiler extends QuizGeneratorBaseVisitor<ST> {

    private STGroup templates = new STGroupFile("StringTemplates.stg");
    private int ansCount = 1;
    private String quizName;
    private HashMap<String, String> varType = new HashMap<>();
    private String[] types = {"Question", "ArrayList<Question>", "DB"};
    private HashMap<String, HashMap<String, String>> themeName = new HashMap<>();
    private boolean visit = true;
    private String type = "";
    private String quizType = "";

    @Override
    public ST visitProgram(QuizGeneratorParser.ProgramContext ctx) {
        ST res = templates.getInstanceOf("module");
        Iterator<QuizGeneratorParser.StatContext> iter = ctx.stat().iterator();
        while (iter.hasNext()) {
            res.add("stat", visit(iter.next()).render());
        }
        this.quizName = ctx.ID().getText();
        return res;
    }

    @Override
    public ST visitInstStat(QuizGeneratorParser.InstStatContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.instructions()));
        return res;
    }

    @Override
    public ST visitForStat(QuizGeneratorParser.ForStatContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.forBlock()));
        return res;
    }

    @Override
    public ST visitIfStat(QuizGeneratorParser.IfStatContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.ifBlock()));
        return res;
    }

    @Override
    public ST visitForBlock(QuizGeneratorParser.ForBlockContext ctx) {
        String type = varType.get(ctx.ID(1).getText());
        if (type.equals("ArrayList<Question>")) {
            type = "Question";
        }
        varType.put(ctx.ID(0).getText(), type);
        ST res = templates.getInstanceOf("forBlock");
        res.add("elemType", type);
        res.add("elem", ctx.ID(0).getText());
        res.add("list", ctx.ID(1).getText());
        Iterator<QuizGeneratorParser.StatContext> iter = ctx.stat().iterator();
        while (iter.hasNext()) {
            res.add("stat", visit(iter.next()));
        }
        return res;
    }

    @Override
    public ST visitEndf(QuizGeneratorParser.EndfContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ST visitIfBlock(QuizGeneratorParser.IfBlockContext ctx) {
        ST res = templates.getInstanceOf("ifBlock");
        res.add("condition", visit(ctx.condition()));
        Iterator<QuizGeneratorParser.StatContext> iter = ctx.stat().iterator();
        while (iter.hasNext()) {
            res.add("stat", visit(iter.next()));
        }
        if (ctx.other() != null) {
            res.add("elseStat", visit(ctx.other()));
        }
        return res;
    }

    @Override
    public ST visitOther(QuizGeneratorParser.OtherContext ctx) {
        ST res = templates.getInstanceOf("stats");
        Iterator<QuizGeneratorParser.StatContext> iter = ctx.stat().iterator();
        while (iter.hasNext()) {
            res.add("stat", visit(iter.next()));
        }
        return res;
    }

    @Override
    public ST visitEndif(QuizGeneratorParser.EndifContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public ST visitCondCorrectAnswer(QuizGeneratorParser.CondCorrectAnswerContext ctx) {
        if (this.quizType.equals("trueOrFalse")) {
            ST resTF = templates.getInstanceOf("correctAnswerTF");
            resTF.add("choice", visit(ctx.mathExpr()));
            resTF.add("question", ctx.ID().getText());
            return resTF;
        }
        ST res = templates.getInstanceOf("condCorrectAnswer");
        res.add("choice", visit(ctx.mathExpr()));
        res.add("question", ctx.ID().getText());
        return res;
    }

    @Override
    public ST visitCondEquals(QuizGeneratorParser.CondEqualsContext ctx) {
        ST res = templates.getInstanceOf("equalsCond");
        res.add("elem1", visit(ctx.mathExpr(0)));
        res.add("elem2", visit(ctx.mathExpr(1)));
        return res;
    }

    @Override
    public ST visitCondBigEq(QuizGeneratorParser.CondBigEqContext ctx) {
        ST res = templates.getInstanceOf("bigEqCond");
        res.add("elem1", visit(ctx.mathExpr(0)));
        res.add("elem2", visit(ctx.mathExpr(1)));
        return res;
    }

    @Override
    public ST visitCondLowEq(QuizGeneratorParser.CondLowEqContext ctx) {
        ST res = templates.getInstanceOf("lowEqCond");
        res.add("elem1", visit(ctx.mathExpr(0)));
        res.add("elem2", visit(ctx.mathExpr(1)));
        return res;
    }

    @Override
    public ST visitCondBig(QuizGeneratorParser.CondBigContext ctx) {
        ST res = templates.getInstanceOf("bigCond");
        res.add("elem1", visit(ctx.mathExpr(0)));
        res.add("elem2", visit(ctx.mathExpr(1)));
        return res;
    }

    @Override
    public ST visitCondLow(QuizGeneratorParser.CondLowContext ctx) {
        ST res = templates.getInstanceOf("lowCond");
        res.add("elem1", visit(ctx.mathExpr(0)));
        res.add("elem2", visit(ctx.mathExpr(1)));
        return res;
    }

    @Override
    public ST visitAssignInst(QuizGeneratorParser.AssignInstContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.assignment()));
        return res;
    }

    @Override
    public ST visitCommandInst(QuizGeneratorParser.CommandInstContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.command()));
        return res;
    }

    @Override
    public ST visitQuestInst(QuizGeneratorParser.QuestInstContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.createQuestion()));
        return res;
    }

    @Override
    public ST visitCreateQuestionphrase(QuizGeneratorParser.CreateQuestionphraseContext ctx) {
        varType.put(ctx.ID().getText(), types[0]);
        ST res = templates.getInstanceOf("questionText");
        res.add("name", ctx.ID().getText());
        res.add("text", ctx.WORD().getText());
        return res;
    }

    @Override
    public ST visitCreateQuestionTheme(QuizGeneratorParser.CreateQuestionThemeContext ctx) {
        HashMap<String, String> tmp = new HashMap<>();
        tmp.put(ctx.WORD().getText(), "");
        themeName.put(ctx.ID().getText(), tmp);
        return null;
    }

    @Override
    public ST visitCreateQuestionDifficulty(QuizGeneratorParser.CreateQuestionDifficultyContext ctx) {
        ST res = templates.getInstanceOf("questionDifficulty");
        res.add("name", ctx.ID().getText());
        res.add("dif", visit(ctx.difficulty()));
        return res;
    }

    @Override
    public ST visitCreateQuestionAnswer(QuizGeneratorParser.CreateQuestionAnswerContext ctx) {
        ST res = templates.getInstanceOf("questionAnswer");
        res.add("name", "ans" + this.ansCount);
        res.add("text", ctx.WORD().getText());
        res.add("points", ctx.NUM().getText());
        res.add("question", ctx.ID().getText());
        this.ansCount++;
        return res;
    }

    @Override
    public ST visitCreateQuestionName(QuizGeneratorParser.CreateQuestionNameContext ctx) {
        if (themeName.containsKey(ctx.ID().getText())) {
            HashMap<String, String> tmp = themeName.get(ctx.ID().getText());
            String[] keys = new String[tmp.size()];
            tmp.keySet().toArray(keys);
            tmp.replace(keys[0], ctx.WORD().getText());
        }
        return null;
    }

    @Override
    public ST visitDeclarAssign(QuizGeneratorParser.DeclarAssignContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.declaration()));
        return res;
    }

    @Override
    public ST visitAttribAssign(QuizGeneratorParser.AttribAssignContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.attribution()));
        return res;
    }

    @Override
    public ST visitQuestDeclarAssign(QuizGeneratorParser.QuestDeclarAssignContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.questionDeclaration()));
        return res;
    }

    @Override
    public ST visitQuestAttribAssign(QuizGeneratorParser.QuestAttribAssignContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.questionAttribution()));
        return res;
    }

    @Override
    public ST visitBdAttribAssign(QuizGeneratorParser.BdAttribAssignContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.bdAttribution()));
        return res;
    }

    @Override
    public ST visitDeclarVar(QuizGeneratorParser.DeclarVarContext ctx) {
        this.visit = true;
        visit(ctx.type());
        String type = this.type;
        varType.put(ctx.ID().getText(), type);
        ST res = templates.getInstanceOf("declarVar");
        res.add("type", visit(ctx.type()));
        res.add("name", ctx.ID().getText());
        this.visit = false;
        this.type = "";
        return res;
    }

    @Override
    public ST visitDeclarArray(QuizGeneratorParser.DeclarArrayContext ctx) {
        this.visit = true;
        visit(ctx.type());
        String type = this.type;
        varType.put(ctx.ID().getText(), type);
        ST res = templates.getInstanceOf("declarArr");
        res.add("type", visit(ctx.type()));
        res.add("name", ctx.ID().getText());
        this.visit = false;
        this.type = "";
        return res;
    }

    @Override
    public ST visitAttribVar(QuizGeneratorParser.AttribVarContext ctx) {
        ST res = templates.getInstanceOf("attribVar");
        if (ctx.type() != null) {
            this.visit = true;
            visit(ctx.type());
            String type = this.type;
            varType.put(ctx.ID().getText(), type);
            res.add("type", visit(ctx.type()));
        }
        res.add("name", ctx.ID().getText());
        res.add("value", visit(ctx.expr()));
        this.visit = false;
        this.type = "";
        return res;
    }

    @Override
    public ST visitAttribArray(QuizGeneratorParser.AttribArrayContext ctx) {
        ST res = templates.getInstanceOf("attribArr");
        if (ctx.type() != null) {
            this.visit = true;
            visit(ctx.type());
            String type = this.type;
            varType.put(ctx.ID().getText(), type);
            res.add("type", visit(ctx.type()));
        }
        res.add("name", ctx.ID().getText());
        if (ctx.expr() != null) {
            Iterator<QuizGeneratorParser.ExprContext> iter = ctx.expr().iterator();
            while (iter.hasNext()) {
                res.add("value", (iter.next()).getText());
            }
        } else {
            res.add("value", visit(ctx.expr(1)));
        }
        this.visit = false;
        this.type = "";
        return res;
    }

    @Override
    public ST visitWordExpr(QuizGeneratorParser.WordExprContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", ctx.WORD().getText());
        return res;
    }

    @Override
    public ST visitMath(QuizGeneratorParser.MathContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.mathExpr()));
        return res;
    }

    @Override
    public ST visitTypeString(QuizGeneratorParser.TypeStringContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", "String");
        if (this.visit == true) {
            this.type = "String";
        }
        return res;
    }

    @Override
    public ST visitTypeInt(QuizGeneratorParser.TypeIntContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", "int");
        if (this.visit == true) {
            this.type = "int";
        }
        return res;
    }

    @Override
    public ST visitTypeDouble(QuizGeneratorParser.TypeDoubleContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", "double");
        if (this.visit == true) {
            this.type = "double";
        }
        return res;
    }

    @Override
    public ST visitBdAttrib(QuizGeneratorParser.BdAttribContext ctx) {
        varType.put(ctx.ID().getText(), types[2]); //não verifico se o nome da variável existe ou não
        ST res = templates.getInstanceOf("newDB");
        res.add("name", ctx.ID().getText());
        res.add("file", ctx.WORD().getText());
        return res;
    }

    @Override
    public ST visitQuestionType(QuizGeneratorParser.QuestionTypeContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", "Question");
        return res;
    }

    @Override
    public ST visitQuestDeclarVar(QuizGeneratorParser.QuestDeclarVarContext ctx) {
        varType.put(ctx.ID().getText(), types[0]);  //não verifico se o nome da variável existe ou não
        ST res = templates.getInstanceOf("newQuestion");
        res.add("name", ctx.ID().getText());
        return res;
    }

    @Override
    public ST visitQuestDeclarArray(QuizGeneratorParser.QuestDeclarArrayContext ctx) {
        varType.put(ctx.ID().getText(), types[1]);    //não verifico se o nome da variável existe ou não
        ST res = templates.getInstanceOf("newQuestionArr");
        res.add("name", ctx.ID().getText());
        return res;
    }

    @Override
    public ST visitQuestAttribVar(QuizGeneratorParser.QuestAttribVarContext ctx) {
        varType.put(ctx.ID(0).getText(), types[0]);        //não verifico se o nome da variável existe ou não
        ST res = templates.getInstanceOf("getOneQuestion");
        res.add("question", ctx.ID(0).getText());
        res.add("data", ctx.ID(1).getText());
        res.add("difficulty", visit(ctx.difficulty()));
        if (ctx.ID(2) != null) {
            res.add("theme", ctx.ID(2).getText());
        } else {
            res.add("theme", ctx.WORD().getText());
        }
        return res;
    }

    @Override
    public ST visitQuestAttribArray(QuizGeneratorParser.QuestAttribArrayContext ctx) {
        varType.put(ctx.ID(0).getText(), types[1]);    //não verifico se o nome da variável existe ou não
        ST res = templates.getInstanceOf("getArrQuestion");
        res.add("list", ctx.ID(0).getText());
        res.add("data", ctx.ID(1).getText());
        res.add("num", Integer.parseInt(ctx.NUM().getText()));
        res.add("difficulty", visit(ctx.difficulty()));
        if (ctx.ID(2) != null) {
            res.add("theme", ctx.ID(2).getText());
        } else {
            res.add("theme", ctx.WORD().getText());
        }
        return res;
    }

    @Override
    public ST visitAnswerModeCommand(QuizGeneratorParser.AnswerModeCommandContext ctx) {
        ST res = templates.getInstanceOf("answerMode");
        res.add("value", visit(ctx.testType()));
        return res;
    }

    @Override
    public ST visitAddCommand(QuizGeneratorParser.AddCommandContext ctx) {
        String ID0 = ctx.ID(0).getText();
        String ID1 = ctx.ID(1).getText();

        //type = DB -> type = Question
        if (varType.get(ID0).equals(types[2]) && varType.get(ID1).equals(types[0])) {
            if (themeName.containsKey(ctx.ID(1).getText())) {
                ST res = templates.getInstanceOf("dbAddQuestion");
                HashMap<String, String> tmp = themeName.get(ctx.ID(1).getText());
                String[] keys = new String[tmp.size()];
                tmp.keySet().toArray(keys);
                String theme = keys[0];
                String name = tmp.get(keys[0]);
                res.add("DB", ctx.ID(0).getText());
                res.add("theme", theme);
                res.add("name", name);
                res.add("question", ctx.ID(1).getText());
                return res;
            }
        }
        //type = ArrayList<Question> -> type = ArrayList<Question>
        else if (varType.get(ID0).equals(types[1]) && varType.get(ID1).equals(types[1])) {
            ST res = templates.getInstanceOf("arrayAddArray");
            res.add("arrayList", ctx.ID(1).getText());
            res.add("list", ctx.ID(0).getText());
            return res;
        }
        ST res = templates.getInstanceOf("addQuestion");
        res.add("list", ctx.ID(0).getText());
        res.add("question", ctx.ID(1).getText());
        return res;
    }

    @Override
    public ST visitRandCommand(QuizGeneratorParser.RandCommandContext ctx) {
        ST res = templates.getInstanceOf("stats");
        res.add("stat", visit(ctx.randMethod()));
        return res;
    }

    @Override
    public ST visitNumAnswersCommand(QuizGeneratorParser.NumAnswersCommandContext ctx) {
        if (this.quizType.equals("trueOrFalse")) {
            System.err.println("ERROR! \"" + ctx.ID().getText() + ".numAnswers(" + ctx.NUM().getText() + ")\" command can't " +
                    "be proccesed in a true or false quiz!");
            System.exit(1);
        }
        ST res = templates.getInstanceOf("numAnswers");
        res.add("question", ctx.ID().getText());
        res.add("num", ctx.NUM().getText());
        return res;
    }

    @Override
    public ST visitPrintCommand(QuizGeneratorParser.PrintCommandContext ctx) {
        ST res = templates.getInstanceOf("print");
        if (ctx.ID() != null) {
            if (varType.get(ctx.ID().getText()).equals(types[0])) {
                if (this.quizType.equals("trueOrFalse")) {
                    ST resTF = templates.getInstanceOf("trueOrFalse");
                    resTF.add("question", ctx.ID().getText());
                    return resTF;
                }
                ST question = templates.getInstanceOf("printQuestion");
                question.add("question", ctx.ID().getText());
                return question;
            } else if (ctx.ID().getText().equals("score")) {
                ST score = templates.getInstanceOf("printScore");
                score.add("score", ctx.ID().getText());
                return score;
            } else {
                res.add("var", ctx.ID().getText());
            }
        } else {
            res.add("var", ctx.WORD().getText());
        }
        return res;
    }

    @Override
    public ST visitUserAnswer(QuizGeneratorParser.UserAnswerContext ctx) {
        ST res = templates.getInstanceOf("userAnswer");
        res.add("id", ctx.ID().getText());
        return res;
    }

    @Override
    public ST visitAddSubExpr(QuizGeneratorParser.AddSubExprContext ctx) {
        ST res = templates.getInstanceOf("operation");
        res.add("elem1", visit(ctx.mathExpr(0)));
        res.add("op", ctx.op.getText());
        res.add("elem2", visit(ctx.mathExpr(1)));
        return res;
    }

    @Override
    public ST visitMultDivExpr(QuizGeneratorParser.MultDivExprContext ctx) {
        ST res = templates.getInstanceOf("operation");
        res.add("elem1", visit(ctx.mathExpr(0)));
        res.add("op", ctx.op.getText());
        res.add("elem2", visit(ctx.mathExpr(1)));
        return res;
    }

    @Override
    public ST visitNumMathExpr(QuizGeneratorParser.NumMathExprContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", ctx.NUM().getText());
        return res;
    }

    @Override
    public ST visitParenthExpr(QuizGeneratorParser.ParenthExprContext ctx) {
        ST res = templates.getInstanceOf("parenth");
        res.add("value", visit(ctx.mathExpr()));
        return res;
    }

    @Override
    public ST visitIdExpr(QuizGeneratorParser.IdExprContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", ctx.ID().getText());
        return res;
    }

    @Override
    public ST visitIdRandMethod(QuizGeneratorParser.IdRandMethodContext ctx) {
        ST res = templates.getInstanceOf("randQuestions");
        res.add("name", ctx.ID().getText());
        return res;
    }

    @Override
    public ST visitAnswersRandMethod(QuizGeneratorParser.AnswersRandMethodContext ctx) {
        if (this.quizType.equals("trueOrFalse")) {
            System.err.println("ERROR! \"rand " + ctx.ID().getText() + ".answers()\" command can't be processed in a true or false quiz!");
            System.exit(1);
        }
        ST res = templates.getInstanceOf("randAnswers");
        res.add("name", ctx.ID().getText());
        return res;
    }

    @Override
    public ST visitMultipleChoiceType(QuizGeneratorParser.MultipleChoiceTypeContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", "\"multipleChoice\"");
        this.quizType = "multipleChoice";
        return res;
    }

    @Override
    public ST visitTrueOrFalseType(QuizGeneratorParser.TrueOrFalseTypeContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", "\"trueOrFalse\"");
        this.quizType = "trueOrFalse";
        return res;
    }

    @Override
    public ST visitEasyDifficulty(QuizGeneratorParser.EasyDifficultyContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", "\"easy\"");
        return res;
    }

    @Override
    public ST visitMediumDifficulty(QuizGeneratorParser.MediumDifficultyContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", "\"medium\"");
        return res;
    }

    @Override
    public ST visitHardDifficulty(QuizGeneratorParser.HardDifficultyContext ctx) {
        ST res = templates.getInstanceOf("atom");
        res.add("value", "\"hard\"");
        return res;
    }

    public String getQuizName() {
        return this.quizName;
    }
}
