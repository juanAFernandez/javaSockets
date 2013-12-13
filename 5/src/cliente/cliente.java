
package cliente;
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
//Para los mensajes.
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class cliente {
    
    public static void main(String[] args){
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);
        try {
            Socket cliente = new Socket("localhost", 4500);
            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            
            boolean fin=false;
            String miNumero; //Número con el que el cliente va a probar.
            
            Random generator = new Random();
            
            int solucion=generator.nextInt(9)+1;
            String mensajeEntrada="", mensajeSalida="";
            System.out.print("Selecciona número con el que jugar: ");
            solucion=Integer.parseInt(br.readLine());
            System.out.println("Mi numero:"+solucion);
            
            try {
                
                
                //El cliente siempre empieza, por eso su primera jugada se lanza sin el while. Así puede
                //empezar también con read.
                System.out.println("Empiezas tu. Introduce valor:");
                miNumero = br.readLine();
                
                mensajeSalida=". "; 
                mensajeSalida=mensajeSalida.concat(miNumero);
                
                System.out.println("Enviado n."+miNumero+" Esperando respuesta del contricante.");
                salida.writeObject(mensajeSalida);
                
                
                while(!fin){
                    
                     
                //Lectura del mensaje.
                mensajeEntrada=(String)entrada.readObject();
                
                //System.out.println("Recibido:"+mensajeEntrada);
                
                //Descomposición del mensaje
                String simbolo=mensajeEntrada.substring(0,1);
                String numero=mensajeEntrada.substring(2, 3);

                  
                if(mensajeEntrada.equals("fin")){
                    //Recibimos el mensaje fin porque hemos ganado.
                    fin=true;
                    System.out.println("Fin de partida. Has ganado !!!");
                    //salida.writeObject("fin");
                    //La conexión sólo se cierra al recibir confirmación.
                    cliente.close();
                }
                else 
                    if(Integer.parseInt(numero)==solucion){
                        fin=true;
                        salida.writeObject("fin");
                        System.out.println("Fin de partida. Has perdido.");
                    }
                else
                        if(Integer.parseInt(numero)>solucion){
                            
                            //Examino la respuesta
                           if("+".equals(simbolo))
                                System.out.println("Contrincante dice: La solución es mayor");
                            if("-".equals(simbolo))
                                System.out.println("Contrincante dice: La solución es menor");
                     
                            //Configuro mensaje salida.
                            //Añado el simbolo - . La solución es menor.
                            mensajeSalida="";
                            mensajeSalida=mensajeSalida.concat("- ");
                            System.out.println("Nuestro turno. Introduce un ńumero:");
                            
                            //Leo el número con el que jugar y lo añado al menssaje de salida.
                            miNumero = br.readLine();
                            mensajeSalida=mensajeSalida.concat(miNumero);
                            
                            //Envío el mensaje.
                            System.out.println("Enviado n."+miNumero+" Esperando respuesta del contricante.");
                            salida.writeObject(mensajeSalida);
                        }
                        else
                            if(Integer.parseInt(numero)<solucion){
                                
                            //Examino la respuesta
                            if("+".equals(simbolo))
                                System.out.println("Contrincante dice: La solución es mayor");
                            if("-".equals(simbolo))
                                System.out.println("Contrincante dice: La solución es menor");
                                
                                //Configuro mensaje salida.
                                
                                //Añado el simbolo - . La solución es menor.
                                mensajeSalida="";
                                mensajeSalida=mensajeSalida.concat("+ ");
                                System.out.println("Nuestro turno. Introduce un ńumero:");
                                
                                //Leo el número con el que jugar y lo añado al menssaje de salida.
                                miNumero = br.readLine();
                                mensajeSalida=mensajeSalida.concat(miNumero);
                                
                                //Envío el mensaje.
                                System.out.println("Enviado n."+miNumero+" Esperando respuesta del contricante.");
                                salida.writeObject(mensajeSalida);
                            }
                
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } catch (UnknownHostException ex) { //Cuando no se encuentra el servidor.
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    
}
