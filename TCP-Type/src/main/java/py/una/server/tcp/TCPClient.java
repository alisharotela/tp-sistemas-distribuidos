package py.una.server.tcp;


import java.io.*;
import java.net.*;
import py.una.entidad.Nis;
import py.una.entidad.Peticion;
import py.una.entidad.NisJSON;
import py.una.entidad.PeticionJSON;

public class TCPClient {

    public static void main(String[] args) throws IOException {

        Socket unSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            unSocket = new Socket("localhost", 4444);
            // enviamos nosotros
            out = new PrintWriter(unSocket.getOutputStream(), true);

            //viene del servidor
            in = new BufferedReader(new InputStreamReader(unSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error de I/O en la conexion al host");
            System.exit(1);
        }

        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
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
        System.out.print("Operacion: ");
        
        int operacion = Integer.parseInt(stdIn.readLine());
        Peticion peticion = new Peticion();
        
        if (operacion == 1){
            System.out.println("Ingrese el id del nis");
            int id = Integer.parseInt(stdIn.readLine());
            System.out.println("Ingrese el consumo");
            long consumo = Long.parseLong(stdIn.readLine());
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
        else{
            System.out.println("Operacion no valida");
            System.exit(0);
        }

        /*while ((fromServer = in.readLine()) != null) {
            System.out.println("Servidor: " + fromServer);
            if (fromServer.equals("Bye")) {
                break;
            }

            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println("Cliente: " + fromUser);

                //escribimos al servidor
                out.println(fromUser);
            }
        }*/

        out.close();
        in.close();
        stdIn.close();
        unSocket.close();
    }
}