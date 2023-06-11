package Proyecto_Spotify;

import java.util.ArrayList;
import java.util.List;

public class PremiumClient extends Clients{
	
	private ArrayList <Playlist> Playlist = new ArrayList <Playlist> ();
	
	public PremiumClient(String user, String pasword, String name, String lastName, Integer age) {
		super(user, pasword, name, lastName, age);
	}

	public ArrayList<Playlist> getPlaylist() {
		return Playlist;
	}

	public void setPlaylist(String name, List <Content> lista) {
		
		Playlist e = new Playlist(name);
		e.addContent(lista, 2);
		Playlist.add(e);
	}
	
	public boolean addContentToPlaylist(String name,Content content) {
		
		for(int i=0;i<Playlist.size();i++) {
			if(Playlist.get(i).getName().equals(name)) {
				this.Playlist.get(i).addContentToPlaylist(content, 2);
				return true;
			}
		}
		
		return false;
	}
}
