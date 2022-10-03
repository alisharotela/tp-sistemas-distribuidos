package py.una.server.tcp;

import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.io.*;
import py.una.entidad.Logger;
import py.una.entidad.Nis;
import py.una.entidad.NisJSON;
import py.una.entidad.Peticion;
import py.una.entidad.PeticionJSON;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TCPServerHilo extends Thread {

    private Socket socket = null;
    
    TCPMultiServer servidor;
    List<Nis> usuarios = new ArrayList<Nis>(); //almacenar una lista de usuarios (no se utiliza, se deja para que el alumno lo utilice)
    List<Logger> logs = new ArrayList<Logger>();
    
    public TCPServerHilo(Socket socket, TCPMultiServer servidor ) {
        super("TCPServerHilo");
        this.socket = socket;
        this.servidor = servidor;
    }

    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));
            //out.println("Bienvenido!");
            String inputLine, outputLine;
            Integer NisNro=0;
            inputLine = in.readLine();
            boolean encuentra= false;
            
            
            //
            JSONObject respuestaJson = new JSONObject();
            while ((inputLine = in.readLine()) != null) {
            	System.out.println("esperando tcp");
            	Peticion peticion = PeticionJSON.stringObjeto(inputLine);
                Integer tipoOperacion = peticion.getOperacion();
            	Peticion respuesta = new Peticion(0,"","",0);

	            if (tipoOperacion == 1){
	                // Registrar consumo
	            	try {
		                Nis nis = NisJSON.stringObjeto(inputLine);
		                usuarios.add(nis);
		                respuesta.setMensaje("OK");
		                respuesta.setEstado(0);
		                respuesta.setOperacion(1);
		                respuesta.setDatos(inputLine);
		                System.exit(0);
	            	}
	            	catch(Exception e) {
	            		respuesta.setMensaje(e.toString());
		                respuesta.setEstado(-1);
		                respuesta.setOperacion(1);
	            	}
	            }
	            else if (tipoOperacion == 2){
	            	try {
		                respuesta.setMensaje("OK");
		                respuesta.setEstado(0);
		                respuesta.setOperacion(2);
	            	}catch (Exception e){
	            		respuesta.setMensaje(e.toString());
		                respuesta.setEstado(-1);
		                respuesta.setOperacion(2);
	            	}
	            }
	            else if (tipoOperacion == 3){
	                // Listar nis activos
	            	try {
	                	String datosNis = "[";
		                for (Nis nis : usuarios){
		                    if (nis.getEsActivo()){
		                        datosNis += NisJSON.objetoString(nis) + ",";
		                    }
		                }
		                datosNis += "]";
		                respuesta.setDatos(datosNis);
		                respuesta.setMensaje("OK");
		                respuesta.setEstado(0);
		                respuesta.setOperacion(3);
		                outputLine = respuestaJson.toJSONString();
	            	}catch (Exception e) {
	            		respuesta.setMensaje(e.toString());
		                respuesta.setEstado(-1);
		                respuesta.setOperacion(3);
	            	}
	            }
	            else if (tipoOperacion == 4){
	                // Listar nis inactivos
	            	try {
		                String datosNis = "[";
		                for (Nis nis : usuarios){
		                    if (!nis.getEsActivo()){
		                        datosNis += NisJSON.objetoString(nis)+",";
		                    }
		                }
		                datosNis += "]";
		                respuesta.setDatos(datosNis);
		                respuesta.setMensaje("OK");
		                respuesta.setEstado(0);
		                respuesta.setOperacion(4);
	            	} catch(Exception e) {
	            		respuesta.setMensaje(e.toString());
		                respuesta.setEstado(-1);
		                respuesta.setOperacion(4);
	            	}
	            }
	            else{
	                respuesta.setDatos("Operacion no valida");
	                respuesta.setEstado(1);
	            }
            }
            String ip=socket.getRemoteSocketAddress().toString();
            int puerto= socket.getPort();
            String ip2=socket.getLocalAddress().toString();
            int puerto2= socket.getLocalPort();
            String puertoAux1=String.valueOf(puerto);
            String puertoAux2=String.valueOf(puerto2);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String fecha = dateFormat.format(date);
         // guarda un registro de las operaciones realizadas por los clientes
           // Logger log = new Logger(ip+puertoAux1, ip2+puertoAux2, fecha, tipoOperacion);
            //logs.add(log);
            out.close();
            in.close();
            socket.close();
            System.out.println("Operación Exitosa");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

