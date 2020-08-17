grammar Calculator;

program:
		stat* EOF
		;

stat:
		expr ? NEWLINE			#ExprStat
		| assignment? NEWLINE		#AssignmentStat
		;

assignment: 
	    ID '=' expr
   		;

expr:
	expr op=( '*' | '/' | '%' ) expr 	#ExprMultDivMod
    	| expr op=( '+' | '-' ) expr 		#ExprAddSub
    	| sign=( '+' | '-' )? Integer 	#ExprInteger
    	| '(' expr ')' 			#ExprParent
    	| ID					#ExprId
    	;

Integer: [0-9]+; 				// implement with long integers
ID: [a-zA-Z_]+;
NEWLINE: '\r' ? '\n';
WS: [ \t]+ -> skip;
COMMENT: '#' .* ? '\n' -> skip;
