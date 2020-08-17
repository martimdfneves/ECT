public class Execute extends SuffixCalculatorBaseVisitor<Double> {

   @Override public Double visitProgram(SuffixCalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Double visitStat(SuffixCalculatorParser.StatContext ctx) {
	  System.out.println(""+visit(ctx.expr()));
	  return null;
   }

   @Override public Double visitExprNumber(SuffixCalculatorParser.ExprNumberContext ctx) {
      return Double.parseDouble(ctx.Number().getText());
   }

   @Override public Double visitExprSuffix(SuffixCalculatorParser.ExprSuffixContext ctx) {
	  Double v1 = visit(ctx.expr(0));
	  Double v2 = visit(ctx.expr(1));
      
      String op = ctx.op.getText();
      
      Double res = 0.0;
      
      switch(op){
		case "+":
			res = v1 + v2;
			break;
		case "-":
			res = v1 - v2;
			break;
		case "/":
			res = v1 / v2;
			break;
		case "*":
			res = v1 * v2;
			break;
      }
      return res;
   }
}
