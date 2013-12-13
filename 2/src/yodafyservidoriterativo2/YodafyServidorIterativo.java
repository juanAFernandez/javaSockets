package yodafyservidoriterativo2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//

//No es necesario cambiar nada para este ejercicio en este archivo.

public class YodafyServidorIterativo {

	public static void main(String[] args) {
	
		// Puerto de escucha
		int port=8989;
		
                ServerSocket serverSocket;
                Socket socketServicio=null;
		try {
			// Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
			serverSocket = new ServerSocket(port);

                        // Mientras ... siempre!
			do {
				
				// Aceptamos una nueva conexión con accept()
				socketServicio=serverSocket.accept();

                                // Creamos un objeto de la clase ProcesadorYodafy, pasándole como 
				// argumento el nuevo socket, para que realice el procesamiento
				// Este esquema permite que se puedan usar hebras más fácilmente.
				ProcesadorYodafy procesador=new ProcesadorYodafy(socketServicio);
				procesador.procesa();
				
			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}
