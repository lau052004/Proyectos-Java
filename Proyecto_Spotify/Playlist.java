package Proyecto_Spotify;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Serializable{
	
	public static Long nextId = 0L;
	private Long Id;
	private String name;
	private List <Content> playlist = new ArrayList <Content> ();
	

	public Playlist(String name) {
		this.name = name;
		this.Id = nextId;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List <Content> getPlaylist() {
		if(playlist!=null) {
			if(playlist.size()>0) {
				return playlist;
			}
		}
		return null;
	}
	
	//Recibe la lista de contenidos que se quiere guardar en la Playlist
	public void addContent(List <Content> playlist, int type) {
		boolean guardar = true;
		
		//standard
		if(type==1) {
			for(Content c:playlist) {
				if(c instanceof Podcast) {
					guardar = false;
				}
			}
			if(guardar == true) {
				this.playlist = playlist;
			}
			else {
				System.out.println("No pudo crearse por que el contenido no existe o corresponde a un podcast");
				this.playlist=null;
			}
		}
		// Premium
		else {
			this.playlist = playlist;
		}
	}
	
	public void addContentToPlaylist(Content content, int type) {
		// standard
		if(type==1) {
			if(content instanceof Songs) {
				this.playlist.add(content);
			}
			else {
				System.out.println("No se pudo agregar el contenido a la playlist");
			}
		}
		// premium
		else {
			this.playlist.add(content);
		}
	}
	
}
