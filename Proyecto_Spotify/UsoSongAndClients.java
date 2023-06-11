package Proyecto_Spotify;

import java.io.EOFException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class UsoSongAndClients {

	private static Integer cantClientes = 0;
	private static Integer cantCanciones = 0;
	
	// Para saber si hay canciones o padcast, ya que con esto puedo imprimir bien
	private static boolean TenemosCan = false;
	private static boolean TenemosPod = false;
	
	//Listas principales
	private static List <Content> listaCanciones;
	private static List <Clients> listaClientes;
	private static Map <Long,Producer> listaProductores;
	
	//Listas para opciones de busqueda
	private static List <Songs> cancionesXalbum;
	private static List <Songs> artistasXgenero;
	
	//Listas para imprimir los datos de un archivo antes de actualizar
	private static List <Content> pruebaCanciones;
	private static List <Clients> pruebaClientes;
	private static Map <Long,Producer> pruebaProductores;
	private static Set <Producer> aux = new HashSet <Producer> ();
	
	public static final String delimiter = ";";
	public static String csvFile = "Productores.csv";
	
	public static void main(String[] args) {
	
		
		Integer input = 0;
		Scanner sc = new Scanner(System.in);
		listaCanciones = new ArrayList <Content> ();
		listaClientes = new ArrayList <Clients> ();
		listaProductores = new HashMap <Long,Producer> ();
		
		pruebaCanciones =  new ArrayList <Content> ();
		pruebaClientes = new ArrayList <Clients> ();
		pruebaProductores = new HashMap <Long,Producer> (); 
		
		
		Integer contador = 0;
		
		String fileNameSongs = "ArchivoCanciones.dat";
		String fileNameClients = "ArchivoClientes.dat";
		String fileNameProducer = "ArchivoProductores.dat";
		
		
		try {
			do {
				
				System.out.println("\nMENU");
				System.out.println("1. Adicionar contenido");
				System.out.println("2. Registrar nuevos usuarios");
				System.out.println("3. Adicionar productores");
				System.out.println("4. Listar canciones de un �lbum");
				System.out.println("5. Listar artistas para un g�nero dado");
				System.out.println("6. Buscar canciones con una palabra clave");
				System.out.println("7. A�adir cancion a un usuario registrado");
				System.out.println("8. A�adir podcast a un usuario registrado");
				System.out.println("9. Eliminar contenido de la lista del usuario");
				System.out.println("10. Listar usuarios");
				System.out.println("11. Listar contenido");
				System.out.println("12. Listar productores");
				System.out.println("13. Guardar el estado actual de la base de datos");
				System.out.println("14. Imprimir un estado previo de la BD");
				System.out.println("15. Cargar un estado previo de la BD");
				System.out.println("16. Crear playlist para un usuario");
				System.out.println("17. A�adir cancion a la playlist de un usuario");
				System.out.println("18. A�adir contenido a un productor");
				System.out.println("19. Salir");
				System.out.println("Seleccione la opcion que desea ingresar: ");
				input = sc.nextInt(); 
					
				switch(input) {
				case 1:
					try {
						if(addNewPodcastOrSong()) {
							System.out.println("\nLas canciones fueron a�adidas correctamente a la base de datos");
						}
						else {
							System.err.println("\nLas canciones NO fueron a�adidas correctamente a la base de datos");
						}
						
					} catch (InputMismatchException e){
						System.err.println("El �ltimo dato ingresado es erroneo, de manera que los datos de la cancion no podran ser almacenados");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());
					}
					break;
						
				case 2:
					try {
						if(addNewClient()) {
							System.out.println("\nLos clientes fueron a�adidos correctamente a la base de datos");
						}
						else {
							System.err.println("\nAVISO: Los clientes NO fueron a�adidos correctamente a la base de datos");
						}				
						
					} catch (InputMismatchException e){
						System.err.println("El �ltimo dato ingresado es erroneo, de manera que los datos del usuario no podran ser almacenados");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());

					}
					break;
				
				case 3:
					if(addNewProducer()==true) {
						System.out.println("Los productores fueron a�adidos correctamente a la base de datos");
					}
					else {
						System.err.println("\nAVISO: Los productores NO fueron a�adidos correctamente a la base de datos");
					}
						
					break;
				case 4:
					String albumBusca;
					
					System.out.println("Ingrese el nombre del album: ");
					albumBusca = sc.next();
					
					listSongsByAlbum(albumBusca);
					
					if(cancionesXalbum.size()>0)
					{
						System.out.println("\nLas canciones pertenecientes al album " + albumBusca + " son:");
						for(int i=0;i<cancionesXalbum.size();i++)
						{
							System.out.println("Codigo: " + cancionesXalbum.get(i).getId() + "     Nombre: " + cancionesXalbum.get(i).getName());
						}
					}
					else {
						System.err.println("\nAVISO: No existen canciones pertenecientes a este album o el album no existe");
					}
					break;
					
				case 5:
					String generoBusca;
					
					System.out.println("Ingrese el nombre del genero musical: ");
					generoBusca = sc.next();
					
					listSongsByArtist(generoBusca);
					
					
					if(artistasXgenero.size()>0){
						System.out.println("\nLos artistas que pertenecen al genero "+generoBusca+" son: ");
						for(int i=0;i<artistasXgenero.size();i++)
						{
							System.out.println(" - " + artistasXgenero.get(i).getArtist());
						}
					}
					else {
						System.err.println("\nAVISO: No existen artistas en las BD que pertenezcan a este genero o el genero musical no existe");
					}
					break;
					
				case 6: 
					try {
						newOption();
					} catch (InputMismatchException e){
						System.err.println("AVISO: El �ltimo dato ingresado es erroneo");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());
					} catch (LongitudErronea e) {
						System.err.println("AVISO: La palabra clave debe tener mas de 3 caracteres");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());
					}
					break;
							
				case 7:
					String nom;
					Long newCod = 0L;
	     			
					try {
		     			System.out.println("\nIngrese el usuario");
		    			nom = sc.next();
		    			System.out.println("Ingrese el codigo de la cacion que desea ingresar");
						newCod = sc.nextLong();
						
						if(addSongToList(nom,newCod)) {
							System.out.println("\nSe acualizaron los datos correctamente");
						}
						else {
							System.err.println("\nAVISO: NO se actualizaron los datos correctamente");
						}
					}catch (InputMismatchException e){
						System.err.println("El �ltimo dato ingresado es erroneo, de manera que la cancion no pudo ser a�adida");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());
					}
					break;
					
				case 8:
					String nom1;
					Long newCod2 = 0L;
	     			
					try {
		     			System.out.println("\nIngrese el usuario");
		    			nom1 = sc.next();
		    			System.out.println("Ingrese el codigo del podcast que desea ingresar");
						newCod = sc.nextLong();
						
						if(addPodcastToList(nom1,newCod2)) {
							System.out.println("\nSe acualizaron los datos correctamente");
						}
						else {
							System.err.println("\nAVISO: NO se actualizaron los datos correctamente");
						}
					}catch (InputMismatchException e){
						System.err.println("El �ltimo dato ingresado es erroneo, de manera que la cancion no pudo ser a�adida");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());
					}
					break;
			
				case 9:
					String nom2, lastName;
					Long newCod1 = 0L;
	
					try {
		     			System.out.println("\nIngrese el usuario");
		    			nom2 = sc.next();
		    			System.out.println("Ingrese el codigo de la cacion que desea eliminar");
						newCod1 = sc.nextLong();
						if(eliminar(newCod1,nom2)) {
							System.out.println("\nSe acualizaron los datos correctamente");
						}
						else {
							System.err.println("\nAVISO: NO se actualizaron los datos correctamente");
						}
					} catch (Exception e) {
						System.err.println("AVISO: El �ltimo dato ingresado es erroneo");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());
					}
					
					break;
					
				case 10:
					imprimirClientes();
					break;
					
				case 11:
					imprimirCanciones();
					break;
					
				case 12:
					imprimirProductores();
					break;
					
				
				
				case 13:
					try {	
						guardarArchivo(fileNameSongs,fileNameClients,fileNameProducer);
						System.out.println("El archivo fue creado exitosamente!");
					}catch(IOException e) {
						System.out.println("No pudo ser creado el archivo correctamente");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());
					}
					break;
					
				case 14:	
					//Imprimir un estado anterior de la lista de canciones
					try {
						
						pruebaCanciones = imprimirBackUp(fileNameSongs);
						System.out.println("LISTA DE CANCIONES BACKUP");
						for(int i=0; i<pruebaCanciones.size();i++) {
							if(pruebaCanciones.get(i) instanceof Songs) {
								System.out.println("ID: " + pruebaCanciones.get(i).getId());
								System.out.println("Nombre: " + pruebaCanciones.get(i).getName());
								System.out.println("Artista: " + ((Songs) pruebaCanciones.get(i)).getArtist());
								System.out.println("Genero: " + ((Songs) pruebaCanciones.get(i)).getGenre());
								System.out.println("Album: " + ((Songs) pruebaCanciones.get(i)).getAlbum());
								System.out.println("Cant. Descargas: " + pruebaCanciones.get(i).getNumberOfDownloads());
								System.out.println("Duracion: " + pruebaCanciones.get(i).getDuration() + "min\n");
							}
						}
						
						System.out.println("LISTA DE PODCAST BACKUP");
						for(int i=0; i<pruebaCanciones.size();i++) {
							if(pruebaCanciones.get(i) instanceof Podcast) {
								System.out.println("ID: " + listaCanciones.get(i).getId());
								System.out.println("Nombre: " + listaCanciones.get(i).getName());
								System.out.println("Autor: " + ((Podcast) listaCanciones.get(i)).getAutor());
								System.out.println("Categoria: " + ((Podcast) listaCanciones.get(i)).getCategory());
								System.out.println("Cant. Descargas: " + listaCanciones.get(i).getNumberOfDownloads());
								System.out.println("Duracion: " + listaCanciones.get(i).getDuration() + "min\n");
							}
						}
						
						pruebaClientes = imprimirBackUp2(fileNameClients);
						System.out.println("LISTA DE CLIENTES BACKUP");
						for(int i=0; i<pruebaClientes.size();i++) {
							System.out.println("ID: " + pruebaClientes.get(i).getId());
							System.out.println("Nombre: "+pruebaClientes.get(i).getName());
							System.out.println("Contrase�a: "+pruebaClientes.get(i).getPasword());
							System.out.println("User: "+pruebaClientes.get(i).getUser());
							System.out.println("Edad: "+pruebaClientes.get(i).getAge());
							if(pruebaClientes.get(i).getSizeSongs()>0)
							{
								System.out.println("Codigo canciones descargadas: ");
								for(int j=0;j<pruebaClientes.get(i).getSizeSongs();j++)
								{
									for(int h=0;h<pruebaClientes.size();h++) {
										if(pruebaClientes.get(i).getSongs(j)==pruebaClientes.get(h).getId()) {
											System.out.println(" - " + pruebaClientes.get(h).getName());
										}
									}
									
								}
							}
							if(pruebaClientes.get(i) instanceof StandardClient) {
								System.out.println("Tipo: Premium");
							}
							else {
									System.out.println("Tipo: Standard");
							}
							System.out.println("\n");
						}
						
						pruebaProductores = imprimirBackUp3(fileNameProducer);
						System.out.println("LISTA DE PRODUCTORES BACKUP");
						
						Iterator it = listaProductores.keySet().iterator();
				    	while(it.hasNext()){
				    	  Long key = (Long) it.next();
				    	  System.out.println("Id: " + key + " -> Name: " + listaProductores.get(key).getName());
				    	}
						
					} catch (IOException e) {
						System.out.println("No se pudo abrir el archivo");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
					break;
				
				case 15:
					
					//Cambiar el estado de la lista de canciones 
					try {
						listaCanciones = imprimirBackUp(fileNameSongs);
						listaClientes = imprimirBackUp2(fileNameClients);
						listaProductores = imprimirBackUp3(fileNameProducer);
						System.out.println("Actualizacion exitosa!");
					} catch (IOException e) {
						System.out.println("No se pudo abrir el archivo");
						System.err.println("Se ha producido un error de tipo: " + e.getClass().getName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					
					break;
					
				case 16:
					if(crearPlaylist()) {
						System.out.println("Creaci�n exitosa");
					}
					else {
						System.out.println("No se pudo crear la playlist");
					}
					break;
					
				case 17:
					if(addContentToAPlaylist()) {
						System.out.println("Playlist creada correctamente");
					} else {
						System.out.println("La playlist no pudo ser creada");
					}
					break;
				
				case 18:
					if(addContentToAProducer()) {
						System.out.println("Contenido agregado correctamente");
					} else {
						System.out.println("El contenido no pudo ser agregado correctamente");
					}
					break;
				
				case 19:
					System.out.println("FIN DEL PROGRAMA");
					break;
					
				default:
					System.err.println("AVISO: El numero ingresado no corresponde a ninguna de las opciones");
					break;
				}
					
			}while(input!=19);	
			
		}catch (InputMismatchException e){
			System.err.println("AVISO: El �ltimo dato ingresado no corresponde a un numero");
			System.out.println("FIN DEL PROGRAMA");
		}
	}
	

	//----------------------------------------------CASE 1----------------------------------------------------------------------------
	public static boolean addNewPodcastOrSong() throws InputMismatchException {
		
			Scanner scanner = new Scanner (System.in);
			String continuar = "no", seguir = "no" ;
			String nom, artist, gen, album, autor, category, productor;
			Integer dura;
			Long id,idProducer;
			Integer option;
			Content nueva;
			boolean pro;
			
	
			do {	
				System.out.println("\n1. Cancion\n"+
	                    "2. Podcast");
				option = scanner.nextInt();
			
				switch (option) {
				case 1:
						System.out.println("\nNombre: ");
						nom = scanner.next();
						System.out.println("Artista: ");
						artist = scanner.next();
						System.out.println("Genero: ");
						gen = scanner.next();
						System.out.println("Album: ");
						album = scanner.next();
						System.out.println("Duracion(min): ");
						dura = scanner.nextInt();
						nueva = new Songs (nom, artist, gen, dura, album);
						System.out.println("Desea agregar un productor? (si/no)");
						productor = scanner.next();
						
						if(productor.equals("si")) {
							//Guardar los productores de la canci�n 
							do {
								pro=false;
								System.out.println("ID Productor");
								idProducer = scanner.nextLong();
								
								Iterator it = listaProductores.keySet().iterator();
						    	while(it.hasNext()){
						    	  Long key = (Long) it.next();
						    	  if(key==idProducer) {
						    		  nueva.addProductores(listaProductores.get(key));
						    		  listaProductores.get(key).setContenido(nueva); //REVISAR
						    		  pro = true;
						    	  }
						    	}
						    	
						    	if(pro==false) {
						    		System.out.println("El productor no existe en la base de datos");
						    	}
								
								System.out.println("\nAgregar otro productor? (si/no)");
								seguir = scanner.next();
							}while(seguir.equals("si"));
						}
						listaCanciones.add(nueva);
						TenemosCan = true;
						listaCanciones.get(cantCanciones);
						id = Songs.getNextId();
						listaCanciones.get(cantCanciones).setId(id);
						break;
					
				case 2:
						System.out.println("\nNombre: ");
						nom = scanner.next();
						System.out.println("Autor: ");
						autor = scanner.next();
						System.out.println("Categoria: ");
						category = scanner.next();
						System.out.println("Duracion(min): ");
						dura = scanner.nextInt();
						nueva = new Podcast (nom, autor, category, dura);
						//Guardar los productores del Podcast 
						do {
							pro = false;
							System.out.println("ID Productor");
							idProducer = scanner.nextLong();
							
							Iterator it = listaProductores.keySet().iterator();
					    	while(it.hasNext()){
					    	  Long key = (Long) it.next();
					    	  if(key==idProducer) {
					    		  nueva.addProductores(listaProductores.get(key));
					    		  listaProductores.get(key).setContenido(nueva); //REVISAR
					    		  pro = true;
					    	  }
					    	}
					    	
					    	if(pro==false) {
					    		System.out.println("El productor no existe en la base de datos");
					    	}
							
							System.out.println("\nAgregar otro productor? (si/no)");
							seguir = scanner.next();
						}while(seguir.equals("si"));
						
						listaCanciones.add(nueva);
						TenemosPod = true;
						listaCanciones.get(cantCanciones);
						id = Songs.getNextId();
						listaCanciones.get(cantCanciones).setId(id);
						break;	
						
				default:
					System.err.println("AVISO: El numero ingresado no corresponde a ninguna de las opciones");
					break;
				
				}
					
				System.out.println("\nDesea agregar una nueva cancion o un nuevo podcast? (si/no)");
				continuar = scanner.next();
				cantCanciones++;
				
			}while(continuar.equals("si"));
			
			return true;
	}
	
	//----------------------------------------------CASE 2----------------------------------------------------------------------------
	
	//CARACTERES 
		private static boolean caracteres(String user) {
			String p = "^[aA-zZ][a-z0-9_]{7,30}$";
			
			if(user.matches(p)){
				return true;
			}
			else {
				return false;
			}
		}
	
	private static boolean addNewClient() throws InputMismatchException {
		
		Scanner scanner = new Scanner (System.in);
		boolean existe, encontrado, alerta;
		String continuar = "no";
		String seguir;
		Long codigo;
		Integer option;
		
		String user;
		String pasword;
		String name;
		String lastName;
		Integer age;
		Integer canti;
		Long id;
		
		
		do {
				existe = false;
				alerta = false;
				System.out.println("\n1. Cliente PREMIUM\n"+
						           "2. Cliente STANDARD");
				option = scanner.nextInt();
				
				if(option==1||option==2) {
					System.out.println("\nUsuario: ");
					user = scanner.next();
					
					// Evaluar si contiene alguno de los caracteres prohibidos
					if(!caracteres(user)) {
						System.err.println("AVISO: ERROR el usuario ingresado contiene caracteres invalidos o su longitud esta fuera del rango de 8-30 caracteres");
					}
					else {
						
						System.out.println("Contrasena: ");
						pasword = scanner.next();
						System.out.println("Nombre: ");
						name = scanner.next();
						System.out.println("Apellido: ");
						lastName = scanner.next();
							
							encontrado = false;
					
							for(int i=0; i<listaClientes.size();i++) {
								if(listaClientes.get(i).getName().equals(name)&&listaClientes.get(i).getLastName().equals(lastName)){
									existe = true;
								}
							}
							
							if(existe==false)
							{
								System.out.println("Edad: ");
								age = scanner.nextInt();
								
								if(option == 1) {
									canti = 0;
									Clients nueva = new PremiumClient (user, pasword, name, lastName, age);
									listaClientes.add(nueva);
									id = listaClientes.get(cantClientes).getNextId();
									listaClientes.get(cantClientes).setId(id);
									
									System.out.println("Desea ingresar el codigo de una cancion descargada? (si/no)");
									seguir = scanner.next();
									
									// GUADAR CANCIONES	
									while(seguir.equals("si")){	
										encontrado = false;
										seguir = "no";
										System.out.println("Codigo: ");
										codigo=scanner.nextLong();
										
										for(int i=0;i<listaCanciones.size();i++) {
											if(listaCanciones.get(i).getId()==codigo) {
												if(listaCanciones.get(i) instanceof Songs) {
													listaClientes.get(cantClientes).addSongToList(codigo);
													canti++;
													encontrado = true;
												}
											}
										}
										
										if(encontrado == false)
										{
											System.err.println("\nAVISO: El codigo ingresado no corresponde a ninguna cancion de la BD");
										}
										
										System.out.println("Desea ingresar el codigo de una cancion descargada? (si/no)");
										seguir = scanner.next();
									}
									
									listaClientes.get(cantClientes).setCanti(canti);
									
									System.out.println("Desea ingresar el codigo de un podcast descargado? (si/no)");
									seguir = scanner.next();
									
									// GUARDAR PODCAST 
									while(seguir.equals("si")){	
										encontrado = false;
										seguir = "no";
										System.out.println("Codigo: ");
										codigo=scanner.nextLong();
										
										for(int i=0;i<listaCanciones.size();i++) {
											if(listaCanciones.get(i).getId()==codigo) {
												if(listaCanciones.get(i) instanceof Podcast) {
													listaClientes.get(cantClientes).addSongToList(codigo);
													canti++;
													encontrado = true;
												}
											}
										}
										
										if(encontrado == false)
										{
											System.err.println("\nAVISO: El codigo ingresado no corresponde a ningun podcast de la BD");
										}
										
										System.out.println("Desea ingresar el codigo de un podcast descargado? (si/no)");
										seguir = scanner.next();
									}
									
									cantClientes++;
								}
								else if(option == 2) {
									canti = 0;
									Clients nueva = new StandardClient (user, pasword, name, lastName, age);
									listaClientes.add(nueva);
									id = listaClientes.get(cantClientes).getNextId();
									listaClientes.get(cantClientes).setId(id);
									
									System.out.println("Desea ingresar el codigo de una cancion descargada? (si/no)");
									seguir = scanner.next();
									
									while(seguir.equals("si")){	
										seguir = "no";
										System.out.println("Codigo: ");
										codigo=scanner.nextLong();
										
										for(int i=0;i<listaCanciones.size();i++) {
											if(listaCanciones.get(i) instanceof Songs) {
												if(listaCanciones.get(i).getId()==codigo) {
													listaClientes.get(cantClientes).addSongToList(codigo);
													canti++;
													encontrado = true;
												}
											}
										}
										
										if(encontrado == false)
										{
											System.err.println("\nAVISO: El codigo ingresado no corresponde a ninguna cancion de la BD");
										}
										
										System.out.println("Desea ingresar el codigo de una cancion descargada? (si/no)");
										seguir = scanner.next();
									}
									
									listaClientes.get(cantClientes).setCanti(canti);
										
									cantClientes++;
								}
							}
							
							else {
								System.err.println("CLIENTE YA EXISTENTE");
							}
					}
				}
				else {
					System.err.println("El numero ingresado no corresponde a una opci�n del men�");
				}
				
				System.out.println("Desea agregar un nuevo cliente (si/no)");
				continuar=scanner.next();
					
				}while(continuar.equals("si"));
	
		return true;
	}
	
	//----------------------------------------------CASE 3----------------------------------------------------------------------------
	
	private static boolean addNewProducer() throws InputMismatchException{
		Scanner scanner = new Scanner (System.in);
		Producer newProducer;
		String name, nickName;
		String continuar, seguir, contenido;
		Long id;
		int option;
		
		System.out.println("1. Ingresar productores"
						 + "\n2. Cargar productores de un archivo de texto");
		option=scanner.nextInt();
		
		if(option==1) {
			do {
				System.out.println("Ingrese nombre del productor: ");
				name = scanner.next();
				System.out.println("Ingrese el Nick Name del productor: ");
				nickName = scanner.next();
				
				newProducer = new Producer(name, nickName);
				
				System.out.println("Tiene contenido producido? (si/no)");
				contenido = scanner.next();
				
				if(contenido.equals("si")) {
					do {
						System.out.println("Ingrese ID del contenido producido");
						id=scanner.nextLong();
						
						for(int i=0;i<listaCanciones.size();i++) {
							if(listaCanciones.get(i).getId()==id) {
								//Guardarle las canciones a cada productor 
								newProducer.setContenido(listaCanciones.get(i));
								listaProductores.put(Producer.getNextId(), newProducer);
								//Se guarda el productor en los creditos de la cancion 
								listaCanciones.get(i).addProductores(newProducer);
							}
						}
						
						System.out.println("Agregar m�s contenido? (si/no)");
						seguir=scanner.next();
						
					}while(seguir.equals("si"));
				}
				
				listaProductores.put(Producer.getNextId(), newProducer);
				
				System.out.println("Desea agregar un nuevo productor?");
				continuar = scanner.next();
			
			} while(continuar.equals("si"));
			return true;
		}
		else if(option==2) {
			try {
				//Creo un objeto de tipo archivo con el nombre de mi archivo
				File file = new File(csvFile);
				//Creo un objeto para leer el archivo
				FileReader fr = new FileReader(file);
				// Utilizo el bufferedReader para leer el archivo
				BufferedReader br = new BufferedReader(fr);
				
				// Se usa para leer linea por lineal el archivo
				String line = "";
				
				//Arreglo para que en cada posicion se guarde la particion de la linea
				String[] words;
				
				// Ciclo para que se lea el archivo mientras haya mas lineas por leer
				while ((line = br.readLine()) != null) {
					// "split" -> Retorna el arreglo con line dividida por el delimiter 
					words = line.split(delimiter);
					
					name = words[0];
					nickName = words[1];
					newProducer = new Producer(name, nickName);
					
					listaProductores.put(Producer.getNextId(), newProducer);
				}
				br.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		else {
			System.err.println("La opcion ingresada no se encuentra en el menu");
			return false;
		}
		return true;
	}
	
	//----------------------------------------------CASE 4----------------------------------------------------------------------------
	private static List<Songs> listSongsByAlbum(String albumBusca) {
		
		cancionesXalbum = new ArrayList <Songs> ();
		
		for(int i=0;i<listaCanciones.size();i++)
		{
			if(listaCanciones.get(i) instanceof Songs) {
				if(((Songs) listaCanciones.get(i)).getAlbum().equals(albumBusca))
				{
					cancionesXalbum.add((Songs) listaCanciones.get(i));
				}
			}
		}	
		return cancionesXalbum;
	}
	
	//----------------------------------------------CASE 5----------------------------------------------------------------------------
	private static List<Songs> listSongsByArtist(String generoBusca) {
		
		artistasXgenero = new ArrayList <Songs> ();
		
		for(int i=0;i<listaCanciones.size();i++)
		{
			if(listaCanciones.get(i) instanceof Songs) {
				if(((Songs) listaCanciones.get(i)).getGenre().equals(generoBusca))
				{
					artistasXgenero.add((Songs) listaCanciones.get(i));
				}
			}	
		}
		return artistasXgenero;
	}
	
	//----------------------------------------------CASE 6----------------------------------------------------------------------------
	private static boolean addSongToList(String nom, Long newCod) throws InputMismatchException {
		
		boolean encontrado = false;
		boolean existe = false;
		
		for(int i=0; i<listaClientes.size();i++) {
			if(listaClientes.get(i).getUser().equals(nom)){ 
				existe = true;
							
				for(int j=0;j<listaCanciones.size();j++){
					if(listaCanciones.get(j).getId()==newCod && listaCanciones.get(j) instanceof Songs) {
						
							if(listaClientes.get(i).addSongToList(newCod)) {
								System.out.println("La cancion fue agregada correctamente");
								encontrado = true;
								listaClientes.get(i).setCanti(1);
							}
							else {
								System.err.println("AVISO: La cancion no pudo ser agregada a la lista");
							}
						
					}
				}
			}
		}
		
		if(existe == true && encontrado == true) {
			for(int i=0; i<listaCanciones.size();i++) {
				if(listaCanciones.get(i).getId()== newCod){
					existe = true;
					
					System.out.println("El nuevo numero de descargas para la cancion con codigo " + newCod + " es: " + listaCanciones.get(i).addDownload());
				}
			}
			return true;
		}
		
		if(existe == false){
			System.err.println("\nAVISO: El usuario ingresado no se encuentra en la BD");
			return false;
		}
		
		if(encontrado == false) {
			System.err.println("\nAVISO: El codigo ingresado no corresponde a ninguna cancion de la BD");
		}
		
		return false;	
	}
	
	//----------------------------------------------CASE 7----------------------------------------------------------------------------
	private static boolean addPodcastToList (String nom, Long newCod) throws InputMismatchException {
			
		boolean encontrado = false;
		boolean existe = false;
		boolean premium = false;

		for(int i=0; i<listaClientes.size();i++) {
			if(listaClientes.get(i).getUser().equals(nom)){ 
				existe = true;
				if(listaClientes.get(i) instanceof PremiumClient)
				{
					premium = true;
					for(int j=0;j<listaCanciones.size();j++){
						if(listaCanciones.get(j).getId()==newCod) {
							if(listaCanciones.get(j) instanceof Podcast)
							{
								if(listaClientes.get(i).addSongToList(newCod)) {
									System.out.println("El podcast fue agregado correctamente");
									encontrado = true;
									listaClientes.get(i).setCanti(1);
								}
								else {
									System.err.println("AVISO: El podcast no pudo ser agregado a la lista");
								}
							}
							else{
								System.out.println("El codigo ingresado corresponde a una CANCI�N");
							}
						}
					}
				}
			}
		}
		
		if(existe == true && encontrado == true) {
			for(int i=0; i<listaCanciones.size();i++) {
				if(listaCanciones.get(i).getId()== newCod){
					existe = true;
					
					System.out.println("El nuevo numero de descargas para el podcast con codigo " + newCod + " es: " + listaCanciones.get(i).addDownload());
				}
			}
			return true;
		}
		if(premium == false) {
			System.err.println("El plan del cliente es STANDARD, por lo tanto no puede descargar ningun podcast");
		}
		
		if(existe == false){
			System.err.println("\nAVISO: El usuario ingresado no se encuentra en la BD");
			return false;
		}
		
		if(encontrado == false) {
			System.err.println("\nAVISO: El codigo ingresado no corresponde a ningun podcast de la BD");
		}
		
		return false;	
	}
	
	
	//----------------------------------------------CASE 8----------------------------------------------------------------------------
	public static void imprimirClientes() {
		
		ArrayList <Playlist> aux = new ArrayList <Playlist> ();
		List <Content> aux2 = new ArrayList <Content> ();
		Playlist aux3;
		
		System.out.println("LISTA DE CLIENTES");
		for(int i=0; i<listaClientes.size();i++) {
			System.out.println("ID: " + listaClientes.get(i).getId());
			System.out.println("User: "+listaClientes.get(i).getUser());
			System.out.println("Nombre: "+listaClientes.get(i).getName());
			System.out.println("Apellido: "+listaClientes.get(i).getLastName());
			System.out.println("Contrase�a: "+listaClientes.get(i).getPasword());
			System.out.println("Edad: "+listaClientes.get(i).getAge());
			
			if(listaClientes.get(i).getSizeSongs()>0)
			{
				System.out.println("Canciones descargadas: ");
				for(int j=0;j<listaClientes.get(i).getSizeSongs();j++)
				{
					for(int h=0;h<listaCanciones.size();h++) {
						if(listaCanciones.get(h) instanceof Songs)
							if(listaClientes.get(i).getSongs(j)==listaCanciones.get(h).getId()) {
								System.out.println(" - " + listaCanciones.get(h).getName());
							}
					}
					
				}
				if(listaClientes.get(i) instanceof PremiumClient) {
					System.out.println("Podcast descargados: ");
					for(int j=0;j<listaClientes.get(i).getSizeSongs();j++)
					{
						for(int h=0;h<listaCanciones.size();h++) {
							if(listaCanciones.get(h) instanceof Podcast)
								if(listaClientes.get(i).getSongs(j)==listaCanciones.get(h).getId()) {
									System.out.println(" - " + listaCanciones.get(h).getName());
								}
						}
					}
				}
			}
			else {
				System.out.println("Aun no tiene canciones o podcast descargados");
			}
			
			if(listaClientes.get(i) instanceof PremiumClient) {
				System.out.println("Playlists creadas por el usuario: ");
				aux = ((PremiumClient) listaClientes.get(i)).getPlaylist();
				
				// Comprobar si tiene playlists creadas 
				if(aux.size()>0) {
					// Recorrer el arreglo con cada playlist
					for(int a=0;a<aux.size();a++) {
						System.out.println("Nombre: " + aux.get(a).getName());
						//Guardar la lista con los codigos de las canciones de esta playlist
						if(aux.get(a).getPlaylist()!=null) {
							aux2 = aux.get(a).getPlaylist();
							//Comprobar si la playlist tiene canciones 
							if(aux2.size()>0)
							{
								for(int f=0;f<aux2.size();f++) {
									System.out.println("- "+aux2.get(f).getName());
								}
							}
						}
						else {
							System.out.println("- Ninguna");
						}
					}
				}
				else {
					System.out.println("- Ninguna");
				}
			}
			else {
				System.out.println("Playlists creadas por el usuario: ");
				//Compruebo si hay algo en la variable playlist del usuario
				if(((StandardClient)listaClientes.get(i)).getPlaylist()!=null)
				{
					aux3 = ((StandardClient)listaClientes.get(i)).getPlaylist();
					System.out.println("Nombre: " + aux3.getName());
					// Compruebo si tiene alguna cancion guardada
					if(aux3.getPlaylist()!=null)
					{
						aux2 = aux3.getPlaylist();
						
						if(aux2.size()>0) {
							
							for(int d=0;d<aux2.size();d++) {
								System.out.println("- " + aux2.get(d).getName());
							}	
						}
						else {
							System.out.println("- Ninguna");
						}
					}
					else{
						System.out.println("- Ninguna");
					}
				}
				else {
					System.out.println("- Ninguna");
				}		
			}
			System.out.println("\n");
		}
	}
	
	//----------------------------------------------CASE 9----------------------------------------------------------------------------
	public static void imprimirCanciones() {
		
		System.out.println("\nLISTA DE CANCIONES");
			for(int i=0; i<listaCanciones.size();i++) {
				if(listaCanciones.get(i) instanceof Songs)
				{
					System.out.println("\nID: " + listaCanciones.get(i).getId());
					System.out.println("Nombre: " + listaCanciones.get(i).getName());
					System.out.println("Artista: " + ((Songs) listaCanciones.get(i)).getArtist());
					System.out.println("Genero: " + ((Songs) listaCanciones.get(i)).getGenre());
					System.out.println("Album: " + ((Songs) listaCanciones.get(i)).getAlbum());
					System.out.println("Cant. Descargas: " + listaCanciones.get(i).getNumberOfDownloads());
					System.out.println("Duracion: " + listaCanciones.get(i).getDuration() + " min");
					System.out.println("Productores: ");
					aux = listaCanciones.get(i).showCredits();
					if(aux.size()>0) {
						for( Iterator it = aux.iterator(); it.hasNext();) { 
						    Producer x = (Producer)it.next();
						    System.out.println(x.getName() + " : " + x.getNickname());
						}
					}
					else {
						System.out.println("-Ninguno");
					}
				}
			}
		
		System.out.println("\nLISTA DE PODCAST");
			for(int i=0; i<listaCanciones.size();i++) {
				if(listaCanciones.get(i) instanceof Podcast)
				{
					System.out.println("\nID: " + listaCanciones.get(i).getId());
					System.out.println("Nombre: " + listaCanciones.get(i).getName());
					System.out.println("Autor: " + ((Podcast) listaCanciones.get(i)).getAutor());
					System.out.println("Categoria: " + ((Podcast) listaCanciones.get(i)).getCategory());
					System.out.println("Cant. Descargas: " + listaCanciones.get(i).getNumberOfDownloads());
					System.out.println("Duracion: " + listaCanciones.get(i).getDuration() + " min");
					System.out.println("Productores: ");
					aux = listaCanciones.get(i).showCredits();
					if(aux.size()>0) {
						for( Iterator it = aux.iterator(); it.
								hasNext();) { 
						    Producer x = (Producer)it.next();
						    System.out.println(x.getName() + " : " + x.getNickname());
						}
					}
					else {
						System.out.println("-Ninguno");
					}
				}
			}
	}

	//----------------------------------------------CASE 10----------------------------------------------------------------------------
    private static void imprimirProductores() {
    	
    	List <Content> contenido = new ArrayList <Content>();
    	
    	Iterator it = listaProductores.keySet().iterator();
    	while(it.hasNext()){
    	  Long key = (Long) it.next();
    	  System.out.println("\nId: " + key + " -> " + listaProductores.get(key).getNickname());
    	  contenido = listaProductores.get(key).getContenido();
    	  if(contenido.size()>0) {
    		  System.out.println("Contenido producido: ");
    		  for(int i=0;i<contenido.size();i++) {
    			  System.out.println("-" + contenido.get(i).getName());
    		  }
    	  }
    	  
    	}
    }
	
  //----------------------------------------------CASE 11----------------------------------------------------------------------------
	//Hacer una excepcion para que la palabra sea mayor a 3 caracteres 
	private static void newOption() throws LongitudErronea{
			
			Scanner scanner = new Scanner (System.in);
			String palabraClave, continuar = "no";
			int opcion;
			boolean encontrado;
			
			do {
				encontrado = false;
				System.out.println("Ingrese el parametro de busqueda:\n"
						+ "1. Filtrar solo por album\n"
						+ "2. Filtrar solo por artista\n"
						+ "3. Filtrar por album y artista");
				opcion = scanner.nextInt();
				System.out.println("Ingrese la palabra clave a buscar en la BD de canciones ");
				palabraClave = scanner.next();
				
				if(palabraClave.length()<3) {
					throw new LongitudErronea ();
				}
				else {
					
					switch(opcion) {
					case 1:
						
						for(int i=0;i<listaCanciones.size();i++) {
							if(listaCanciones.get(i) instanceof Songs) {
								if(((Songs) listaCanciones.get(i)).getAlbum().contains(palabraClave)) {
									System.out.println("ID: " + listaCanciones.get(i).getId());
									System.out.println("Nombre: " + listaCanciones.get(i).getName());
									System.out.println("Artista: " + ((Songs) listaCanciones.get(i)).getArtist());
									System.out.println("Genero: " + ((Songs) listaCanciones.get(i)).getGenre());
									System.out.println("Album: " + ((Songs) listaCanciones.get(i)).getAlbum());
									System.out.println("Cant. Descargas: " + listaCanciones.get(i).getNumberOfDownloads());
									System.out.println("Duracion: " + listaCanciones.get(i).getDuration() + "min\n");
									encontrado = true;
								}
							}
						}
						break;
					case 2:
				
						for(int i=0;i<listaCanciones.size();i++) {
							if(listaCanciones.get(i) instanceof Songs) {
								if(((Songs) listaCanciones.get(i)).getArtist().contains(palabraClave)) {
									System.out.println("ID: " + listaCanciones.get(i).getId());
									System.out.println("Nombre: " + listaCanciones.get(i).getName());
									System.out.println("Artista: " + ((Songs) listaCanciones.get(i)).getArtist());
									System.out.println("Genero: " + ((Songs) listaCanciones.get(i)).getGenre());
									System.out.println("Album: " + ((Songs) listaCanciones.get(i)).getAlbum());
									System.out.println("Cant. Descargas: " + listaCanciones.get(i).getNumberOfDownloads());
									System.out.println("Duracion: " + listaCanciones.get(i).getDuration() + "min\n");
									encontrado = true;
								}
							}
						}
						break;
						
					case 3:
						
						for(int i=0;i<listaCanciones.size();i++) {
							if(listaCanciones.get(i) instanceof Songs) {
								if(((Songs) listaCanciones.get(i)).getArtist().contains(palabraClave)||((Songs)listaCanciones.get(i)).getAlbum().contains(palabraClave)) {
									System.out.println("ID: " + listaCanciones.get(i).getId());
									System.out.println("Nombre: " + listaCanciones.get(i).getName());
									System.out.println("Artista: " + ((Songs) listaCanciones.get(i)).getArtist());
									System.out.println("Genero: " + ((Songs) listaCanciones.get(i)).getGenre());
									System.out.println("Album: " + ((Songs) listaCanciones.get(i)).getAlbum());
									System.out.println("Cant. Descargas: " + listaCanciones.get(i).getNumberOfDownloads());
									System.out.println("Duracion: " + listaCanciones.get(i).getDuration() + "min\n");
									encontrado = true;
								}
							}
						}
						break;
					
					default:
						System.err.println("AVISO: El numero ingresado no corresponde a ninguna de las opciones" );
						break;
					}
				}
				
				if(!encontrado) {
					System.err.println("AVISO: No se encontraron resultados para la busqueda");
				}
				
				System.out.println("Desea realizar una nueva busqueda? (si/no)");
				continuar = scanner.next();
				
			}while(continuar.equals("si"));
	}
	
// -------------------------------------------- case 12 --------------------------------------------------------------------------------------------------------------
	
	private static boolean eliminar(Long newCod, String nom) {
		
		boolean existe = false;
		boolean encontrado = false;
		
		 for(int i=0;i<listaClientes.size();i++) {
			 if(listaClientes.get(i).getUser().equals(nom)) {
				 existe = true;
				 for(int j=0;j<listaClientes.get(i).getSizeSongs();j++)
				 {
					 if(listaClientes.get(i).getSongs(j)==newCod) {
						 encontrado = true;
						 listaClientes.get(i).deleteSong(j);
						 for(int h=0;h<listaCanciones.size();h++)
						 {
							 if(listaCanciones.get(h).getId()==newCod)
							 {
								 if(listaCanciones.get(h) instanceof Songs) {
									 System.out.println("El nuevo numero de descargas para la cancion con codigo" + newCod + " es: " +  listaCanciones.get(h).deleteDownload());
								 }
								 else if(listaCanciones.get(h) instanceof Podcast) {
									 System.out.println("El nuevo numero de descargas para el podcast con codigo" + newCod + " es: " +  listaCanciones.get(h).deleteDownload());
								 }
							 }
						 }
					 }
				 }
			 }
		 }
		 if(existe == false)
		 {
			 System.err.println("AVISO: El ususario ingresado no existe en la BD");
		 }
		 if(existe == true && encontrado == false)
		 {
			 System.out.println("AVISO: El contenido que desea eliminar no fue encontrado en la lista del usuario");
		 }
		 if(existe == true && encontrado == true) {
			 return true;
		 }
		
		 return false;
	}
	
// -------------------------------------------- case 13 --------------------------------------------------------------------------------------------------------------
	
	private static void guardarArchivo(String fileNameSong, String fileNameClients, String fileNameProducer) throws FileNotFoundException, IOException {

		File file = new File(fileNameSong);

        try (FileOutputStream fos = new FileOutputStream(file);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {

			for(Content contenido : listaCanciones) {
                oos.writeObject(contenido);
            }
        }
        
        File file2 = new File(fileNameClients);

        try (FileOutputStream fos = new FileOutputStream(file2);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {

			for(Clients cliente : listaClientes) {
                oos.writeObject(cliente);
            }
        }
        
        File file3 = new File(fileNameProducer);

        try (FileOutputStream fos = new FileOutputStream(file3);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
        	
        	oos.writeObject(listaProductores);
        	oos.close();
        	 
        } 
	}
	
	// -------------------------------------------- case 14 y 15 --------------------------------------------------------------------------------------------------------------

	private static List<Content> imprimirBackUp(String fileName) throws IOException, ClassNotFoundException {
		File file = new File(fileName);
        List<Content> songs = new ArrayList <Content>();
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while(true) {
            	Content song = (Content) ois.readObject();
                songs.add(song);
            }
        }catch (EOFException e) {
            return songs;
        }
	}
	
	private static List<Clients> imprimirBackUp2(String fileName) throws IOException, ClassNotFoundException {
		File file = new File(fileName);
		
        List<Clients> clientes = new ArrayList<Clients>();
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while(true) {
            	Clients song = (Clients) ois.readObject();
            	clientes.add(song);
            }
        }catch (EOFException e) {
            return clientes;
        }
	}
	
	private static Map<Long,Producer> imprimirBackUp3(String fileName) throws IOException, ClassNotFoundException {
		File file = new File(fileName);
		Long key;
        Map <Long,Producer> productores = new HashMap <Long,Producer>();
        try (FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
        	
        	productores = (HashMap<Long,Producer>)ois.readObject();
        	return productores;
        	
           }catch (EOFException e) {
               System.out.println("ERROR");
           }
		return productores;
	}
	
	private static boolean crearPlaylist() {
		String user, name;
		int cod;
		boolean existeC = false, existeS = false;
		Scanner sc = new Scanner (System.in);
		String continuar, seguir;
		List <Content> playlist = new ArrayList <Content>();
		
		//Crear playlist para un usuario
		System.out.println("Usuario: ");
		user = sc.next();
		
		for(int i=0; i<listaClientes.size();i++) {
			if(listaClientes.get(i).getUser().equals(user)) {
				existeC = true;
				System.out.println("Ingrese el nombre de la nueva playlist:");
				name = sc.next();
				
				do {
					System.out.println("Codigo de la canci�n o podcast que desea agregar: ");
					cod = sc.nextInt();
					for(Content s: listaCanciones) {
						if(s.getId()==cod) {
							existeS=true;
							playlist.add(s);
						}
					}
					
					System.out.println("Desea agregar algo m�s? (si/no)");
					continuar = sc.next();
					
				}while(continuar.equals("si")) ;
				
				if(listaClientes.get(i) instanceof PremiumClient) {
					((PremiumClient)listaClientes.get(i)).setPlaylist(name, playlist);
				}
				else {
					if(((StandardClient) listaClientes.get(i)).isTienePlaylist()){
						System.out.println("El usuario ya posee una playlist creada. Desea eliminarla y crear una nueva? (si/no)");
						seguir = sc.next();
						
						if(seguir.equals("si")) {
							((StandardClient)listaClientes.get(i)).setPlaylist(name, playlist);
						}
						else
						{
							return false;
						}
					}
					else {
						((StandardClient)listaClientes.get(i)).setPlaylist(name, playlist);
					}
				}
			}
		}
		
		if(existeC==false)
		{
			System.out.println("El usuario ingresado no existe en la base de datos");
			return false;
		}
		else if(existeS == false) {
			System.out.println("El codigo ingresado no corresponde a ning�n contenido en la base de datos");
			return false;
		}		
		return true;
	}
	
	public static boolean addContentToAPlaylist() {
		Scanner sc = new Scanner(System.in);
		String user, Pname;
		Long id;
		boolean existeUser = false, existeContent = false;
		
		System.out.println("Usuario: ");
		user=sc.next();
		
		for(int i=0;i<listaClientes.size();i++) {
			if(listaClientes.get(i).getUser().equals(user)) {
				existeUser=true;
				if(listaClientes.get(i) instanceof PremiumClient) {
					System.out.println("Ingrese nombre de la playlist");
					Pname=sc.next();
					System.out.println("Ingrese ID del contenido: ");
					id=sc.nextLong();
					for(int j=0;j<listaCanciones.size();j++) {
						if(listaCanciones.get(j).getId()==id) {
							existeContent = true;
							if(((PremiumClient) listaClientes.get(i)).addContentToPlaylist(Pname,listaCanciones.get(j))) {
								System.out.println("Contenido agregado exitosamente!");
							}
							else {
								System.out.println("El nombre de la playlist no fue encontrado");
							}
						}
					}
				}
				else {
					//solamente agregar a la playlist que ya tiene
					System.out.println("Ingrese ID del contenido: ");
					id=sc.nextLong();
					for(int j=0;j<listaCanciones.size();j++) {
						existeContent = true;
						if(listaCanciones.get(j).getId()==id) {
							if(((StandardClient)listaClientes.get(j)).getPlaylist()!=null) {
								((StandardClient) listaClientes.get(i)).addContentToPlaylist(listaCanciones.get(j));
							}
							else {
								System.out.println("El usuario no tiene ninguna playlist");
								return false;
							}
						}
					}
				}
			}
		}
		if(existeUser==false) {
			System.out.println("El usuario ingresado no existe en la base de datos");
		}
		else if (existeContent==false){
			System.out.println("El Id ingresado no existe en la base de datos");
		}
		return true;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	
	public static boolean addContentToAProducer() {
		Scanner sc = new Scanner(System.in);
		boolean existe = false, contenido = false;
		
		System.out.println("Ingrese codigo del productor:");
		int id = sc.nextInt();
		
		Iterator it = listaProductores.keySet().iterator();
    	while(it.hasNext()){
    	  Long key = (Long) it.next();
    	  if(key == id)
    	  {
    		  existe = true;
    		  System.out.println("Ingrese codigo de la canci�n: ");
    		  int id2 = sc.nextInt();
    		  
    		  for(int i=0;i<listaCanciones.size();i++) {
    			  if(listaCanciones.get(i).getId()==id2) {
    				  contenido = true;
    				  listaProductores.get(key).setContenido(listaCanciones.get(i));
    				  listaCanciones.get(i).addProductores(listaProductores.get(key));
    			  }
    		  } 
    	  }
    	}
    	if(existe==false) {
    		System.out.println("El productor con ese ID no existe");
    		return false;
    	}
    	else if(contenido = false) {
    		System.out.println("El Id no corresponde a ningpun contenido de la base de datos");
    		return false;
    	}
    	return true;
	}
}

	


