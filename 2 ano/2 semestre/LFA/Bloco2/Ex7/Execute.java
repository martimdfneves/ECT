import java.util.*;

public class Execute extends CalculatorBaseVisitor<String> {

   @Override public String visitProgram(CalculatorParser.ProgramContext ctx) {
      String res = "";
      Iterator<CalculatorParser.StatContext> iter = ctx.stat().iterator();
      while(iter.hasNext()){
		res += visit(iter.next()) + "\n";
	  } 
      return res;
   }

   @Override public String visitExprStat(CalculatorParser.ExprStatContext ctx) {
      return visit(ctx.expr());
   }

   @Override public String visitAssignmentStat(CalculatorParser.AssignmentStatContext ctx) {
      return visit(ctx.assignment());
   }

   @Override public String visitAssignment(CalculatorParser.AssignmentContext ctx) {
      return ctx.ID().getText() + " = " + visit(ctx.expr());
   }

   @Override public String visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      return visit(ctx.expr(0)) + visit(ctx.expr(1)) + ctx.op.getText();
   }

   @Override public String visitExprParent(CalculatorParser.ExprParentContext ctx) {
      return visit(ctx.expr());
   }

   @Override public String visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      if(ctx.sign != null){
		return ctx.Integer().getText() + "!" + ctx.sign.getText();
	  }
	  return ctx.Integer().getText();
   }

   @Override public String visitExprId(CalculatorParser.ExprIdContext ctx) {
      return ctx.ID().getText();
   }

   @Override public String visitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      return visit(ctx.expr(0)) + visit(ctx.expr(1)) + ctx.op.getText();
   }
}
