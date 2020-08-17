grammar Calculator;

 main: 
		 stat* EOF
		;

stat: 
		 expr
		;       

expr:	  
		  expr '\\' expr 			#ExprSubtract
		| expr '&' expr 			#ExprIntersect
		| expr '+' expr 			#ExprUnion
		| '(' expr ')' 				#ExprParentheses
		| Var '=' expr    			#ExprAssign
		| Var						#ExprVar
		| set						#ExprSet
		;

set:	  
		 '{' (elem (',' elem)* )? '}'
		;

elem:   
		  sign=('+' | '-')? Num 
		| Word
		;

Num: [0-9]+;
Word: [a-z]+; 
Var: [A-Z]+;
Comment: '--' .*? '\n' -> skip;
WS: [ \t\n\r] -> skip;