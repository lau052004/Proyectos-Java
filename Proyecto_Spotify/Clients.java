package Proyecto_Spotify;

import java.util.ArrayList;
import java.io.Serializable;

public class Clients implements Serializable {

	private static final long serialVersionUID = 1L;
	public static Long nextId = 0L;
	private Long id;
	private String user;
	private String pasword;
	private String name;
	private String lastName;
	private Integer age;
	private Integer canti;
	private ArrayList<Long> songs = new ArrayList <Long> ();
	
	//CONSTRUCTOR
	public Clients(String user, String pasword, String name, String lastName, Integer age) {
		
		this.user = user;
		this.pasword = pasword;
		this.name = name;
		this.lastName = lastName;
		this.age = age;
		this.canti = 0;
		
	}
	
	//GETTERS & SETTERS
	public String getName() {
		return name;
	}

	public static void setNextId(Long nextId) {
		Clients.nextId = nextId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasword() {
		return pasword;
	}

	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getCanti() {
		return canti;
	}

	public void setCanti(Integer canti) {
		this.canti = this.canti+canti;
	}


	public Long getSongs(Integer i) {
		return songs.get(i);
	}
	
	public int getSizeSongs() {
		return this.songs.size();
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String newLastName) {
		this.lastName = newLastName;
	}

	//METODOS
	public boolean addSongToList(Long codigo){
		this.songs.add(codigo);
		return true;
	}
	
	public boolean deleteSong(int codigo) {
		this.songs.remove(codigo);
		return true;
	}
	
	public Long getNextId () {
		nextId++;
		return nextId;
	}

	
}
