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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy extends Thread{
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;

        // Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(Socket socketServicio) {
		this.socketServicio=socketServicio;
		random=new Random();
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa() throws IOException{
		
		String recibido;
                String envio;
		
                //Envia datos, como el outputStream		
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                //Recibe datos, como el inputStream
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                
		try {
                    
                        // Lee la frase a Yodaficar:
			recibido=inReader.readLine();

			// como yodaDo() devuelve un String y ya lo hemos declarado no hace falta hacer nada aqui
			// Yoda reinterpreta el mensaje:
			envio=yodaDo(recibido);
			
			// Enviamos la traducción de Yoda:
			outPrinter.println(envio);
			
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
        
        //Para hacerlo concurrente hemos de declarar un metodo run, que en servidor lanzaremos con .start()
        public void run(){
            try {
                procesa();
            } catch (IOException ex) {
                Logger.getLogger(ProcesadorYodafy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
