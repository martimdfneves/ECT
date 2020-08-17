import static java.lang.System.*;
import java.util.*;

public class Execute extends CalculatorBaseVisitor<Set<String>> {

	private HashMap<String, Set<String>> varTable = new HashMap<>();

	@Override public Set<String> visitMain(CalculatorParser.MainContext ctx) {
		return visitChildren(ctx);
	}

	@Override public Set<String> visitStat(CalculatorParser.StatContext ctx) {
		Set<String> result = visit(ctx.expr());
		String str = result.toString();
		str = "{" +str.substring(1, str.length()-1).replaceAll(" ", "")+ "}";
		out.println("result: "+str);
		return result;
	} 

	@Override public Set<String> visitExprVar(CalculatorParser.ExprVarContext ctx) {
		Set<String> res = null;
		String var = ctx.Var().getText();
		if(!varTable.containsKey(var)){	
			err.println("ERROR: variable \"" + var + "\" not defined!");
			exit(1);
		}
		res = varTable.get(var);
		return res;
	}

	@Override public Set<String> visitExprSubtract(CalculatorParser.ExprSubtractContext ctx) {
		Set<String> res = new HashSet<String>();
		Set<String> s1 = visit(ctx.expr(0));
		Set<String> s2 = visit(ctx.expr(1));
		Iterator<String> iter = s1.iterator();
		while(iter.hasNext()){
			String str = iter.next();
			if(!s2.contains(str)){
				res.add(str);
			}
		}
		return res;
	}

	@Override public Set<String> visitExprParentheses(CalculatorParser.ExprParenthesesContext ctx) {
		return visit(ctx.expr());
	}

	@Override public Set<String> visitExprUnion(CalculatorParser.ExprUnionContext ctx) {
		Set<String> s1 = visit(ctx.expr(0));
		Set<String> res = s1;
		Set<String> s2 = visit(ctx.expr(1));
		Iterator<String> iter = s2.iterator();
		while(iter.hasNext()){
			res.add(iter.next());
		}
		return res;
	}

	@Override public Set<String> visitExprAssign(CalculatorParser.ExprAssignContext ctx) {
		Set<String> res = visit(ctx.expr());
		String var = ctx.Var().getText();
		varTable.put(var, res);
		return res; 
	}

	@Override public Set<String> visitExprIntersect(CalculatorParser.ExprIntersectContext ctx) {
		Set<String> res = new HashSet<>();
		Set<String> s1 = visit(ctx.expr(0));
		Set<String> s2 = visit(ctx.expr(1));
		Iterator<String> iter = s1.iterator();
		while(iter.hasNext()){
			String str = iter.next();
			if(s2.contains(str)){
				res.add(str);
			}
		}
		return res;
	}

	@Override public Set<String> visitExprSet(CalculatorParser.ExprSetContext ctx) {
		return visit(ctx.set());
	}
	
	protected Set<String> tempSet;
	@Override public Set<String> visitSet(CalculatorParser.SetContext ctx) {
		tempSet = new HashSet<String>();
		visitChildren(ctx);
		return tempSet;
	}

	@Override public Set<String> visitElem(CalculatorParser.ElemContext ctx) {
		tempSet.add(ctx.getText());
		return null;
	}
}
