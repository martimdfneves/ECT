package aula2_1;

public class Video {
	
	private static int seqID = 1;
	private final int id;
    private int totalRating;
    private int screenings;
    private int rating;
    private String title;
    private String category;
    private String idade;
    private boolean available;
    
    public Video(String t, String cat, String i) {
    	
    	id=seqID++;
    	title=t;
    	if (!cat.equals("Ação") && !cat.equals("Comédia") && !cat.equals("Infantil") && !cat.equals("Drama"))
        	throw new RuntimeException("Categoria não especializada.\n");
    	category=cat;
    	 if (!i.equals("ALL") && !i.equals("M6") && !i.equals("M12") && !i.equals("M16") && !i.equals("M18"))
    	    	throw new RuntimeException("Padrão etário não aceitável.\n");
    	idade=i;
    	available=true;
    	rating=0;
    	screenings=0;
    	totalRating=0;
    }

	public int getId() {
		return id;
	}

	public int getRating() {
		return rating;
	}

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public String getIdade() {
		return idade;
	}

	public boolean isAvailable() {
		return available;
	}
    
	public boolean loan() {
		
		if(!isAvailable()) {
			
			System.out.println("Filme não pode ser alugado");
			return false;
		}
		available=false;
		return true;
	}
	
	public void returnvid() {
		
		available=true;
	}
	
	public void updaterating(int novo) {
		
		screenings++;
		totalRating += novo;
		rating = totalRating/screenings;
	}
	
	public int compareTo(Video m) {
		  if (this.rating == m.rating)
			  return 0;
		  else if (this.rating < m.rating)
			  return -1;
		  else
			  return 1;
	}
	
	public String toString() {
	    return "ID do filme: " + id + " | Título: " + title + " | Categoria: " + category + 
	    		" | Faixa etária: " + idade + " | Disponível: " + (available ? "sim" : "não") + " | Rating: " + rating + "\n";
	}
}
