import java.util.*;

public class Execute extends NumbersBaseVisitor<String> {

	Map<String, Integer> map = new HashMap<>();

   @Override public String visitProgram(NumbersParser.ProgramContext ctx) {
      return visitChildren(ctx);
   }

   @Override public String visitLine(NumbersParser.LineContext ctx) {
	  map.put(ctx.Word().getText(), Integer.parseInt(ctx.Integer().getText()));	
      return null;
   }
   
   public Map<String, Integer> getMap(){
	   return map;
   }
}
