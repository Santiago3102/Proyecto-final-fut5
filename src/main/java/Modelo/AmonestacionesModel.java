package Modelo;

import Datos.AmonestacionesDatos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AmonestacionesModel {

    BaseDeDatos baseDatos;
    
    public AmonestacionesModel(BaseDeDatos bd) {
        this.baseDatos = bd;
    }
    
    /**
     * esta funcion se encarga de realizar la amonestacion a un jugador, ademas 
     * de especificar cual es el partido en el que se amonesta
     * 
     * 
     * @param idPersona
     * @param idPartido
     * @return 
     */
    public boolean amonestarJugador(int idPersona, int idPartido) {
        
        boolean guardado = false;
        
        try {
            String query = "INSERT INTO TEMPORADA_PENALIZACIONES (`ID_PERSONA`, `ID_PARTIDO`) VALUES (?, ?)";
            Object[] parametros = { idPersona, idPartido };
            
            int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
            
            guardado = (filasAfectadas > 0);
            
        } 
        catch (Exception e) {
            System.out.println("No se pudo amonestar el jugador, error => "+ e.getMessage());
        }
        
        return guardado;
    }
    
    /**
     * esta funcion se encarga de realizar la eliminacion de una amonestacion
     * aunque mas que eliminacion es una inactivacion de esa amonestacion
     * 
     * 
     * @param idPenalizacion
     * @return 
     */
    public boolean quitarAmonestacionJugador(int idPenalizacion) {
        
        boolean guardado = false;
        
        try {
            String query = "UPDATE TEMPORADA_PENALIZACIONES SET `ESTADO` = 0 WHERE `ID` = ?";
            Object[] parametros = { idPenalizacion };
            
            int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
            
            guardado = (filasAfectadas > 0);
            
        } 
        catch (Exception e) {
            System.out.println("No se pudo amonestar el jugador, error => "+ e.getMessage());
        }
        
        return guardado;
        
    }
    
    /**
     * esta funcion se encarga de realizar la consulta de las amonestacionaes asociadas
     * a los jugadores
     * 
     * 
     * @return 
     */
    public List<AmonestacionesDatos> ListadoAmonestaciones(){
        
        List<AmonestacionesDatos> jugadores = new ArrayList<>();
        
        try {
            String query = """
                            SELECT 
                            	temporada_penalizaciones.ID     as ID,
                                configuracion_personas.ID       as id_persona,
                                configuracion_personas.NOMBRE   as nombre_persona,
                                temporada_partidos.ID           as id_partido,
                                temporada_partidos.FECHA_JUEGO  as fecha
                            FROM 
                            	temporada_penalizaciones 
                            INNER join 
                            	configuracion_personas on (temporada_penalizaciones.ID_PERSONA = configuracion_personas.ID)
                            INNER JOIN
                            	temporada_partidos on (temporada_penalizaciones.ID_PARTIDO = temporada_partidos.ID)
                            WHERE 
                                temporada_penalizaciones.ESTADO = 1
                           """;
 
            
            Object[] parametros = {};

            ResultSet resultado = baseDatos.RealizarConsulta(query, parametros);
            
            while (resultado.next()) {
                AmonestacionesDatos jugador = new AmonestacionesDatos();
                
                jugador.setId(resultado.getInt("ID"));
                
                jugador.setIdPersona(resultado.getInt("id_persona"));
                jugador.setNombreJugador(resultado.getString("nombre_persona"));
                
                jugador.setIdPartido(resultado.getInt("id_partido"));
                jugador.setFechaJuego(resultado.getString("fecha"));
                
                jugadores.add(jugador);
            }
            
        } 
        catch (SQLException e) {
            System.out.println("No se pudo obtener la informacion acerca de las sanciones, error => " + e.getMessage());
        }
        
        return jugadores;
        
    }
    
}
