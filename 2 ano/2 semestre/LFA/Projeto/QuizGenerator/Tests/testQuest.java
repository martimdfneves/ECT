// Código java gerado pela execução do ficheiro de teste "testGeneratedCode.QG"

import java.util.*;
import java.io.*;

public class testQuest {
static Scanner sc = new Scanner(System.in);

	public static void main (String [] args) throws IOException{
		    ArrayList<Question> questions = new ArrayList<>();   
		   String choice;   
		    double  score =  0 ;   
		    String[]  themes = {"animals", "history", "maths"};   
		  Question newQuest = new Question();
			newQuest.setTitle("O número de patas que um gato possui é...");  
		  
		  
		  newQuest.setDifficulty("easy");  
		  Answer ans1 = new Answer("4 patas", 100);
			newQuest.addAnswer(ans1);  
		  Answer ans2 = new Answer("2 patas", 0);
			newQuest.addAnswer(ans2);  
		  Answer ans3 = new Answer("3 patas", 0);
			newQuest.addAnswer(ans3);  
		  Answer ans4 = new Answer("1 patas", 0);
			newQuest.addAnswer(ans4);  
		  questions.add(newQuest);  
		  String answerMode = "multipleChoice";  
		   DB data = new DB("bd1.question");   
		  data.add("animals", "P13", newQuest);  
		 for (String theme : themes) {
				    ArrayList<Question> quest = data.get(2, "medium", theme);   
				  for(Question q : quest){
						questions.add(q);
					}   
			} 
		   Question simpleQuest = data.get("easy", "languages");   
		  questions.add(simpleQuest);  
		   Collections.shuffle(questions);   
		 for (Question question : questions) {
				   question.numAnswers(4);  
				   question.shuffleAnswers();   
				  System.out.println(question.toString());  
				  System.out.print("A: ");
					choice = sc.nextLine();
					System.out.println("----------------------------------------------------------");  
				 if (choice.equalsIgnoreCase(question.getCorrectLetter())) {
						     score =  score + 100 ;    
					}
					else{
						      score =  score - ( score * 0.1 ) ;     
					}
				  
			} 
		  System.out.println("Score: "+score);	   
	}
}
