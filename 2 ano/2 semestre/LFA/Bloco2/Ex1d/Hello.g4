grammar Hello;			// Define a grammar called Hello
head: greetings | bye;		// accepts greetings or bye rule
greetings: 'hello' name;	// match keyword hello followed by an identifier
bye: 'bye' name;		// match keyword bye followed by an identifier
name: ID+;			
ID: [a-zA-Z]+;			// match lower−case identifiers
WS: [ \t\r\n]+ -> skip;		// skip spaces, tabs, newlines, \r (Windows)
