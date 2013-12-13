/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package servidor;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
*
* @author juan
*/
class ThreadServerHandler extends Thread {
    
    private Socket cliente = null;
    
    public void procesaRespuesta(String simbolo, int numNuevo, int numAnterior){
        
          if(".".equals(simbolo)){
             //Si es el inicio del juego enviamos el número nuevo sin necesidad de hacer nada mas.
             System.out.println("Contrincante "+this.getName().substring(7)+ " dice: Ánimo!");
          }
          if("+".equals(simbolo)){
             System.out.println("Contrincante "+this.getName().substring(7)+ " dice: La solución es mayor");
             numNuevo=(int)(Math.random()*(9-numAnterior)+numAnterior);
          }
          if("-".equals(simbolo)){
             System.out.println("Contrincante "+this.getName().substring(7)+ " dice: La solución es menor");
             numNuevo=(int)(Math.random()*(numAnterior-0)+0);
          }
    
    }
    
    public ThreadServerHandler(Socket socket){
        this.cliente = socket;
    }
    @Override
    public void run() {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader (isr);
        
        //Control de juego.
        boolean fin=false;
        
        //Número con el que jugará el servidor, del 0 al 9.
        Random generator = new Random();
        int solucion=generator.nextInt(9)+1;
        
        //Variables que necesita el servidor para jugar.
        int numAnterior, numNuevo;
        //Inicialización de las mismas.
        numAnterior=(int)(Math.random()*(9-0)+0);
        numNuevo=numAnterior;
        
        //Mensajes necesarios.
        String mensajeEntrada="", mensajeSalida="";
        
        try{
            
            //Establecimiento de los flujos de entrada y de salida.
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
            System.out.println("Empieza el juego con contrincante n. "+this.getName().substring(7));
            System.out.println("Número SERVER: "+solucion);
            
            while(!fin){
                
                //Lectura del mensaje.
                mensajeEntrada=(String)entrada.readObject();
                //System.out.println("Mensaje recibido: "+mensajeEntrada);
                //Descomposición del mensaje
                String simbolo=mensajeEntrada.substring(0,1);
                String numeroEntrada=mensajeEntrada.substring(2,3);
                
                //System.out.println("Numero: "+numero);
                //System.out.println("Símbolo: "+simbolo);
                
                  
                if(mensajeEntrada.equals("fin")){
                    //Recibimos el mensaje fin porque hemos ganado.
                    fin=true;
                    System.out.println("Fin de partida contra jugador "+this.getName().substring(7)+".  Has ganado !!!");
                    //salida.writeObject("fin");
                    //La conexión sólo se cierra al recibir confirmación.
                    cliente.close();
                }
                else
                    if(Integer.parseInt(numeroEntrada)==solucion){
                        fin=true;
                        System.out.println("Fin de partida contra jugador "+this.getName().substring(7)+". Has perdido.");
                        salida.writeObject("fin");
                    }
                    else
                        if(Integer.parseInt(numeroEntrada)>solucion){
  
                             //Configuro mensaje salida.
                            
                            //Añado el simbolo - . La solución es menor. Para el contrincante.
                            mensajeSalida=mensajeSalida.concat("- ");
                           
                            /*Interpretación del símbolo de entrada y respecto a este configuración
                             * del numero con el que el servidor va a jugar*/
                            if(".".equals(simbolo)){
                                //Si es el inicio del juego enviamos el número nuevo sin necesidad de hacer nada mas.
                                System.out.println("Contrincante "+this.getName().substring(7)+ " dice: Ánimo!");
                            }
                            if("+".equals(simbolo)){
                                System.out.println("Contrincante "+this.getName().substring(7)+ " dice: La solución es mayor");
                                numNuevo=(int)(Math.random()*(9-numAnterior)+numAnterior);
                            }
                            if("-".equals(simbolo)){
                                System.out.println("Contrincante "+this.getName().substring(7)+ " dice: La solución es menor");
                                numNuevo=(int)(Math.random()*(numAnterior-0)+0);
                            }
                            
                            //Introduzco el número que ha sido calculado de forma aleatoria respecto a la respuesta.
        
                            System.out.println("Nuestro turno. Calculando número.");
                                                        
                            mensajeSalida=mensajeSalida.concat(Integer.toString(numNuevo));
                            
                            //Envío el mensaje.
                            System.out.println("Enviado n."+numNuevo+" a jugador "+this.getName().substring(7)+" Esperando su respuesta.");
                            numAnterior=numNuevo;
                            salida.writeObject(mensajeSalida);
                        }
                        else
                            if(Integer.parseInt(numeroEntrada)<solucion){
                                
                           //Configuro mensaje salida.
                            
                            //Añado el simbolo - . La solución es menor. Para el contrincante.
                            mensajeSalida=mensajeSalida.concat("+ ");
                           
                            /*Interpretación del símbolo de entrada y respecto a este configuración
                             * del numero con el que el servidor va a jugar*/
                            if(".".equals(simbolo)){
                                //Si es el inicio del juego enviamos el número nuevo sin necesidad de hacer nada mas.
                                System.out.println("Contrincante "+this.getName().substring(7)+ " dice: Ánimo!");
                            }
                            if("+".equals(simbolo)){
                                System.out.println("Contrincante "+this.getName().substring(7)+ " dice: La solución es mayor");
                                numNuevo=(int)(Math.random()*(9-numAnterior)+numAnterior);
                            }
                            if("-".equals(simbolo)){
                                System.out.println("Contrincante "+this.getName().substring(7)+ " dice: La solución es menor");
                                numNuevo=(int)(Math.random()*(numAnterior-0)+0);
                            }
                            
                            //Introduzco el número que ha sido calculado de forma aleatoria respecto a la respuesta.
        
                            System.out.println("Nuestro turno. Calculando número.");
                                                        
                            mensajeSalida=mensajeSalida.concat(Integer.toString(numNuevo));
                            
                            //Envío el mensaje.
                            System.out.println("Enviado n."+numNuevo+" a jugador "+this.getName().substring(7)+" Esperando su respuesta.");
                            numAnterior=numNuevo;
                            salida.writeObject(mensajeSalida);
                            }
                System.out.println("\n");
                mensajeSalida="";
            }
        }catch(Exception ex){};
        
        
    }
    
}