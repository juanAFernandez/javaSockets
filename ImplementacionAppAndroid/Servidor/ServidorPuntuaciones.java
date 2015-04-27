import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.*;


public class ServidorPuntuaciones {


    private static String[] extraer (String cadena){
        String resultado[]= cadena.split(" ");
        return resultado;
    }


    public static void main(String[] args) throws IOException, SQLException {

        System.out.println("START SEVER");

        baseDatos miBaseDatos = new baseDatos();

        //Alias y puntuación temporales
        String datosTemporales[];

        try{

            //Apertura del socket:
            ServerSocket s = new ServerSocket(1234);

            //Inicio de bucle infinito de ejecución del servidor
            while (true){

                //Aceptamos la conexiones entrantes
                Socket cliente = s.accept();

                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter salida = new PrintWriter( new OutputStreamWriter(cliente.getOutputStream()),true);

                //Recibimos los datos:
                String datos = entrada.readLine();


                //Si recibimos el string PUNTUACIONES el cliente quiere que le devolvamos la lista de las puntuaciones.
                if(datos.equals("PUNTUACIONES")){
                    System.out.println("Recibido string: PUNTUACIONES  ");
                        //Consultamos la base de datos y enviamos el resultado al cliente.
                        salida.println(miBaseDatos.consultarPuntuaciones());


                //Si no es así lo que nos está enviando es su propia puntuación.
                }else{
                    System.out.println("Grabando en base de datos las  puntuaciones");

                    //Separamos el string en alias y puntuacion.
                    datosTemporales=extraer(datos);

                    //Graamos los datos en la base de datos.
                    miBaseDatos.introducirPuntuacion(datosTemporales[0], Integer.parseInt(datosTemporales[1]));

                    salida.println("OK");
                }

                //Cerramos la conexión con ese cliente.
                cliente.close();

            }
        } catch (IOException e){
            System.out.println(e);
        }



    }

}
