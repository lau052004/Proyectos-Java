package Proyecto_Spotify;

public class Podcast extends Content {
	
	private String autor;
	private String category;
	
	public Podcast (String name, String autor, String category, Integer duration) {
		super (name, duration);
		this.autor = autor;
		this.category = category;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
