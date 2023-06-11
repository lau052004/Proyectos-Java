package Proyecto_Spotify;

import java.util.List;

public class StandardClient extends Clients{
	
	private Playlist playlist;
	boolean tienePlaylist;

	public StandardClient(String user, String pasword, String name, String lastName, Integer age) {
		super(user, pasword, name, lastName, age);
		tienePlaylist = false;
	}
	
	public Playlist getPlaylist() {
		if(playlist!=null) {
			return playlist;
		}
		return null;
	}

	public void setPlaylist(String name, List <Content> lista) {
		this.playlist = new Playlist(name);
		this.playlist.addContent(lista, 1);
		tienePlaylist = true;
	}
	
	public void addContentToPlaylist(Content content) {
		this.playlist.addContentToPlaylist(content, 1);
	}

	public boolean isTienePlaylist() {
		return tienePlaylist;
	}

}
