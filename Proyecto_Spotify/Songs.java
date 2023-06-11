package Proyecto_Spotify;


public class Songs extends Content{
	
	private String artist;
	private String genre;
	private String album;
	
	
	// CONSTRUCTOR
	public Songs(String name, String artist, String genre, Integer duration, String album) {
		
		super (name, duration);
		
		this.artist = artist;
		this.genre = genre;
		this.album = album;		
	}
	
	// GETTERS AND SETTERS
	
	// ARTIST
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	// GENRE
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	// ALBUM
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
}
