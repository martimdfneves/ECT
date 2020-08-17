grammar Quiz;

program:
		   question+ EOF
		  ; 

question: 
		   ID '.' ID '(' String ')' '{' answer+ '}' 
		  ;

answer:    String ':' Number ';' 
          ;

Number: [0-9]+;
ID: [a-zA-Z0-9]+;
String : '"' .*? '"' ;
WS: [ \t\r\n]+ -> skip;
COMMENT: '#' .* ? '\n' -> skip;