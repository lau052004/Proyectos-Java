package Proyecto_Spotify;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Content implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected static Long nextId = 0L; 
	protected Long id;
	protected String name;
	protected Integer duration;
	protected Integer numberOfDownloads;
	protected Set <Producer> productores = new HashSet <Producer> ();
	
	//CONSTRUCTOR
	public Content (String name,Integer duration) {
		this.name = name;
		this.duration = duration;
		this.numberOfDownloads = 0;
	}
	
	//GETTERS & SETTERS
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getNumberOfDownloads() {
		return numberOfDownloads;
	}

	public void setNumberOfDownloads(Integer numberOfDownloads) {
		this.numberOfDownloads = numberOfDownloads;
	}

	public static void setNextId(Long nextId) {
		Content.nextId = nextId;
	}

	
	//GET NEXT ID
	public static Long getNextId() {
		Songs.nextId++;
		return Songs.nextId;
	}
	
	//ADD DOWNLOAD
	public  Integer addDownload () {
		this.numberOfDownloads ++;
		return this.numberOfDownloads;
	}
	
	//DELETE DOWNLOAD
	public Integer deleteDownload () {
		this.numberOfDownloads --;
		return this.numberOfDownloads;
	}
	
	
	public void addProductores(Producer productor) {
		this.productores.add(productor);
	}

	public Set<Producer> showCredits() {
		return productores;
	}
}

