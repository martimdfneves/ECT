grammar Numbers;

program: 	
		line* EOF
		;

line: 
		Integer '-' Word NEWLINE
		;

Integer: [0-9]+; 
Word: [a-zA-Z]+;
NEWLINE: '\r' ? '\n';
WS: [ \t]+ -> skip; 