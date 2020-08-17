grammar Hello;			// Define a grammar called Hello
head: greetings | bye;		// accepts greetings or bye rule
greetings: 'hello' ID;		// match keyword hello followed by an identifier
bye: 'bye' ID;			// match keyword bye followed by an identifier
ID: [a-z]+;			// match lower−case identifiers
WS: [ \t\r\n]+ -> skip;		// skip spaces, tabs, newlines, \r (Windows)
