package Modelo;

import Datos.UsuarioDatos;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginModel {

    BaseDeDatos baseDatos;
    
    public loginModel(BaseDeDatos bd) {
        this.baseDatos = bd;
    }
    
    /**
     * esta funcion se encarga de realizar la consulta de la informacion 
     * del usuario al que se reciba la informacion 
     * 
     * 
     * @param usuario
     * @param pass
     * @return 
     * 
     * @author santiago
     */
    public UsuarioDatos ingresar(String usuario, String pass) {
    UsuarioDatos usuarioDto = null;
    
    try {
        String query = "SELECT * FROM `SEGURIDAD_LOGIN` WHERE USUARIO = ? AND CLAVE = ? ";
        Object[] parametros = { usuario, pass };
        
        ResultSet resultado = baseDatos.RealizarConsulta(query, parametros);
        
        // Usar next() en lugar de first()
        if (resultado != null && resultado.next()) {
            usuarioDto = new UsuarioDatos();
            usuarioDto.setUsuario(resultado.getString("USUARIO"));
            usuarioDto.setClave(resultado.getString("CLAVE"));
            usuarioDto.setTipo(Integer.parseInt(resultado.getString("TIPO")));
            usuarioDto.setPersona(Integer.parseInt(resultado.getString("PERSONA_ASOCIADA")));
        }
    } 
    catch (NumberFormatException | SQLException e) {
        System.out.println("Error al realizar el logueo de los datos, excepcion => " + e.getMessage());
    }
    
    return usuarioDto;
}
}

