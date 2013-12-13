package yodafyservidoriterativo3;
//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteTCP{

	public static void main(String[] args) {
		
                String envio;
                String recibido;
		String envio2;
                String recibido2;
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		
		try {
			// Creamos un socket que se conecte a "host" y "port":
        		socketServicio=new Socket(host,port);
                        //Envia datos, como el outputStream
			PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                        //Recibe datos, como el inputStream
                        BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                         
			//Pasaremos el String "envio" directamente, sin pasarlo a un array de bytes.
			envio="Al monte del volcán debes ir sin demora";
			envio2="Estamos probando con dos hebras";
                        // Enviamos el String con println
                        outPrinter.println(envio);
                        
                        //No es necesario hacer .flush(), outPrinter lo hace automaticamente
                        //gracias al true que le hemos puesto en la declaración.
                        
			// Leemos la respuesta del servidor. 
                        //readLine() nos devuelve un String que será lo que mostraremos por pantalla
                        recibido=inReader.readLine();
                        System.out.println("Recibido: "+recibido);
                        //Probamos el envio con otra hebra de forma concurrente
                        outPrinter.println(envio2);
                        recibido2=inReader.readLine();
			// Mostramos la cadena de caracteres recibidos:
			
                        System.out.println("Recibido 2: "+recibido2);
                        //Al ser un String no hace falta hacer el for de los ejercicios anteriores para obtener
                        //el mensaje
						
			// Una vez terminado el servicio, cerramos el socket		
			socketServicio.close();
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
