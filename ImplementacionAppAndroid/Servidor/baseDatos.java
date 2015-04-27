//Import de las clases necesarias para la hacer la conexión.
import java.sql.*;

/**
 * Clase para la conexión de la base de datos que simplifica su uso y manejo.
 */
public class baseDatos {

    private String host;
    private String user;
    private String pass;
    private Connection con;


    /**
     * Constructor.
     * host: equipo
     * user: usuario de la base de datos
     * pass: contraseña de acceso a la base de datos
    */
    public baseDatos(){
        //Asignamos las variables:
        this.host="localhost";
        this.user="root";
        this.pass="root";
        this.con=null;
    }

    /* MÉTODOS PRIVADOS */
    private void conectar(){

        //Conectamos el driver:
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Arrancando driver de base de datos.");
        } catch (Exception e) {
            System.out.println("\n ### Imposible arrancar driver. ### \n ");
            System.out.println(e);
        }

        try {
            con =DriverManager.getConnection("jdbc:mysql://localhost/puntuaciones?"+"user="+this.user+"&password="+this.pass);
            System.out.println("Conexión establecida con la base de datos.");
            // Otros y operaciones sobre la base de datos...
        } catch (SQLException ex) {
        // Mantener el control sobre el tipo de error
        System.out.println("SQLException: " + ex.getMessage());
        }
    }

    private void desconectar() throws SQLException{
        con.close();
    }


    /* MÉTODOS PÚBLICOS */
    public String consultarPuntuaciones() throws SQLException{

        String resultado="";
        this.conectar();

        Statement cmd = null;
        ResultSet result = null;

        try{
            cmd=con.createStatement();
          //  result=cmd.executeQuery("SELECT * FROM puntuaciones");
            result=cmd.executeQuery("select * from puntuaciones ORDER BY puntuacion DESC");
        }catch(Exception e){
            System.out.println(e);
        }


        //Conversión a String de los resultados:
         while(result.next()){
            String nombre=result.getString("nombre");
            int valor=result.getInt(2);
            resultado+=nombre+" "+valor+"\n";
        }


        this.desconectar();
        return resultado;
    }

    public void introducirPuntuacion(String alias, int puntuacion) throws SQLException{
        String resultado="";
        this.conectar();

        Statement cmd = null;
        ResultSet result = null;

        try{
            cmd=con.createStatement();
            cmd.executeUpdate("INSERT INTO puntuaciones VALUES('"+alias+"',"+puntuacion+")");
            //System.out.println(result);

        }catch(Exception e){
            System.out.println(e);
        }

        this.desconectar();
    }

}
