import java.util.*;

public class Execute extends FractionsCalculatorBaseVisitor<Fraction> {
	
   private HashMap<String, Fraction> dict = new HashMap<>();
	
   @Override public Fraction visitProgram(FractionsCalculatorParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public Fraction visitStatExpr(FractionsCalculatorParser.StatExprContext ctx) {
      System.out.println(""+visit(ctx.expr()));
      return null;
   }

   @Override public Fraction visitStatAssignment(FractionsCalculatorParser.StatAssignmentContext ctx) {
      return visit(ctx.assignment());
   }

   @Override public Fraction visitAssignment(FractionsCalculatorParser.AssignmentContext ctx) {
      dict.put(ctx.ID().getText(), visit(ctx.expr()));
      return null;
   }

   @Override public Fraction visitExprAddSub(FractionsCalculatorParser.ExprAddSubContext ctx) {
      Fraction f1 = visit(ctx.expr(0));
      Fraction f2 = visit(ctx.expr(1));
      
      int f1num = f1.getNum();
      int f1den = f1.getDen();
      int f2num = f2.getNum();
      int f2den = f2.getDen();
      
      String op = ctx.op.getText();
	  Fraction res = null;
      
      switch(op){
		case "+":
			if(f1den != f2den){
				res = new Fraction((f1num*f2den + f2num*f1den), (f1den*f2den));
			}
			else if (f1den == f2den){
				res = new Fraction((f1num + f2num), (f1den));
			}
			break;
		case "-":
			if(f1den != f2den){
				res = new Fraction((f1num*f2den - f2num*f1den), (f1den*f2den));
			}
			else if (f1den == f2den){
				res = new Fraction((f1num - f2num), (f1den));
			}
			break;
	  }
	  return res;
   }

   @Override public Fraction visitExprPot(FractionsCalculatorParser.ExprPotContext ctx) {
      Fraction f1 = visit(ctx.fraction());
      int exp = Integer.parseInt(ctx.Integer().getText());
      
	  int f1num = f1.getNum();
	  int f1den = f1.getDen();
	  int num = 1;
	  int den = 1;
	  
	  for(int i = 1; i <= exp; i++){
		  num = num * f1num;
		  den = den * f1den;
      }
      return new Fraction(num, den);
   }

   @Override public Fraction visitExprParent(FractionsCalculatorParser.ExprParentContext ctx) {
      return visit(ctx.expr());
   }

   @Override public Fraction visitExprMultDiv(FractionsCalculatorParser.ExprMultDivContext ctx) {
      Fraction f1 = visit(ctx.expr(0));
      Fraction f2 = visit(ctx.expr(1));
      
      int f1num = f1.getNum();
      int f1den = f1.getDen();
      int f2num = f2.getNum();
      int f2den = f2.getDen();
      
      String op = ctx.op.getText();
	  Fraction res = null;
      
      switch(op){
		case "*":
			res = new Fraction((f1num*f2num), (f1den*f2den));
			break;
		case ":":
			res = new Fraction((f1num*f2den), (f2num*f1den));
			break;
	  }
	  return res;
   }

   @Override public Fraction visitExprInteger(FractionsCalculatorParser.ExprIntegerContext ctx) {
      if(ctx.sign != null){
		  if(ctx.sign.getText().equals("-")){
			  int val = Integer.parseInt(ctx.Integer().getText());
			  return new Fraction(val * -1, 1);
	      }
	      else if (ctx.sign.getText().equals("+")){
			  return new Fraction(Integer.parseInt(ctx.Integer().getText()), 1);
		  }
	  }
	  else{
		return new Fraction(Integer.parseInt(ctx.Integer().getText()), 1);
      }
      return null;
   }

   @Override public Fraction visitExprReduce(FractionsCalculatorParser.ExprReduceContext ctx) {
      Fraction f1 = visit(ctx.fraction());
      int num = f1.getNum();
      int den = f1.getDen();
      int max = 1;
      
      if(num > den){
		for (int i = 2; i <= den; i++){
			if( (num % i == 0) && (den % i == 0) ){
				max = i;
			}
		}
	  }
	  else if(den > num){
		 for (int i = 2; i <= num; i++){
			if( (num % i == 0) && (den % i == 0) ){
				max = i;
			}
		 } 
	  }
	  return new Fraction(num/max, den/max);
   }

   @Override public Fraction visitExprId(FractionsCalculatorParser.ExprIdContext ctx) {
      if(dict.containsKey(ctx.ID().getText())){
		return dict.get(ctx.ID().getText());
	  }
	  else{
		System.out.println("Error!! ID sem valor atribuído!!");
	  }
	  return null;
   }

   @Override public Fraction visitExprFraction(FractionsCalculatorParser.ExprFractionContext ctx) {
      return visit(ctx.fraction());
   }

   @Override public Fraction visitFraction(FractionsCalculatorParser.FractionContext ctx) {
      if (ctx.sign != null){
		  if(ctx.sign.getText().equals("+")){
			return new Fraction(Integer.parseInt(ctx.num.getText()), Integer.parseInt(ctx.den.getText()));
		  }
		  else if(ctx.sign.getText().equals("-")){
			int val = Integer.parseInt(ctx.num.getText());
			return new Fraction(val * -1, Integer.parseInt(ctx.den.getText()));
		  }
	  }
      return new Fraction(Integer.parseInt(ctx.num.getText()), Integer.parseInt(ctx.den.getText()));
   }
}
