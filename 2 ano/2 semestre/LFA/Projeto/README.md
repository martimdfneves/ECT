A nossa linguagem contém duas gramáticas. 
A gramática principal chama-se *"QuizGenerator.g4"* que tem como principal função gerar o questionário que foi programado.
A gramática secundária tem como nome *"ReadQuestions.g4"*, e o seu principal objetivo é ler de uma base de dados, *"bd1.question"*, as perguntas e respostas que se encontram disponíveis para colocar nos questionários.

Todos os ficheiros necessários para a nossa linguagem encontram-se no diretório raiz, **QuizGenerator**, exceto os ficheiros de teste que estão dentro do diretório **Test** e o relatório que se encontra no diretório **Report**.

Para compilar todos os ficheiros dentro do diretório raiz basta correr o nosso script *"build.sh"* usando o seguinte comando; *"./build.sh"*.

Ao correr a nossa main *"QuizGeneratorMain.java"* pode-o fazer usando várias flags dentro do comando. Caso não o faça apenas o código gerado vai ser impresso no terminal.

**Flags suportadas:**
**[-f]:** exporta o código gerado para um ficheiro java com o nome do quiz criado.
**[-s]:** imprime as mensagens da análise semântica no terminal, juntamente com o código gerado.
**[-f -s]:** exporta o código gerado para um ficheiro java com o nome do quiz criado e as mensagens da análise semântica são exportadas para um ficheiro chamado *"semanticCheck.txt"*. 

Para poder realizar o questionário anteriormente criado é só compilar e correr o ficheiro java com o código gerado exportado.


**Exemplos de comandos:**

- *./buid.sh*
- *java QuizGeneratorMain < Test/testGeneratedCode.QG*
- *java QuizGeneratorMain -f < Test/testGeneratedCode.QG*
- *java QuizGeneratorMain -s < Test/testGeneratedCode.QG*
- *java QuizGeneratorMain -f -s < Test/testGeneratedCode.QG*
- *javac testQuest.java*
- *java testQuest*