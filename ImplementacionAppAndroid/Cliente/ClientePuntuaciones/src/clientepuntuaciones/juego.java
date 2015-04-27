package clientepuntuaciones;


import java.util.List;

/**
 * Simulación del juego.
*/
public class juego {
    
    //Simulación:
    public static void main(String[] args){
        
        
       ConexionServidor miConexion = new ConexionServidor();
       miConexion.enviaPuntuacion("jP", 367462);
        
       List<String>ranking;
       ranking=miConexion.pedirRanking();
       
       for (String dato : ranking) {
        System.out.println(dato);
       }
       
        
        
    }
    
}
