package py.una.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.una.entidad.Logger;
import py.una.entidad.Nis;
import py.una.entidad.NisJSON;
import py.una.entidad.Peticion;
import py.una.entidad.PeticionJSON;

public class UDPServer {
	
	
    public static void main(String[] a){

        // Variables
        int puertoServidor = 9876;
        // Arraylist de datos
        List<Nis> datos = new ArrayList<Nis>();
        List<Logger> logs = new ArrayList<Logger>();
        
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			System.out.println("Servidor Sistemas Distribuidos - UDP ");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

			
            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);


                System.out.println("Esperando a algun cliente... ");

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println("________________________________________________");
                String puerto = String.valueOf(receivePacket.getPort());
                String ip = receivePacket.getAddress().getHostAddress();
                System.out.println("Aceptamos un paquete de " + ip + ":" + puerto);

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData());
                datoRecibido = datoRecibido.trim();
                System.out.println("DatoRecibido: " + datoRecibido );
                
                Peticion peticion = PeticionJSON.stringObjeto(datoRecibido);
                Integer tipoOperacion = peticion.getOperacion();

                Peticion respuesta = new Peticion(0,"","",0);
                // 1 registrar_consumo
                // 2 informar conectividad
                // 3 listar nis activos
                // 4 listar nis inactivos
                // 5 Apagar server
                if (tipoOperacion == 1){
                    // Registrar consumo
                    Nis nis = NisJSON.stringObjeto(peticion.getDatos());
                    datos.add(nis);
                    respuesta.setMensaje("OK");
                    respuesta.setEstado(0);
                }
                else if (tipoOperacion == 2){
                    respuesta.setMensaje("OK");
                    respuesta.setEstado(0);
                }
                else if (tipoOperacion == 3){
                    // Listar nis activos
                    String datosNis = "[";
                    for (Nis nis : datos){
                        if (nis.getEsActivo()){
                            datosNis += NisJSON.objetoString(nis) + ",";
                        }
                    }
                    datosNis += "]";
                    respuesta.setDatos(datosNis);
                    respuesta.setEstado(0);
                }
                else if (tipoOperacion == 4){
                    // Listar nis inactivos
                    String datosNis = "[";
                    for (Nis nis : datos){
                        if (!nis.getEsActivo()){
                            datosNis += NisJSON.objetoString(nis)+",";
                        }
                    }
                    datosNis += "]";
                    respuesta.setDatos(datosNis);
                    respuesta.setEstado(0);
                }
                else if (tipoOperacion == 5){
                    // Apagar server
                    respuesta.setDatos("OK");
                    respuesta.setEstado(0);
                    serverSocket.close();
                    System.exit(0);
                }
                else{
                    respuesta.setDatos("Operacion no valida");
                    respuesta.setEstado(1);
                }

                sendData = PeticionJSON.objetoString(respuesta).getBytes();
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);

                String puerto2 = String.valueOf(sendPacket.getPort());
                String ip2 = sendPacket.getAddress().getHostAddress();

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String fecha = dateFormat.format(date);

                // guarda un registro de las operaciones realizadas por los clientes
                Logger log = new Logger(ip+puerto, ip2+puerto2, fecha, tipoOperacion);
                logs.add(log);
            }
            
        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }
}  

