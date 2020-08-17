// Generated from Hello.g4 by ANTLR 4.8
import java.util.*;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * This class provides an empty implementation of {@link HelloVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public class Execute extends HelloBaseVisitor<String> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public String visitHead(HelloParser.HeadContext ctx) { return visitChildren(ctx); }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public String visitGreetings(HelloParser.GreetingsContext ctx) { 
		System.out.println("Olá "+visit(ctx.name()));
		return null; 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public String visitBye(HelloParser.ByeContext ctx) { 
		System.out.println("Adeus "+visit(ctx.name()));
		return null;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public String visitName(HelloParser.NameContext ctx) { 
		Iterator<TerminalNode> iter = ctx.ID().iterator();
		String str = "";
		while(iter.hasNext()){
			str += iter.next()+ " ";
		}  
		return str.trim();
	}
}
