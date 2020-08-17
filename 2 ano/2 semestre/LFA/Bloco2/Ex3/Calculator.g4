grammar Calculator;

program:
		stat* EOF
		;

stat:
		expr ? NEWLINE
		;

expr:
		expr op=( '*' | '/' | '%' ) expr 	#ExprMultDivMod
    	| expr op=( '+' | '-' ) expr 		#ExprAddSub
    	| Integer 							#ExprInteger
    	| '(' expr ')' 						#ExprParent
    	;

Integer: [0-9]+; 							// implement with long integers
NEWLINE: '\r' ? '\n';
WS: [ \t]+ -> skip;
COMMENT: '#' .* ? '\n' -> skip;
