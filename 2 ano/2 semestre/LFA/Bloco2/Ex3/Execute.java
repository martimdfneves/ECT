public class Execute extends CalculatorBaseVisitor<Integer> {

   @Override public Integer visitProgram(CalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Integer visitStat(CalculatorParser.StatContext ctx) {
	  if(ctx.expr() != null){
		System.out.println(""+visit(ctx.expr()));
      }
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
      return Integer.parseInt(ctx.Integer().getText());
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
