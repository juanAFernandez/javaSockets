package yodafyservidoriterativo3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//

public class YodafyServidorConcurrente{

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
				ProcesadorYodafy procesador=new ProcesadorYodafy(socketServicio);
				//Lanzamos el procesador con .start()
                                procesador.start();
                                //Para crear y lanzar otra hebra
                                ProcesadorYodafy procesador2=new ProcesadorYodafy(socketServicio);
                                procesador2.start();
				
			} while (true);
			
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}

}
