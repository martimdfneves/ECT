public class Answer {
	private String text;
	private int points;
	
	public Answer(String text, int points){
		if(text.contains("\n")){
			String[] str = text.split("\n");
			this.text = str[0].trim()+" "+str[1].trim(); 
		}
		else{
			this.text = text;
		}
		this.points = points;
	}
	
	public String getText(){
		return this.text;
	} 
	
	public int getPoints(){
		return this.points;
	}
}

