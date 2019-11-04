package aula8_3;

public class Question implements Comparable<Question> {
	
    private String imgPath;
    private String questText;
    private String questAnswer;
    private String[] options;
    private int difficulty;
    private int prize;

    public Question(String imgPath, String questText, String questAnswer, int difficulty, String... options) {
    	
        this.imgPath = imgPath;
        this.questText = questText;
        this.questAnswer = questAnswer;
        this.difficulty = difficulty;
        this.options = Parser.scrambleArray(options);
    }

    public String getImgPath() {
    	
        return imgPath;
    }

    public String getQuestText() {
    	
        return questText;
    }

    public String getQuestAnswer() {
    	
        return questAnswer;
    }

    public String[] getOptions() {
    	
        return options;
    }

    public String getOption(int index) {
    	
        return options[index];
    }

    public int getDifficulty() {
    	
        return difficulty;
    }

    @Override
    public int compareTo(Question q) {
    	
        return this.difficulty < q.difficulty ? -1 : (this.difficulty == q.difficulty ? 0 : 1);
    }

    public int getPrize() {
    	
        return prize;
    }

    public void setPrize(final int prize) {
    	
        this.prize = prize;
    }

}