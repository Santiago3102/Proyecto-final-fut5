package Modelo;

import Datos.JugadorDatos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author SANTIAGO
 */
public class JugadorModel {

    BaseDeDatos baseDatos;
    
    public JugadorModel(BaseDeDatos bd) {
        this.baseDatos = bd;
    }
    
    /**
     * esta funcion se encarga de realizar la insercion de un nuevo jugador en el sistema
     * 
     * 
     * @param nombre
     * @param tipoSuscripcion
     * @param correo
     * @return 
     */
   public boolean RegistrarJugador(String nombre, int tipoSuscripcion, String correo) {
    
    boolean registrado = false;
    
    try {
        
        String query = "INSERT INTO CONFIGURACION_PERSONAS (`NOMBRE`, `TIPO_SUSCRIPCION`, `CORREO`) VALUES (?, ?, ?)";
        Object[] parametros = { nombre, tipoSuscripcion, correo };
        
        int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
        
        if (filasAfectadas > 0) {
            
            query = "SELECT * FROM CONFIGURACION_PERSONAS WHERE NOMBRE = ? ORDER BY ID DESC LIMIT 1";
            Object[] parametros2 = { nombre };
            
            ResultSet resultado = baseDatos.RealizarConsulta(query, parametros2);
        
            if (resultado != null && resultado.next()) {
                
                query = "INSERT INTO ESTADISTICAS_PROMEDIO (`ID_PERSONA`, `PROMEDIO`) VALUES (?, ?)";
                Object[] parametros3 = { resultado.getString("ID"), 5 };
                
                this.baseDatos.RealizarActualizacion(query, parametros3);
                
                query = "INSERT INTO SEGURIDAD_LOGIN (`USUARIO`, `CLAVE`, `TIPO`, `PERSONA_ASOCIADA`) VALUES (?, ?, ?, ?)";
                Object[] parametros4 = { nombre, nombre, 1, resultado.getString("ID") };
                
                this.baseDatos.RealizarActualizacion(query, parametros4);
                
                registrado = true;
                
                JOptionPane.showMessageDialog(null, "Se ha registrado al jugador, id asignado = " + resultado.getString("ID"));
            }
            
        }
        
    } 
    catch (SQLException e) {
        System.out.println("Error al registrar al jugador, error => " + e.getMessage());
    }
    
    return registrado;
}
    
    /**
     * esta funcion se encarga de realizar la consulta de la informacion de la persona 
     * por su identificador
     * 
     * 
     * @param idPersona
     * @return 
     */
    public JugadorDatos ConsultarInformacionJugador(int idPersona) {
        
        JugadorDatos jugador = null;
        
        try {
            String query = """
                           SELECT 
                           	CONFIGURACION_PERSONAS.ID,
                                CONFIGURACION_PERSONAS.NOMBRE,
                                CONFIGURACION_PERSONAS.TIPO_SUSCRIPCION,
                                estadisticas_promedio.PROMEDIO,
                                (SELECT COUNT(*) FROM temporada_penalizaciones WHERE temporada_penalizaciones.ID_PERSONA = ? ) as 'penalizaciones'
                           FROM 
                           	CONFIGURACION_PERSONAS 
                           left join 
                           	estadisticas_promedio on (CONFIGURACION_PERSONAS.ID = estadisticas_promedio.ID_PERSONA)
                           WHERE CONFIGURACION_PERSONAS.ID = ?
                           """;
            
            Object[] parametros = { idPersona, idPersona };

            ResultSet resultado = baseDatos.RealizarConsulta(query, parametros);
            
            if (resultado != null && resultado.next()) {
                jugador = new JugadorDatos();
                jugador.setId(Integer.parseInt(resultado.getString("ID")));
                jugador.setNombre(resultado.getString("NOMBRE"));
                jugador.setTipoSuscripcion(Integer.parseInt(resultado.getString("TIPO_SUSCRIPCION")));
                
                float promedio = (resultado.getString("PROMEDIO") != null) ? Float.parseFloat(resultado.getString("PROMEDIO")) : 0;
                jugador.setPromedio(promedio);
                jugador.setCantidadPenalizaciones(Integer.parseInt(resultado.getString("penalizaciones")));
            }
            
        } 
        catch (SQLException e) {
            System.out.println("No se pudo obtener la informacion acerca de la persona, error => " + e.getMessage());
        }
        
        return jugador;
    }

    /**
     * esta funcion se encarga de realizar el registro de participacion en el 
     * partido para el jugador, indicando la forma de participacion
     * 
     * 
     * @param idJugador
     * @param formaParticipacion
     * @return 
     */
    public boolean RegistrarParticipacion(int idJugador, int formaParticipacion) {
        
        boolean registrado = false;
        
        try {
            String query = "INSERT INTO CONFIGURACION_FORMA_PARTICIPACION (`ID_PERSONA`, `FORMA`) VALUES (?, ?)";
            Object[] parametros = { idJugador, formaParticipacion };
            
            int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
            
            registrado = (filasAfectadas > 0);
        } 
        catch (Exception e) {
            System.out.println("No se logro registrar la participacion del jugador, error => "+ e.getMessage());
        }
        
        return registrado;
    }

    /**
     * esta funcion se encarga de realizar la consulta de los jugadores con 
     * los cuales se puede generar el partido
     * 
     * 
     * @return 
     */
    public List<JugadorDatos> ListadoJugadores(){
        
        List<JugadorDatos> jugadores = new ArrayList<>();
        
        try {
            String query = """
                            SELECT 
                           	configuracion_personas.ID,
                                configuracion_personas.NOMBRE,
                                configuracion_personas.TIPO_SUSCRIPCION,
                           	configuracion_forma_participacion.FORMA,
                                estadisticas_promedio.PROMEDIO
                            FROM 
                                configuracion_personas 
                            INNER JOIN 
                                configuracion_forma_participacion ON configuracion_personas.ID = configuracion_forma_participacion.ID_PERSONA
                            INNER JOIN 
                                estadisticas_promedio ON estadisticas_promedio.ID_PERSONA = configuracion_personas.ID
                            ORDER BY 
                                configuracion_forma_participacion.FORMA ASC,
                                estadisticas_promedio.PROMEDIO DESC
                            LIMIT 10;
                           """;
            
            Object[] parametros = {};

            ResultSet resultado = baseDatos.RealizarConsulta(query, parametros);
            
            while (resultado.next()) {
                JugadorDatos jugador = new JugadorDatos();
                jugador.setId(resultado.getInt("ID"));
                jugador.setNombre(resultado.getString("NOMBRE"));
                jugador.setTipoSuscripcion(resultado.getInt("TIPO_SUSCRIPCION"));
                jugador.setForma(Integer.parseInt(resultado.getString("FORMA")));
                jugador.setPromedio(resultado.getFloat("PROMEDIO"));

                jugadores.add(jugador);
            }
            
        } 
        catch (SQLException e) {
            System.out.println("No se pudo obtener la informacion acerca de los jugadores, error => " + e.getMessage());
        }
        
        return jugadores;
        
    }
    
}
