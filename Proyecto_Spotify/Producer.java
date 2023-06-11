package Proyecto_Spotify;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List; 

public class Producer implements Serializable {
	
	public static Long nextId = 0L;
	protected Long id;
	protected String name;
	protected String nickname;
	protected List <Content> contenido = new ArrayList <Content> ();
	
	public Producer(String name, String nickname) {
		super();
		this.name = name;
		this.nickname = nickname;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	//GET NEXT ID
	public static Long getNextId() {
		Producer.nextId++;
		return Producer.nextId;
	}

	public List<Content> getContenido() {
		return contenido;
	}

	public void setContenido(Content cont) {
		this.contenido.add(cont);
	}
}
