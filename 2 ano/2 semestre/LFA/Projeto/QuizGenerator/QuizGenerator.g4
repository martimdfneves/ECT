grammar QuizGenerator;

program: 'Begin' 'create' ID stat* 'endcreate' EOF;

stat: instructions ';'					#instStat
	| forBlock							#forStat
	| ifBlock							#ifStat
	;

forBlock: 'for' ID 'in' ID ':' stat+ endf
		;

endf: 'endfor'
    ;

ifBlock: 'if' '(' condition ')' ':' stat+ other? endif		
	   ; 

other: 'else' ':' stat+
	 ;

endif: 'endif' 
	 ;

condition: mathExpr '==' ID '.correctAnswer()'		#condCorrectAnswer
		 | mathExpr '==' mathExpr  					#condEquals
		 | mathExpr '>=' mathExpr  					#condBigEq
		 | mathExpr '<=' mathExpr  					#condLowEq
		 | mathExpr '>' mathExpr  					#condBig
		 | mathExpr '<' mathExpr  					#condLow
		 ;

instructions: assignment				#assignInst
			| command					#commandInst
			| createQuestion			#questInst
			;

createQuestion: 'Question' ID '.text' '=' WORD 		#createQuestionphrase
			  | ID '.theme' '=' WORD				#createQuestionTheme
			  | ID '.difficulty' '=' difficulty		#createQuestionDifficulty
			  | ID '.answer' '(' WORD ',' NUM ')' 	#createQuestionAnswer
			  | ID '.name' '=' WORD 				#createQuestionName
			  ;

assignment: declaration 		 		#declarAssign
		  | attribution 				#attribAssign
		  | questionDeclaration			#questDeclarAssign
		  | questionAttribution			#questAttribAssign
		  | bdAttribution				#bdAttribAssign
		  ;

declaration: type ID					#declarVar
		   | type '[]' ID 				#declarArray
		   ;

attribution: type? ID '=' expr 								#attribVar
		   | type? '[]' ID '=' '[' (expr ',')* expr ']'		#attribArray
		   ;	

expr: WORD				#wordExpr
	| mathExpr			#math
	;

type: 'String' 		#typeString											
	| 'int'			#typeInt						
	| 'double'      #typeDouble
	;
	
bdAttribution: 'DB' ID '=' 'load' '(' WORD ')' 	#bdAttrib
			;

questionType: 'Question' 
			;

questionDeclaration: questionType ID 			#questDeclarVar
				   | questionType '[]' ID		#questDeclarArray
				   ;

questionAttribution: questionType? ID '=' ID '.get' '(' difficulty ',' (ID|WORD) ')'					#questAttribVar
				   | questionType? '[]' ID '=' ID '.get' '(' NUM ',' difficulty ',' (ID|WORD) ')'		#questAttribArray
				   ;

command: 'answersMode' '=' testType 			#answerModeCommand
	   | ID '.add' '(' ID ')'					#addCommand
	   | 'rand' randMethod						#randCommand
	   | ID '.numAnswers' '(' NUM ')'			#numAnswersCommand
	   | 'print' (ID | WORD) 					#printCommand
	   | ID '=' 'userAnswer'					#userAnswer
	   ;

mathExpr: mathExpr op=('*' | '/') mathExpr		#multDivExpr
		| mathExpr op=('+' | '-') mathExpr		#addSubExpr
		| NUM 									#numMathExpr
		| '(' mathExpr ')'						#parenthExpr
		| ID									#idExpr
		;

randMethod: ID 									#idRandMethod
		  | ID '.answers()'						#answersRandMethod
	      ;

testType: 'multipleChoice'						#multipleChoiceType
		| 'trueOrFalse'							#trueOrFalseType
		;

difficulty: 'easy'								#easyDifficulty
		  | 'medium'							#mediumDifficulty
		  | 'hard'								#hardDifficulty
		  ;

NUM: [0-9]+ ('.'[0-9]*)?;
ID: [a-zA-Z] [a-zA-Z0-9_]*;
WORD: ('"' (~'"')* '"');
WS: [ \t\r\n]+ -> skip;
COMMENT: '//' .*? '\n'-> skip;