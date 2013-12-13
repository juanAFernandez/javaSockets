
package servidor;
//Para poder montar el servidor.
import java.net.ServerSocket;
//Para los mensajes.

import java.net.Socket;


public class servidor {
 
    public static void main(String[] args) {
        //Iniciamos el servidor.
        try{
        ServerSocket servidor = new ServerSocket(4500);
        
        while (true){
            Socket clienteNuevo = servidor.accept();
            Thread t = new ThreadServerHandler(clienteNuevo);
            t.start();
        }
        }catch(Exception ex){};
    }
}
