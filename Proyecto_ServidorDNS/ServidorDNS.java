package Proyecto_ServidorDNS;

public class ServidorDNS {
	public static void main(String[] args) {
		Servidor DNS= new Servidor(53);
		DNS.escucharDNS();
	}
}
