grammar FractionsCalculator;

program: 
		 stat* NEWLINE EOF
       ;

stat: 
	  	 'print' expr ';'					#StatExpr
 	   | assignment ';'						#StatAssignment
 	   ;

assignment:
         expr '->' ID
       ;

expr:  
         expr op=( '*' | ':' ) expr			#ExprMultDiv
       | expr op=( '+' | '-' ) expr #ExprAddSub								
       | sign=( '-' | '+' )? Integer        #ExprInteger
       | '(' fraction ')' '^' Integer		#ExprPot
       | '(' expr ')'                       #ExprParent
       | 'reduce' fraction         			#ExprReduce
       | fraction       					#ExprFraction
       | ID                                 #ExprId
       ;

fraction: sign=( '-' | '+' )? num=Integer '/' den=Integer;
Integer: [0-9]+;
ID: [a-zA-Z_]+;
NEWLINE: '\r' ? '\n';
WS: [ \t]+ -> skip;
COMMENT: '//' [^\r\n]* -> skip;
