#! /bin/bash

clear

/bin/bash -c "antlr4 ReadQuestions.g4 && antlr4 ReadQuestions.g4 -visitor && antlr4-build; exec /bin/bash" 
