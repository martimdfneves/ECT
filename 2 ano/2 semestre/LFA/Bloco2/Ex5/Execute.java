import java.util.*;

public class Execute extends CalculatorBaseVisitor<Integer> {
   
   private HashMap<String, Integer> dict = new HashMap<>();
   
   @Override public Integer visitProgram(CalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitStat(CalculatorParser.StatContext ctx) {
      if(ctx.expr() != null){
		System.out.println(""+visit(ctx.expr()));
      }
      else if (ctx.assignment() != null){
		visit(ctx.assignment());
	  }
      return null;
   }

   @Override public Integer visitAssignment(CalculatorParser.AssignmentContext ctx) {
	  dict.put(ctx.ID().getText(), visit(ctx.expr()));
      return null;
   }

   @Override public Integer visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      Integer v1 = visit(ctx.expr(0));
      Integer v2 = visit(ctx.expr(1));
      
      String op = ctx.op.getText();      
      Integer res = 0;
      
      switch(op){
		  case "+":
			res = v1 + v2;
			break;
		  case "-":
			res = v1 - v2;
			break;
	  }

      return res;
   }

   @Override public Integer visitExprParent(CalculatorParser.ExprParentContext ctx) {
      return visit(ctx.expr());
   }

   @Override public Integer visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      if(ctx.sign != null){
		  if (ctx.sign.getText().equals("-")){
			  int val = Integer.parseInt(ctx.Integer().getText());
			  return val * -1;
		  }
		  else if (ctx.sign.getText().equals("+")){
			  return Integer.parseInt(ctx.Integer().getText());
		  }
	  }
	  else{
		return Integer.parseInt(ctx.Integer().getText());
	  }
	  return null;
   }

   @Override public Integer visitExprId(CalculatorParser.ExprIdContext ctx) {
      return dict.get(ctx.ID().getText());
   }

   @Override public Integer visitExprMultDivMod(CalculatorParser.ExprMultDivModContext ctx) {
      Integer v1 = visit(ctx.expr(0));
      Integer v2 = visit(ctx.expr(1));
      
      String op = ctx.op.getText();
      Integer res = 0;
      
      switch(op){
		  case "*":
			res = v1 * v2;
			break;
		  case "/":
			res = v1 / v2;
			break;
		  case "%":
			res = v1 % v2;
			break;
	  }
      
      return res;
   }
}
