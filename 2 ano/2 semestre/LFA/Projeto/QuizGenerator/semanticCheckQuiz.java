import java.util.HashMap;
import java.util.*;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

import java.io.*;

public class semanticCheckQuiz extends QuizGeneratorBaseVisitor<Boolean> {

    private HashMap<String, TYPE> tipo_id = new HashMap<String, TYPE>(); //mapa que guarda os tipos de cada variavel
    private HashMap<String, TYPE> tipo_array_id = new HashMap<String, TYPE>();
    private HashMap<String, TYPE> tipo_for = new HashMap<String, TYPE>();
    private HashMap<String, TYPE> tipo_array_for = new HashMap<String, TYPE>();

    private enum TYPE {STRING, INT, QUESTION, DOUBLE, BD}

    private TYPE id_atual;
    private TYPE if_atual;
    private int if_count;
    private String tipo_atual;
    private Boolean is_Array = false;
    private Boolean in_for = false;
    private Boolean in_if = false;
    private boolean numeric_required = false;
    public boolean semantic_checked = true;
    public ErrorHandling errorHand = new ErrorHandling();

    @Override
    public Boolean visitProgram(QuizGeneratorParser.ProgramContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public Boolean visitInstStat(QuizGeneratorParser.InstStatContext ctx) {
        return visit(ctx.instructions());
    }

    @Override
    public Boolean visitForStat(QuizGeneratorParser.ForStatContext ctx) {
        return visit(ctx.forBlock());
    }

    @Override
    public Boolean visitIfStat(QuizGeneratorParser.IfStatContext ctx) {
        return visit(ctx.ifBlock());
    }

    @Override
    public Boolean visitForBlock(QuizGeneratorParser.ForBlockContext ctx) {
        if (in_for) {
            errorHand.printError(ctx, "you cant use a 'for' inside another 'for'");
            semantic_checked = false;
            return false;
        }
        tipo_for = new HashMap<String, TYPE>();
        tipo_array_for = new HashMap<String, TYPE>();
        in_for = true;

        String list = ctx.ID(1).getText();
        String tmp_id = ctx.ID(0).getText();

        if (!tipo_array_id.containsKey(list)) {
            errorHand.printError(ctx, "Variable '" + list + "' doesn't exist or is not iterable!");
            semantic_checked = false;
            return false;
        } else {
            tipo_for.put(tmp_id, tipo_array_id.get(list));
        }
        Boolean check = true;

        for (int i = 0; i < ctx.stat().size(); i++) {
            check = check && visit(ctx.stat(i));
        }
        visit(ctx.endf());
        return check;
    }

    @Override
    public Boolean visitEndf(QuizGeneratorParser.EndfContext ctx) {
        in_for = false;
        tipo_for.clear();
        tipo_array_for.clear();
        return true;
    }

    @Override
    public Boolean visitIfBlock(QuizGeneratorParser.IfBlockContext ctx) {
        Boolean check = true;
        if (in_if) {
            errorHand.printError(ctx, "you cant use 'if' inside another 'if'");
            semantic_checked = false;
            return false;
        } else {
            in_if = true;
            check = visit(ctx.condition());
            for (int i = 0; i < ctx.stat().size(); i++) {
                check = check && visit(ctx.stat(i));
            }

            if (ctx.other() != null) {
                check = check && visit(ctx.other());
            }
            errorHand.printInfo(ctx, "if done");
        }
        return check;
    }

    @Override
    public Boolean visitOther(QuizGeneratorParser.OtherContext ctx) {
        Boolean check = true;

        for (int i = 0; i < ctx.stat().size(); i++) {
            check = check && visit(ctx.stat(i));
        }

        errorHand.printInfo(ctx, "if done");
        return check;
    }

    @Override
    public Boolean visitCondCorrectAnswer(QuizGeneratorParser.CondCorrectAnswerContext ctx) {
        String id = ctx.ID().getText();
        if (tipo_id.containsKey(id)) {
            if (tipo_id.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        }
        errorHand.printInfo(ctx, "Condition Correct Answer done");
        return true;
    }

    @Override
    public Boolean visitCondEquals(QuizGeneratorParser.CondEqualsContext ctx) {
        Boolean check = true;

        for (int i = 0; i < ctx.mathExpr().size(); i++) {
            if_count = i;
            check = check && visit(ctx.mathExpr(i));
        }
        errorHand.printInfo(ctx, "Condition == done");
        return check;
    }

    @Override
    public Boolean visitCondBigEq(QuizGeneratorParser.CondBigEqContext ctx) {
        Boolean check = true;
        for (int i = 0; i < ctx.mathExpr().size(); i++) {
            if_count = i;
            numeric_required = true;
            check = check && visit(ctx.mathExpr(i));
        }
        errorHand.printInfo(ctx, "Condition >= done");
        return check;
    }

    @Override
    public Boolean visitCondLowEq(QuizGeneratorParser.CondLowEqContext ctx) {
        Boolean check = true;

        for (int i = 0; i < ctx.mathExpr().size(); i++) {
            if_count = i;
            numeric_required = true;
            check = check && visit(ctx.mathExpr(i));
        }
        errorHand.printInfo(ctx, "Condition <= done");
        return check;
    }

    @Override
    public Boolean visitCondBig(QuizGeneratorParser.CondBigContext ctx) {
        Boolean check = true;
        for (int i = 0; i < ctx.mathExpr().size(); i++) {
            if_count = i;
            numeric_required = true;
            check = check && visit(ctx.mathExpr(i));
        }
        errorHand.printInfo(ctx, "Condition > done");
        return check;
    }

    @Override
    public Boolean visitCondLow(QuizGeneratorParser.CondLowContext ctx) {
        Boolean check = true;
        for (int i = 0; i < ctx.mathExpr().size(); i++) {
            if_count = i;
            numeric_required = true;
            check = check && visit(ctx.mathExpr(i));
        }
        errorHand.printInfo(ctx, "Condition < done");
        return check;
    }

    @Override
    public Boolean visitEndif(QuizGeneratorParser.EndifContext ctx) {
        in_if = false;
        if_atual = null;
        return true;
    }


    @Override
    public Boolean visitCreateQuestionphrase(QuizGeneratorParser.CreateQuestionphraseContext ctx) {
        String id = ctx.ID().getText();
        if (tipo_array_id.containsKey(id) || tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "cant use an array of questions for this");
            semantic_checked = false;
            return false;
        }
        if (tipo_id.containsKey(id)) {
            if (tipo_id.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else if (tipo_for.containsKey(id)) {
            if (tipo_for.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else {
            tipo_id.put(id, TYPE.QUESTION);
            errorHand.printInfo(ctx, "done");
        }
        errorHand.printInfo(ctx, "done");
        return true;
    }

    @Override
    public Boolean visitCreateQuestionTheme(QuizGeneratorParser.CreateQuestionThemeContext ctx) {
        String id = ctx.ID().getText();
        if (tipo_array_id.containsKey(id) || tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "cant use an array of questions for this");
            semantic_checked = false;
            return false;
        }
        if (tipo_id.containsKey(id)) {
            if (tipo_id.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else if (tipo_for.containsKey(id)) {
            if (tipo_for.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else {
            tipo_id.put(id, TYPE.QUESTION);
            errorHand.printInfo(ctx, "done");
        }
        errorHand.printInfo(ctx, "done");
        return true;
    }

    @Override
    public Boolean visitCreateQuestionDifficulty(QuizGeneratorParser.CreateQuestionDifficultyContext ctx) {
        String id = ctx.ID().getText();
        if (tipo_array_id.containsKey(id) || tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "cant use an array of questions for this");
            semantic_checked = false;
            return false;
        }
        if (tipo_id.containsKey(id)) {
            if (tipo_id.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else if (tipo_for.containsKey(id)) {
            if (tipo_for.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else {
            tipo_id.put(id, TYPE.QUESTION);
            errorHand.printInfo(ctx, "done");
        }
        errorHand.printInfo(ctx, "done");
        return true;
    }

    @Override
    public Boolean visitCreateQuestionAnswer(QuizGeneratorParser.CreateQuestionAnswerContext ctx) {
        String id = ctx.ID().getText();
        if (tipo_array_id.containsKey(id) || tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "cant use an array of questions for this");
            return false;
        }
        if (tipo_id.containsKey(id)) {
            if (tipo_id.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else if (tipo_for.containsKey(id)) {
            if (tipo_for.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else {
            tipo_id.put(id, TYPE.QUESTION);
            errorHand.printInfo(ctx, "done");
        }
        errorHand.printInfo(ctx, "done");
        return true;
    }

    @Override
    public Boolean visitCreateQuestionName(QuizGeneratorParser.CreateQuestionNameContext ctx) {
        String id = ctx.ID().getText();
        if (tipo_array_id.containsKey(id) || tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "cant use an array of questions for this");
            semantic_checked = false;
            return false;
        }
        if (tipo_id.containsKey(id)) {
            if (tipo_id.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else if (tipo_for.containsKey(id)) {
            if (tipo_for.get(id) != TYPE.QUESTION) {
                errorHand.printError(ctx, "Variable '" + id + "' it is not a QUESTION!");
                semantic_checked = false;
                return false;
            }
        } else {
            tipo_id.put(id, TYPE.QUESTION);
            errorHand.printInfo(ctx, "done");
        }
        errorHand.printInfo(ctx, "done");
        return true;
    }

    @Override
    public Boolean visitAssignInst(QuizGeneratorParser.AssignInstContext ctx) {
        return visit(ctx.assignment());
    }

    @Override
    public Boolean visitCommandInst(QuizGeneratorParser.CommandInstContext ctx) {
        return visit(ctx.command());
    }

    @Override
    public Boolean visitQuestInst(QuizGeneratorParser.QuestInstContext ctx) {
        return visit(ctx.createQuestion());
    }

    @Override
    public Boolean visitDeclarAssign(QuizGeneratorParser.DeclarAssignContext ctx) {
        return visit(ctx.declaration());
    }

    @Override
    public Boolean visitAttribAssign(QuizGeneratorParser.AttribAssignContext ctx) {
        return visit(ctx.attribution());
    }

    @Override
    public Boolean visitQuestDeclarAssign(QuizGeneratorParser.QuestDeclarAssignContext ctx) {
        return visit(ctx.questionDeclaration());
    }

    @Override
    public Boolean visitQuestAttribAssign(QuizGeneratorParser.QuestAttribAssignContext ctx) {
        return visit(ctx.questionAttribution());
    }

    @Override
    public Boolean visitBdAttribAssign(QuizGeneratorParser.BdAttribAssignContext ctx) {
        return visit(ctx.bdAttribution());
    }

    @Override
    public Boolean visitDeclarVar(QuizGeneratorParser.DeclarVarContext ctx) {
        String id = ctx.ID().getText();
        visit(ctx.type());
        if (tipo_id.containsKey(id) || tipo_array_id.containsKey(id) || tipo_for.containsKey(id) || tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "Variable '" + id + "' Already exists!");
            semantic_checked = false;
            return false;
        }
        if (in_for) {
            if (tipo_atual.equals("STRING")) {
                tipo_for.put(id, TYPE.STRING);
                id_atual = TYPE.STRING;
            } else if (tipo_atual.equals("INT")) {
                tipo_for.put(id, TYPE.INT);
                id_atual = TYPE.INT;
            } else if (tipo_atual.equals("DOUBLE")) {
                tipo_for.put(id, TYPE.DOUBLE);
                id_atual = TYPE.DOUBLE;
            }
        } else {
            if (tipo_atual.equals("STRING")) {
                tipo_id.put(id, TYPE.STRING);
                id_atual = TYPE.STRING;
            } else if (tipo_atual.equals("INT")) {
                tipo_id.put(id, TYPE.INT);
                id_atual = TYPE.INT;
            } else if (tipo_atual.equals("DOUBLE")) {
                tipo_id.put(id, TYPE.DOUBLE);
                id_atual = TYPE.DOUBLE;
            }
        }
        return true;
    }

    @Override
    public Boolean visitDeclarArray(QuizGeneratorParser.DeclarArrayContext ctx) {
        String id = ctx.ID().getText();
        visit(ctx.type());
        if (tipo_id.containsKey(id) || tipo_array_id.containsKey(id) || tipo_for.containsKey(id) || tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "Variable '" + id + "' Already exists!");
            semantic_checked = false;
            return false;
        }
        if (in_for) {
            if (tipo_atual.equals("STRING")) {
                tipo_array_for.put(id, TYPE.STRING);
                id_atual = TYPE.STRING;
            } else if (tipo_atual.equals("INT")) {
                tipo_array_for.put(id, TYPE.INT);
                id_atual = TYPE.INT;
            } else if (tipo_atual.equals("DOUBLE")) {
                tipo_array_for.put(id, TYPE.DOUBLE);
                id_atual = TYPE.DOUBLE;
            }
        } else {
            if (tipo_atual.equals("STRING")) {
                tipo_array_id.put(id, TYPE.STRING);
                id_atual = TYPE.STRING;
            } else if (tipo_atual.equals("INT")) {
                tipo_array_id.put(id, TYPE.INT);
                id_atual = TYPE.INT;
            } else if (tipo_atual.equals("DOUBLE")) {
                tipo_array_id.put(id, TYPE.DOUBLE);
                id_atual = TYPE.DOUBLE;
            }
        }
        return true;
    }

    @Override
    public Boolean visitAttribVar(QuizGeneratorParser.AttribVarContext ctx) {
        boolean check;
        String id = ctx.ID().getText();
        if (ctx.type() != null) {
            visit(ctx.type());
            if (tipo_id.containsKey(id) || tipo_array_id.containsKey(id) || tipo_for.containsKey(id)) {
                errorHand.printError(ctx, "Variable '" + id + "' Already exists!");
                semantic_checked = false;
                return false;
            } else {
                if (in_for) {
                    if (tipo_atual.equals("STRING")) {
                        tipo_for.put(id, TYPE.STRING);
                        id_atual = TYPE.STRING;
                    } else if (tipo_atual.equals("INT")) {
                        tipo_for.put(id, TYPE.INT);
                        id_atual = TYPE.INT;
                    } else if (tipo_atual.equals("DOUBLE")) {
                        tipo_for.put(id, TYPE.DOUBLE);
                        id_atual = TYPE.DOUBLE;
                    }
                } else {
                    if (tipo_atual.equals("STRING")) {
                        tipo_id.put(id, TYPE.STRING);
                        id_atual = TYPE.STRING;
                    } else if (tipo_atual.equals("INT")) {
                        tipo_id.put(id, TYPE.INT);
                        id_atual = TYPE.INT;
                    } else if (tipo_atual.equals("DOUBLE")) {
                        tipo_id.put(id, TYPE.DOUBLE);
                        id_atual = TYPE.DOUBLE;
                    }
                }
            }
        } else {
            if (tipo_id.containsKey(id)) {
                id_atual = tipo_id.get(id);
            } else if (tipo_for.containsKey(id)) {
                id_atual = tipo_for.get(id);
            } else {
                errorHand.printError(ctx, "Variable '" + id + "' Doesn't exists!");
                semantic_checked = false;
                return false;
            }
        }
        is_Array = false;
        check = visit(ctx.expr());
        return check;
    }

    @Override
    public Boolean visitAttribArray(QuizGeneratorParser.AttribArrayContext ctx) {
        boolean check = true;
        String id = ctx.ID().getText();
        if (ctx.type() != null) {
            visit(ctx.type());
            if (tipo_id.containsKey(id) || tipo_array_id.containsKey(id)) {
                errorHand.printError(ctx, "Variable '" + id + "' Already exists!");
                semantic_checked = false;
                return false;
            } else {
                if (in_for) {
                    if (tipo_atual.equals("STRING")) {
                        tipo_array_for.put(id, TYPE.STRING);
                        id_atual = TYPE.STRING;
                    } else if (tipo_atual.equals("INT")) {
                        tipo_array_for.put(id, TYPE.INT);
                        id_atual = TYPE.INT;
                    } else if (tipo_atual.equals("DOUBLE")) {
                        tipo_array_for.put(id, TYPE.DOUBLE);
                        id_atual = TYPE.DOUBLE;
                    }
                } else {
                    if (tipo_atual.equals("STRING")) {
                        tipo_array_id.put(id, TYPE.STRING);
                        id_atual = TYPE.STRING;
                    } else if (tipo_atual.equals("INT")) {
                        tipo_array_id.put(id, TYPE.INT);
                        id_atual = TYPE.INT;
                    } else if (tipo_atual.equals("DOUBLE")) {
                        tipo_array_id.put(id, TYPE.DOUBLE);
                        id_atual = TYPE.DOUBLE;
                    }
                }
            }
        } else {
            if (tipo_array_id.containsKey(id)) {
                id_atual = tipo_id.get(id);
            } else if (tipo_array_for.containsKey(id)) {
                id_atual = tipo_for.get(id);
            } else {
                errorHand.printError(ctx, "Variable '" + id + "' Doesn't exists!");
                semantic_checked = false;
                return false;
            }
        }
        is_Array = true;
        for (int i = 0; i < ctx.expr().size(); i++) {
            check = check && visit(ctx.expr(i));
        }
        return check;
    }

    @Override
    public Boolean visitWordExpr(QuizGeneratorParser.WordExprContext ctx) {
        if (is_Array) {
            if (id_atual != TYPE.STRING) {
                errorHand.printError(ctx, "Variable is a '" + id_atual + "' not a String");
                semantic_checked = false;
                return false;
            } else {
                errorHand.printInfo("done");
                return true;
            }
        } else {
            if (id_atual != TYPE.STRING) {
                errorHand.printError(ctx, "Variable is a '" + id_atual + "' not a String");
                semantic_checked = false;
                return false;
            } else {
                errorHand.printInfo("done");
                return true;
            }
        }
    }

    @Override
    public Boolean visitMath(QuizGeneratorParser.MathContext ctx) {
        return visit(ctx.mathExpr());
    }

    @Override
    public Boolean visitTypeDouble(QuizGeneratorParser.TypeDoubleContext ctx) {
        tipo_atual = "DOUBLE";
        return true;
    }

    @Override
    public Boolean visitTypeString(QuizGeneratorParser.TypeStringContext ctx) {
        tipo_atual = "STRING";
        return true;
    }

    @Override
    public Boolean visitTypeInt(QuizGeneratorParser.TypeIntContext ctx) {
        tipo_atual = "INT";
        return true;
    }

    @Override
    public Boolean visitBdAttrib(QuizGeneratorParser.BdAttribContext ctx) {
        String id = ctx.ID().getText();
        if (tipo_id.containsKey(id) || tipo_array_id.containsKey(id)) {
            errorHand.printError(ctx, "Variable '" + id + "' Already exists!");
            semantic_checked = false;
            return false;
        } else {
            tipo_id.put(id, TYPE.BD);
            errorHand.printInfo("done");
            return true;
        }
    }

    @Override
    public Boolean visitQuestionType(QuizGeneratorParser.QuestionTypeContext ctx) {
        return true;
    }

    @Override
    public Boolean visitQuestDeclarVar(QuizGeneratorParser.QuestDeclarVarContext ctx) {
        String id = ctx.ID().getText();
        if (tipo_id.containsKey(id) || tipo_array_id.containsKey(id) || tipo_for.containsKey(id) || tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "Variable '" + id + "' Already exists!");
            semantic_checked = false;
            return false;

        } else {
            if (in_for) {
                tipo_for.put(id, TYPE.QUESTION);
                errorHand.printInfo("done");
                return true;
            } else {
                tipo_id.put(id, TYPE.QUESTION);
                errorHand.printInfo("done");
                return true;
            }
        }
    }

    @Override
    public Boolean visitQuestDeclarArray(QuizGeneratorParser.QuestDeclarArrayContext ctx) {
        String id = ctx.ID().getText();
        if (tipo_id.containsKey(id) || tipo_array_id.containsKey(id) || tipo_for.containsKey(id) || tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "Variable '" + id + "' Already exists!");
            semantic_checked = false;
            return false;
        } else {
            if (in_for) {
                tipo_array_for.put(id, TYPE.QUESTION);
                errorHand.printInfo("done");
                return true;
            } else {
                tipo_array_id.put(id, TYPE.QUESTION);
                errorHand.printInfo("done");
                return true;
            }
        }
    }

    @Override
    public Boolean visitQuestAttribVar(QuizGeneratorParser.QuestAttribVarContext ctx) {
        String quest_id = ctx.ID(0).getText();
        String bd_id = ctx.ID(1).getText();
        String id_theme = "";
        if (ctx.ID(2) != null) {
            id_theme = ctx.ID(2).getText();
        }
        if (ctx.questionType() != null) {
            if (tipo_id.containsKey(quest_id) || tipo_array_id.containsKey(quest_id) || tipo_for.containsKey(quest_id) || tipo_array_for.containsKey(quest_id)) {
                errorHand.printError(ctx, "Variable '" + quest_id + "' Already exists!");
                semantic_checked = false;
                return false;
            } else {
                if (in_for) {
                    tipo_for.put(quest_id, TYPE.QUESTION);
                } else {
                    tipo_id.put(quest_id, TYPE.QUESTION);
                }
            }
        } else {
            if (!tipo_id.containsKey(quest_id) && !tipo_for.containsKey(quest_id)) {
                errorHand.printError(ctx, "Variable '" + quest_id + "' doesn't exists!");
                semantic_checked = false;
                return false;
            }
            if (tipo_id.get(quest_id) != TYPE.QUESTION && tipo_id.get(quest_id) != null) {
                errorHand.printError(ctx, "Variable '" + quest_id + "' is not a Question!");
                semantic_checked = false;
                return false;
            }
            if (tipo_for.get(quest_id) != TYPE.QUESTION && tipo_id.get(quest_id) != null) {
                errorHand.printError(ctx, "Variable '" + quest_id + "' is not a Question!");
                semantic_checked = false;
                return false;
            }
        }
        if (!tipo_id.containsKey(bd_id) && !tipo_for.containsKey(bd_id)) {
            errorHand.printError(ctx, "BD '" + bd_id + "' doesnt exist");
            semantic_checked = false;
            return false;
        } else {
            if (tipo_id.get(bd_id) != TYPE.BD && tipo_id.get(bd_id) != null) {
                errorHand.printError(ctx, "Variable '" + bd_id + "' is not a BD");
                semantic_checked = false;
                return false;
            }
            if (tipo_for.get(bd_id) != TYPE.BD && tipo_for.get(bd_id) != null) {
                errorHand.printError(ctx, "Variable '" + bd_id + "' is not a BD");
                semantic_checked = false;
                return false;
            }
        }
        if (!id_theme.equals("")) {
            if (!tipo_id.containsKey(id_theme) && !tipo_for.containsKey(id_theme)) {
                errorHand.printError(ctx, "Variable '" + id_theme + "' doesnt exist");
                semantic_checked = false;
                return false;
            } else {
                if (tipo_id.get(id_theme) != TYPE.STRING && tipo_id.get(id_theme) != null) {
                    errorHand.printError(ctx, "Variable '" + id_theme + "' is not a String");
                    semantic_checked = false;
                    return false;
                }
                if (tipo_for.get(id_theme) != TYPE.STRING && tipo_for.get(id_theme) != null) {
                    errorHand.printError(ctx, "Variable '" + id_theme + "' is not a String");
                    semantic_checked = false;
                    return false;
                }
            }
        }
        errorHand.printInfo("done");
        return true;
    }

    @Override
    public Boolean visitQuestAttribArray(QuizGeneratorParser.QuestAttribArrayContext ctx) {
        String quest_id = ctx.ID(0).getText();
        String bd_id = ctx.ID(1).getText();
        String id_theme = "";
        if (ctx.ID(2) != null) {
            id_theme = ctx.ID(2).getText();
        }
        if (ctx.questionType() != null) {
            if (tipo_id.containsKey(quest_id) || tipo_array_id.containsKey(quest_id) || tipo_for.containsKey(quest_id) || tipo_array_for.containsKey(quest_id)) {
                errorHand.printError(ctx, "Variable '" + quest_id + "' Already exists!");
                semantic_checked = false;
                return false;
            } else {
                if (in_for) {
                    tipo_array_for.put(quest_id, TYPE.QUESTION);
                } else {
                    tipo_array_id.put(quest_id, TYPE.QUESTION);
                }
            }
        } else {
            if (!tipo_id.containsKey(quest_id) && !tipo_for.containsKey(quest_id)) {
                errorHand.printError(ctx, "Variable '" + quest_id + "' doesn't exists!");
                semantic_checked = false;
                return false;
            }
        }
        if (!tipo_id.containsKey(bd_id) && !tipo_for.containsKey(bd_id)) {  //permitir apenas get de bd
            errorHand.printError(ctx, "BD '" + bd_id + "' doesnt exist");
            semantic_checked = false;
            return false;
        } else {
            if (tipo_id.get(bd_id) != TYPE.BD && tipo_id.get(bd_id) != null) {
                errorHand.printError(ctx, "Variable '" + bd_id + "' is not a BD");
                semantic_checked = false;
                return false;
            }
            if (tipo_for.get(bd_id) != TYPE.BD && tipo_for.get(bd_id) != null) {
                errorHand.printError(ctx, "Variable '" + bd_id + "' is not a BD");
                semantic_checked = false;
                return false;
            }
        }
        if (!id_theme.equals("")) {
            if (!tipo_id.containsKey(id_theme) && !tipo_for.containsKey(id_theme)) {//permitir apenas Strings
                errorHand.printError(ctx, "Variable '" + id_theme + "' doesnt exist");
                semantic_checked = false;
                return false;
            } else {
                if (tipo_id.get(id_theme) != TYPE.STRING && tipo_id.get(id_theme) != null) {
                    errorHand.printError(ctx, "Variable '" + id_theme + "' is not a String");
                    semantic_checked = false;
                    return false;
                }
                if (tipo_for.get(id_theme) != TYPE.STRING && tipo_for.get(id_theme) != null) {
                    errorHand.printError(ctx, "Variable '" + id_theme + "' is not a String");
                    semantic_checked = false;
                    return false;
                }
            }
        }
        errorHand.printInfo("done");
        return true;
    }

    @Override
    public Boolean visitAnswerModeCommand(QuizGeneratorParser.AnswerModeCommandContext ctx) {
        return visit(ctx.testType());
    }

    @Override
    public Boolean visitAddCommand(QuizGeneratorParser.AddCommandContext ctx) {
        String id = ctx.ID(0).getText();
        String id_quest = ctx.ID(1).getText();
        if (!tipo_id.containsKey(id_quest) && !tipo_for.containsKey(id_quest) && !tipo_array_id.containsKey(id_quest) && !tipo_array_for.containsKey(id_quest)) {
            errorHand.printError(ctx, "Variable '" + id_quest + "' doesnt exist");
            semantic_checked = false;
            return false;
        } else {
            if (tipo_id.get(id_quest) != TYPE.QUESTION && tipo_id.get(id_quest) != null) {
                errorHand.printError(ctx, "Variable '" + id_quest + "' is not a Question");
                semantic_checked = false;
                return false;
            }
            if (tipo_for.get(id_quest) != TYPE.QUESTION && tipo_for.get(id_quest) != null) {
                errorHand.printError(ctx, "Variable '" + id_quest + "' is not a Question");
                semantic_checked = false;
                return false;
            }
            if (tipo_array_id.get(id) != TYPE.QUESTION && tipo_array_id.get(id) != null) {
                errorHand.printError(ctx, "Variable '" + id + "' is not a Question");
                semantic_checked = false;
                return false;
            }
            if (tipo_array_for.get(id) != TYPE.QUESTION && tipo_array_for.get(id) != null) {
                errorHand.printError(ctx, "Variable '" + id + "' is not a Question");
                semantic_checked = false;
                return false;
            }
        }
        if (!tipo_array_id.containsKey(id) && !tipo_array_for.containsKey(id) && !tipo_id.containsKey(id) && !tipo_for.containsKey(id)) {
            errorHand.printError(ctx, "Variable '" + id + "' doesnt exist");
            semantic_checked = false;
            return false;
        } else {
            if (tipo_id.get(id) != TYPE.BD && tipo_id.get(id) != null) {
                errorHand.printError(ctx, "Variable '" + id + "' is not a BD");
                semantic_checked = false;
                return false;
            }
            if (tipo_for.get(id) != TYPE.BD && tipo_for.get(id) != null) {
                errorHand.printError(ctx, "Variable '" + id + "' is not a BD");
                semantic_checked = false;
                return false;
            }
            if (tipo_array_id.get(id) != TYPE.QUESTION && tipo_array_id.get(id) != null) {
                errorHand.printError(ctx, "Variable '" + id + "' is not a Question");
                semantic_checked = false;
                return false;
            }
            if (tipo_array_for.get(id) != TYPE.QUESTION && tipo_array_for.get(id) != null) {
                errorHand.printError(ctx, "Variable '" + id + "' is not a Question");
                semantic_checked = false;
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean visitRandCommand(QuizGeneratorParser.RandCommandContext ctx) {
        return visit(ctx.randMethod());
    }

    @Override
    public Boolean visitNumAnswersCommand(QuizGeneratorParser.NumAnswersCommandContext ctx) {
        String id = ctx.ID().getText();
        if (!tipo_id.containsKey(id) && !tipo_for.containsKey(id)) {
            errorHand.printError(ctx, "Variable '" + id + "' doesnt exist");
            semantic_checked = false;
            return false;
        } else {
            if (tipo_id.get(id) != TYPE.QUESTION && tipo_id.get(id) != null) {
                errorHand.printError(ctx, "Variable '" + id + "' is not a Question");
                semantic_checked = false;
                return false;
            }
            if (tipo_for.get(id) != TYPE.QUESTION && tipo_for.get(id) != null) {
                errorHand.printError(ctx, "Variable '" + id + "' is not a Question");
                semantic_checked = false;
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean visitPrintCommand(QuizGeneratorParser.PrintCommandContext ctx) {
        String id = "";
        if (ctx.ID() != null) {
            id = ctx.ID().getText();
        }
        if (!id.equals("")) {
            if (!tipo_array_id.containsKey(id) && !tipo_array_for.containsKey(id) && !tipo_id.containsKey(id) && !tipo_for.containsKey(id)) {
                errorHand.printError(ctx, "Variable '" + id + "' doesnt exist");
                semantic_checked = false;
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean visitUserAnswer(QuizGeneratorParser.UserAnswerContext ctx) {
        return true;
    }

    @Override
    public Boolean visitAddSubExpr(QuizGeneratorParser.AddSubExprContext ctx) {
        boolean check = true;
        check = visit(ctx.mathExpr(0));
        check = check && visit(ctx.mathExpr(1));
        return check;
    }

    @Override
    public Boolean visitMultDivExpr(QuizGeneratorParser.MultDivExprContext ctx) {
        boolean check = true;
        check = visit(ctx.mathExpr(0));
        check = check && visit(ctx.mathExpr(1));
        return check;
    }

    @Override
    public Boolean visitNumMathExpr(QuizGeneratorParser.NumMathExprContext ctx) {
        if (in_if) {
            if (if_atual == null) {
                if (ctx.NUM().getText().contains(".")) {
                    if_atual = TYPE.DOUBLE;
                } else {
                    if_atual = TYPE.INT;
                }
            }
            if (if_atual != TYPE.INT && if_atual != TYPE.DOUBLE) {
                errorHand.printError(ctx, "Variable is a '" + if_atual + "' not a INT or a DOUBLE");
                semantic_checked = false;
                return false;
            } else {
                errorHand.printInfo("done");
                return true;
            }
        } else if (is_Array) {
            if (id_atual != TYPE.INT && id_atual != TYPE.DOUBLE) {
                errorHand.printError(ctx, "Variable is a '" + id_atual + "' not a INT or a DOUBLE");
                semantic_checked = false;
                return false;
            } else {
                errorHand.printInfo("done");
                return true;
            }
        } else {
            if (id_atual != TYPE.INT && id_atual != TYPE.DOUBLE) {
                errorHand.printError(ctx, "Variable is a '" + id_atual + "' not a INT or a DOUBLE");
                semantic_checked = false;
                return false;
            } else {
                errorHand.printInfo("done");
                return true;
            }
        }
    }

    @Override
    public Boolean visitParenthExpr(QuizGeneratorParser.ParenthExprContext ctx) {
        return visit(ctx.mathExpr());
    }

    @Override
    public Boolean visitIdExpr(QuizGeneratorParser.IdExprContext ctx) {
        String id = ctx.ID().getText();
        if (in_if) {
            if (if_count == 0) {
                if (tipo_id.containsKey(id)) {
                    if_atual = tipo_id.get(id);
                    if (numeric_required) {
                        if (if_atual != TYPE.INT && if_atual != TYPE.DOUBLE) {
                            errorHand.printError(ctx, "Variable '" + id + "' must be INT or DOUBLE ");
                            semantic_checked = false;
                            return false;
                        }
                    }
                } else if (tipo_for.containsKey(id)) {
                    if_atual = tipo_for.get(id);
                    if (numeric_required) {
                        if (if_atual != TYPE.INT && if_atual != TYPE.DOUBLE) {
                            errorHand.printError(ctx, "Variable '" + id + "' must be INT or DOUBLE ");
                            semantic_checked = false;
                            return false;
                        }
                    }
                } else {
                    errorHand.printError(ctx, "Variable '" + id + "' doesnt exist or it is an array ");
                    semantic_checked = false;
                    return false;
                }
            } else {
                if (tipo_id.containsKey(id)) {
                    if (if_atual == TYPE.STRING && tipo_id.get(id) != TYPE.STRING) {
                        errorHand.printError(ctx, "Variable '" + id + "' is not STRING ");
                        semantic_checked = false;
                    } else if ((if_atual == TYPE.DOUBLE || if_atual == TYPE.INT) && tipo_id.get(id) == TYPE.STRING) {
                        errorHand.printError(ctx, " Variable '" + id + "' must be INT or DOUBLE");
                        semantic_checked = false;
                        return false;
                    }
                }
                if (tipo_for.containsKey(id)) {
                    if (if_atual != tipo_for.get(id)) {
                        errorHand.printError(ctx, " Variables are from different types");
                        semantic_checked = false;
                        return false;
                    }
                }
            }
        } else if (is_Array) {
            if (tipo_array_for.containsKey(id)) {
                if (id_atual != tipo_array_for.get(id)) {
                    errorHand.printError(ctx, " Variables are from different types");
                    semantic_checked = false;
                    return false;
                }
            }
            if (tipo_array_id.containsKey(id)) {
                if (id_atual != tipo_array_id.get(id)) {
                    errorHand.printError(ctx, " Variables are from different types");
                    semantic_checked = false;
                    return false;
                }
            }
        } else {
            if (tipo_for.containsKey(id)) {
                if (id_atual != tipo_for.get(id)) {
                    errorHand.printError(ctx, "Variables are from different types");
                    semantic_checked = false;
                    return false;
                }
            }
            if (tipo_id.containsKey(id)) {
                if (id_atual != tipo_id.get(id)) {
                    errorHand.printError(ctx, "Variables are from different types");
                    semantic_checked = false;
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean visitIdRandMethod(QuizGeneratorParser.IdRandMethodContext ctx) {
        String id = ctx.ID().getText();
        if (!tipo_array_id.containsKey(id) && !tipo_array_for.containsKey(id)) {
            errorHand.printError(ctx, "Variable '" + id + "' doesnt exist or its not an array");
            semantic_checked = false;
            return false;
        }
        if (tipo_array_id.get(id) != TYPE.QUESTION && tipo_array_id.get(id) != null) {
            errorHand.printError(ctx, "Variable '" + id + "' it's not a Question");
            semantic_checked = false;
            return false;
        }
        if (tipo_array_for.get(id) != TYPE.QUESTION && tipo_array_for.get(id) != null) {
            errorHand.printError(ctx, "Variable '" + id + "' it's not a Question");
            semantic_checked = false;
            return false;
        }
        errorHand.printInfo(ctx, "done");
        return true;
    }

    @Override
    public Boolean visitAnswersRandMethod(QuizGeneratorParser.AnswersRandMethodContext ctx) {
        String id = ctx.ID().getText();
        if (!tipo_id.containsKey(id) && !tipo_for.containsKey(id)) {
            errorHand.printError(ctx, "Variable '" + id + "' doesnt exist or its not an array");
            semantic_checked = false;
            return false;
        }
        if (tipo_id.get(id) != TYPE.QUESTION && tipo_id.get(id) != null) {
            errorHand.printError(ctx, "Variable '" + id + "' it's not a Question");
            semantic_checked = false;
            return false;
        }
        if (tipo_for.get(id) != TYPE.QUESTION && tipo_for.get(id) != null) {
            errorHand.printError(ctx, "Variable '" + id + "' it's not a Question");
            semantic_checked = false;
            return false;
        }
        errorHand.printInfo(ctx, "done");
        return true;
    }

    @Override
    public Boolean visitMultipleChoiceType(QuizGeneratorParser.MultipleChoiceTypeContext ctx) {
        return true;
    }

    @Override
    public Boolean visitTrueOrFalseType(QuizGeneratorParser.TrueOrFalseTypeContext ctx) {
        return true;
    }

    @Override
    public Boolean visitEasyDifficulty(QuizGeneratorParser.EasyDifficultyContext ctx) {
        return true;
    }

    @Override
    public Boolean visitMediumDifficulty(QuizGeneratorParser.MediumDifficultyContext ctx) {
        return true;
    }

    @Override
    public Boolean visitHardDifficulty(QuizGeneratorParser.HardDifficultyContext ctx) {
        return true;
    }
}
