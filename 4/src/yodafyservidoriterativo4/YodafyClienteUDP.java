package yodafyservidoriterativo4;
//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class YodafyClienteUDP {

	public static void main(String[] args) {
		
		byte []buferRecepcion=new byte[256];
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		// Socket para la conexión UDP
		DatagramSocket socketServicio=null;
		InetAddress direccion;
                DatagramPacket paquete;
                DatagramPacket paqueteRecibido;
                byte[] bufer = new byte[256];
                
                
		try {
			// Creamos un socket que se conecte a "host" y "port":
			socketServicio=new DatagramSocket();
                        //Buscamos la direccion IP del servidor
                        direccion = InetAddress.getByName(host);
                        
                        bufer="Al monte del volcán debes ir sin demora".getBytes();
                        //Creamos un DatagramPacket con todos los datos para enviarlo por el DatagramSocket
                        paquete =new DatagramPacket(bufer, bufer.length, direccion,port);

                        //Enviamos el mensaje.
                        socketServicio.send(paquete);

                        //Cambiamos el DatagramPacket para recibir el mensaje.
                        paqueteRecibido = new DatagramPacket(buferRecepcion, buferRecepcion.length);
                        //recibimos el DatagramPacket
                        socketServicio.receive(paqueteRecibido);

			// Mostramos la cadena de caracteres recibidos:
			System.out.println("Recibido: ");
			for(int i=0;i<paqueteRecibido.getLength();i++){
				System.out.print((char)buferRecepcion[i]);
			}
			
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
