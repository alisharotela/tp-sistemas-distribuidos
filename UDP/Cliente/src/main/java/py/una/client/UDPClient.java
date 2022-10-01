package py.una.client;


import java.io.*;
import java.net.*;

import py.una.entidad.Nis;
import py.una.entidad.Peticion;
import py.una.entidad.NisJSON;
import py.una.entidad.PeticionJSON;

public class UDPClient {

    public static void main(String a[]) throws Exception  {

        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (a.length > 0) {
            direccionServidor = a[0];
        }

        int puertoServidor = 9876;
        
        try {

            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor +  " via UDP...");

            // 1 registrar_consumo
            // 2 informar conectividad
            // 3 listar nis activos
            // 4 listar nis inactivos
            // 5 Apagar server
            System.out.println("Ingrese la operacion que sea realizar");
            System.out.println("1 registrar consumo");
            System.out.println("2 informar conectividad");
            System.out.println("3 listar nis activos");
            System.out.println("4 listar nis inactivos");
            System.out.println("5 Apagar server");
            System.out.print("Operacion: ");
            
            int operacion = Integer.parseInt(inFromUser.readLine());
            Peticion peticion = new Peticion();
            
            if (operacion == 1){
                System.out.println("Ingrese el id del nis");
                int id = Integer.parseInt(inFromUser.readLine());
                System.out.println("Ingrese el consumo");
                long consumo = Long.parseLong(inFromUser.readLine());
                Nis nis = new Nis();
                nis.setId(id);
                nis.setConsumo(consumo);
                nis.setEsActivo(true);
                peticion.setOperacion(1);
                peticion.setDatos(NisJSON.objetoString(nis));
            }
            else if(operacion == 2){
                peticion.setOperacion(2);
            }
            else if(operacion == 3){
                peticion.setOperacion(3);
            }
            else if(operacion == 4){
                peticion.setOperacion(4);
            }
            else if(operacion == 5){
                peticion.setOperacion(5);
            }
            else{
                System.out.println("Operacion no valida");
                System.exit(0);
            }

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            sendData = PeticionJSON.objetoString(peticion).getBytes();

            DatagramPacket sendPacket = new DatagramPacket(
                sendData, sendData.length, IPAddress, puertoServidor);
            
            clientSocket.send(sendPacket);
            clientSocket.setSoTimeout(5000);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try{

                clientSocket.receive(receivePacket);
                String respuesta = new String(receivePacket.getData()).trim();
                System.out.println("Respuesta del servidor: " + respuesta);
            }
            catch(SocketTimeoutException e){
                System.out.println("No se pudo conectar con el servidor");
                System.exit(0);
            }

                // // send data.operacion = 2
                // byte[] sendData = new byte[1024];
                // Datos d = new Datos();
                // d.setOperacion(2);
                // String datoPaquete = DatosJSON.objetoString(d);
                // sendData = datoPaquete.getBytes();
                // DatagramPacket sendPacket =
                // new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);

                // clientSocket.send(sendPacket);

                // byte[] receiveData = new byte[1024];
    
                // DatagramPacket receivePacket =
                //         new DatagramPacket(receiveData, receiveData.length);
    
                // System.out.println("Esperamos si viene la respuesta.");
    
                // //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
                // clientSocket.setSoTimeout(10000);
    
                // try {
                //     // ESPERAMOS LA RESPUESTA, BLOQUENTE
                //     clientSocket.receive(receivePacket);
    
                //     String respuesta = new String(receivePacket.getData());
                //     Datos presp = DatosJSON.stringObjeto(respuesta.trim());
                    
                //     InetAddress returnIPAddress = receivePacket.getAddress();
                //     int port = receivePacket.getPort();
    
                //     System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);
                    
                //     System.out.println("El vehículo de monto mayor es:" + presp.getMarca() + " " + presp.getChapa() + " " + presp.getMonto());
                    
    
                // } catch (SocketTimeoutException ste) {
    
                //     System.out.println("TimeOut: El paquete udp se asume perdido.");
                // }


            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
} 

