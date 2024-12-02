package Modelo;

import Datos.EquiposDatos;
import Datos.JugadorDatos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartidosModel {
    
    BaseDeDatos baseDatos;
    
    public PartidosModel(BaseDeDatos bd) {
        this.baseDatos = bd;
    }
    
    /**
     * esta funcion se encarga de realizar la insercion de un partido
     * 
     * 
     * @param fecha
     * @param local
     * @param visita
     * @return 
     */
    public boolean InsertarPartido(String fecha, int local, int visita) {
        
        boolean guardado = false;
        
        try {
            String query = "INSERT INTO `TEMPORADA_PARTIDOS` (`FECHA_JUEGO`, `LOCAL`, `VISITA`, `ESTADO`) VALUES (?, ?, ?, 1)";
            Object[] parametros = { fecha, local, visita };
            
            int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
            
            guardado = (filasAfectadas > 0);
            
        } 
        catch (Exception e) {
            System.out.println("No se pudo amonestar el jugador, error => "+ e.getMessage());
        }
        
        return guardado;
        
    }

    /**
     * eata funcion se encarga de realizar la asignacion de una persona a un 
     * equipo 
     * 
     * 
     * @param idEquipo
     * @param idPersona
     * @return 
     */
    public boolean AsignarJugadorEquipo(int idEquipo, int idPersona) {
        
        boolean guardado = false;
        
        try {
            String query = "INSERT INTO `TEMPORADA_JUGADOR_EQUIPO` (`ID_EQUIPO`, `ID_PERSONA`) VALUES (?, ?)";
            Object[] parametros = { idEquipo, idPersona };
            
            int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
            
            guardado = (filasAfectadas > 0);
            
        } 
        catch (Exception e) {
            System.out.println("No se pudo amonestar el jugador, error => "+ e.getMessage());
        }
        
        return guardado;
        
    }

    /**
     * esta funcion se encarga de realizar la creacion de los equipos 
     * separando los jugadores en dos listas organizadas por medio de 
     * una selccion simple
     * 
     * @param jugadores
     * @return 
     */
    public EquiposDatos GenerarAlineaciones(List<JugadorDatos> jugadores) {
        
        EquiposDatos equipos = new EquiposDatos();
        
        try {
             
            List<JugadorDatos> equipoUno = new ArrayList<>();
            List<JugadorDatos> equipoDos = new ArrayList<>();
            
            for(int i = 0; i < jugadores.size(); i++){
                if (i % 2 == 0) {
                    equipoUno.add(jugadores.get(i));
                }
                else {
                    equipoDos.add(jugadores.get(i));
                }
            }
            
            equipos.setEquipoA(equipoUno);
            equipos.setEquipoB(equipoDos);
            
        } 
        catch (Exception e) {
            System.out.println("No se pudo consultar el listado, error => "+ e.getMessage());
        }
        
        return equipos;
        
    } 
    
    /**
     * esta funcion se encarga de realizar la consulta de cual es el ultimo partido jugado
     * 
     * 
     * @return 
     */
    public int CodigoUltimoPartido() {
        
        int IdPartido = 0;
        
        try {
            String query = "SELECT * FROM `temporada_partidos` ORDER BY ID DESC LIMIT 1";
            Object[] parametros = { };

            ResultSet resultado = baseDatos.RealizarConsulta(query, parametros);
            
            if (resultado != null && resultado.next()) {
                IdPartido = Integer.parseInt(resultado.getString("ID"));
            }
            
        } 
        catch (SQLException e) {
            System.out.println("No se pudo obtener la informacion acerca del partido, error => " + e.getMessage());
        }
        
        return IdPartido;
    }
    
    /**
     * esta funcion se encarga de realizar la consulta del ultimo partido activo
     * @return 
     */
    public int CodigoUltimoPartidoActivo() {
        
        int IdPartido = 0;
        
        try {
            String query = "SELECT * FROM `temporada_partidos` WHERE ESTADO = 1 ORDER BY ID DESC LIMIT 1";
            Object[] parametros = { };

            ResultSet resultado = baseDatos.RealizarConsulta(query, parametros);
            
            if (resultado != null && resultado.next()) {
                IdPartido = Integer.parseInt(resultado.getString("ID"));
            }
            
        } 
        catch (SQLException e) {
            System.out.println("No se pudo obtener la informacion acerca del ultimo partido activo, error => " + e.getMessage());
        }
        
        return IdPartido;
    }
    
    /**
     * esta funcion se encarga de realizar la la consulta de un partido por su codigo
     * @param idPartido
     * @return 
     */
    public int ConsultarPartido(int idPartido) {
        
        int IdPartido = 0;
        
        try {
            String query = "SELECT * FROM `temporada_partidos` WHERE ID = ? ORDER BY ID DESC LIMIT 1";
            Object[] parametros = { idPartido };

            ResultSet resultado = baseDatos.RealizarConsulta(query, parametros);
            
            if (resultado != null && resultado.next()) {
                IdPartido = Integer.parseInt(resultado.getString("ID"));
            }
            
        } 
        catch (SQLException e) {
            System.out.println("No se pudo obtener la informacion acerca del partido, error => " + e.getMessage());
        }
        
        return IdPartido;
    }
}
