
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class BaseDeDatos {

    // CONSTANTES USADAS PARA CONEXION
    private final String driverName = "com.mysql.cj.jdbc.Driver";
    private final String DBName = "jdbc:mysql://localhost/torneo";
    private final String DBUser = "root";
    private final String DBPass = "";
    
    Connection Connection;
    
    /**
     * CONSTRUCTOR DEFECTO
     * 
     * @throws java.sql.SQLException
     */
    public BaseDeDatos() throws SQLException {
        this.ConectarBD();
    }
    
    /**
     * ESTABLECE LA CONEXION BASE DE DATOS
     * 
     * @throws java.sql.SQLException
     */
    private Connection ConectarBD() throws SQLException {
        
        try {
            Class.forName(this.driverName); 
            this.Connection = DriverManager.getConnection(this.DBName, this.DBUser, this.DBPass);
        } 
        catch (ClassNotFoundException ex) {
          JOptionPane.showMessageDialog(null, "Error de conexión con el servidor de base de datos");
        }
        return this.Connection;
    }

    /**
     * Esta función se encarga de realizar consultas a la base de datos
     * @param consulta
     * @param parametros
     * @return 
     */
    public ResultSet RealizarConsulta(String consulta, Object[] parametros) {
        
        ResultSet resultado = null;
        
        try {
            PreparedStatement preparedStatement = this.Connection.prepareStatement(consulta);
            for (int i = 0; i < parametros.length; i++) {
                preparedStatement.setObject(i + 1, parametros[i]);
            }
            resultado = preparedStatement.executeQuery();
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se ha encontrado el siguiente error: " + e.getMessage());
        }
        
        return resultado;
    }

    /**
     * Esta función se encarga de realizar actualizaciones en la base de datos
     * @param consulta
     * @param parametros
     * @return número de filas afectadas
     */
    public int RealizarActualizacion(String consulta, Object[] parametros) {
        int filasAfectadas = 0;

        try {
            PreparedStatement preparedStatement = this.Connection.prepareStatement(consulta);
            for (int i = 0; i < parametros.length; i++) {
                preparedStatement.setObject(i + 1, parametros[i]);
            }
            filasAfectadas = preparedStatement.executeUpdate();
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se ha encontrado el siguiente error: " + e.getMessage());
        }

        return filasAfectadas;
    }

}
