package yodafyservidoriterativo4;
//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private DatagramSocket socketServicio;
	
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor
	public ProcesadorYodafy(DatagramSocket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){

     		InetAddress direccion;
                int port;
                byte[] bufer=new byte[256];
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarla:
		byte [] datosEnviar;
		DatagramPacket paqueteRecibido;
                DatagramPacket paquete;
		
		try {
                        paqueteRecibido =new DatagramPacket(bufer, bufer.length);
                        //recibimos el DatagramPacket
                        socketServicio.receive(paqueteRecibido);
                        //Obtenemos la direccion y el puerto de donde hemos recibido los mensajes.
                        direccion=paqueteRecibido.getAddress();
                        port=paqueteRecibido.getPort();

			// Creamos un String a partir de un array de bytes de tamaño "paqueteRecibido.getLenght()":
			String peticion=new String(paqueteRecibido.getData(),0,paqueteRecibido.getLength());

			// Yoda reinterpreta el mensaje:
			String respuesta=yodaDo(peticion);

			// Convertimos el String de respuesta en una array de bytes:
			datosEnviar=respuesta.getBytes();

			//Rellenamos el DatagramPacket que vamos a enviar.
                        paquete =new DatagramPacket(datosEnviar, datosEnviar.length, direccion,port);

                        //Enviamos el mensaje yodificado.
                        socketServicio.send(paquete);

			
		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";
		
		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];
			
			s[j]=s[k];
			s[k]=tmp;
		}
		
		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}
		
		return resultado;
	}
}
