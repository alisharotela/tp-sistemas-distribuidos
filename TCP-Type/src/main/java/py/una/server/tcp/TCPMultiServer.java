package py.una.server.tcp;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


import py.una.entidad.Nis;
//import py.una.entidad.NisJSON;
public class TCPMultiServer {

	//variables compartidas
	int puerto = 4444;
	boolean listening = true;
	
	List<TCPServerHilo> hilosClientes = new ArrayList<>(); //almacenar los hilos (no se utiliza en el ejemplo, se deja para que el alumno lo utilice)
	
    public void ejecutar() throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("No se puede abrir el puerto: 4444.");
            System.exit(1);
        }
        System.out.println("Puerto abierto: 4444.");

        while (listening) {
        	
        	TCPServerHilo hilo = new TCPServerHilo(serverSocket.accept(), this);
            hilosClientes.add(hilo);
            hilo.start();
        }

        serverSocket.close();
    }
    
    public static void main(String[] args) throws IOException {
    	
    	TCPMultiServer tms = new TCPMultiServer();
    	tms.ejecutar();
    	
    }
}
