import java.util.*;
import java.io.*;

public class quest1{
static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException{
		//Question[] questions;
		ArrayList<Question> questions = new ArrayList<>();
		
		String choice;
		double score = 0;
		String[] themes = {"animals", "history", "maths"};
		
		//Question newQuest.text = "Quantas patas tem um gato?";
		Question newQuest = new Question();
		newQuest.setTitle("O número de patas que um gato possui é...");
		
		//newQuest.difficulty = easy; -> facultativo pode ser null por default
		newQuest.setDifficulty("easy");
		
		//newQuest.answer("4 patas", 100);
		Answer ans1 = new Answer("4 patas", 100);
		
		//newQuest.answer("2 patas", 0);
		Answer ans2 = new Answer("2 patas", 0);
		
		//newQuest.answer("3 patas", 0);
		Answer ans3 = new Answer("3 patas", 0);
		
		//newQuest.answer("1 patas", 0);
		Answer ans4 = new Answer("1 patas", 0);
		
		//questions.add(newQuest);
		newQuest.addAnswer(ans1);
		newQuest.addAnswer(ans2);
		newQuest.addAnswer(ans3);
		newQuest.addAnswer(ans4);
		questions.add(newQuest);
		
		//answersMode = multipleChoice;
		String answersMode = "multipleChoice";
		
		//DB data = load("bd1.question");	
		DB data = new DB("bd1.question");
		
		//data.add(newQuest);
		data.add("animals", "P13", newQuest);
		
		ArrayList<Question> quest = new ArrayList<>();
		
		//for theme in themes:
		for (String theme : themes) {
			//Question[] quest = data.get(3, medium, theme);
			ArrayList<Question> tmp = data.get(2, "medium", theme);
			for(Question q : tmp){
				quest.add(q);
			}
		}
		
		//questions.add(quest);
		for(Question q : quest){
			questions.add(q);
		}
		
		//Question simpleQuest = data.get(1, easy, "Languages");
		Question simpleQuest = data.get("easy", "languages");
		
		//questions.add(simpleQuest);
		questions.add(simpleQuest);
		
		//rand questions;
		Collections.shuffle(questions);
		
		if(answersMode.equals("multipleChoice")){
			//for question in questions:
			for(Question question : questions){
				//question.numAnswers(4);
				question.numAnswers(4);
				//rand question.answers();
				question.shuffleAnswers();
				// print question;
				System.out.print(""+question.toString());
				//choice = userAnswer;
				System.out.print("A: ");
				choice = sc.nextLine();
				//if (choice == question.correctAnswer()):
				if(choice.equalsIgnoreCase(question.getCorrectLetter())){
					score = score + 100;
				}
				else{
					score = score - (score * 0.1);
				}
				System.out.println("");
			}
		}
		//true or false
		else if(answersMode.equals("trueOrFalse")){
			for(Question question : questions){
				question.loadTrueOrFalse();
				String str1 = question.getTitle().replaceAll("[\".]", "");
				String str2 = question.getAnswerTF().getText().replaceAll("\"", "");
				System.out.println("\""+str1+" "+str2+"\"");
				System.out.print("A: ");
				choice = sc.nextLine();
				if(choice.equalsIgnoreCase(question.trueOrFalse())){
					score = score + 100;
				}
				else{
					score = score - (score * 0.1);
				}
				System.out.println("");
			}
		}
		//print score;
		System.out.println("Score: "+score);
	} 
}
