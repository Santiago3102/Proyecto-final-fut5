package Modelo;

import Datos.EquiposDatos;
import Datos.JugadorDatos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Util.EmailUtil;

public class PartidosModel {
    
    BaseDeDatos baseDatos;
    
    public PartidosModel(BaseDeDatos bd) {
        this.baseDatos = bd;
    }
    
    /**
     * Esta función se encarga de realizar la inserción de un partido
     * con notificación por correo electrónico a los jugadores
     * 
     * @param fecha Fecha del partido
     * @param equipoA ID del equipo local
     * @param equipoB ID del equipo visitante
     * @return true si el partido se insertó correctamente, false en caso contrario
     */
    public boolean InsertarPartido(String fecha, int equipoA, int equipoB) {
        boolean registrado = false;
        
        try {
            // Insertar el partido en la base de datos
            String query = "INSERT INTO TEMPORADA_PARTIDOS (`FECHA_JUEGO`, `LOCAL`, `VISITA`, `ESTADO`) VALUES (?, ?, ?, ?)";
            Object[] parametros = { fecha, equipoA, equipoB, 1 }; // 1 es el estado activo
            
            int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
            
            if (filasAfectadas > 0) {
                // Consultar jugadores de ambos equipos para enviar notificaciones
                query = """
                    SELECT cp.NOMBRE, cp.CORREO 
                    FROM CONFIGURACION_PERSONAS cp 
                    JOIN TEMPORADA_JUGADOR_EQUIPO tje ON cp.ID = tje.ID_PERSONA 
                    WHERE tje.ID_EQUIPO IN (?, ?)
                """;
                Object[] parametrosConsulta = { equipoA, equipoB };
                
                ResultSet resultado = baseDatos.RealizarConsulta(query, parametrosConsulta);
                
                // Enviar correos a todos los jugadores
                while (resultado.next()) {
                    String nombre = resultado.getString("NOMBRE");
                    String correo = resultado.getString("CORREO");
                    
                    String asunto = "Nuevo Partido Programado";
                    String cuerpo = "Hola " + nombre + ",\n\n" + 
                                    "Se ha programado un nuevo partido para la fecha: " + fecha + "\n\n" +
                                    "Puede verificar los detalles en el sistema.";
                    
                    // Enviar correo
                    EmailUtil.enviarCorreo(correo, asunto, cuerpo);
                }
                
                registrado = true;
            }
        } 
        catch (SQLException e) {
            System.out.println("Error al insertar partido, error => " + e.getMessage());
        }
        
        return registrado;
    }
    
    /**
     * Esta función se encarga de realizar la inserción de un partido
     * con el método original
     * 
     * @param fecha Fecha del partido
     * @param local ID del equipo local
     * @param visita ID del equipo visitante
     * @return true si el partido se insertó correctamente, false en caso contrario
     */
    public boolean InsertarPartidoOriginal(String fecha, int local, int visita) {
        boolean guardado = false;
        
        try {
            String query = "INSERT INTO `TEMPORADA_PARTIDOS` (`FECHA_JUEGO`, `LOCAL`, `VISITA`, `ESTADO`) VALUES (?, ?, ?, 1)";
            Object[] parametros = { fecha, local, visita };
            
            int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
            
            guardado = (filasAfectadas > 0);
            
        } 
        catch (Exception e) {
            System.out.println("No se pudo insertar el partido, error => "+ e.getMessage());
        }
        
        return guardado;
    }
    
    /**
     * Esta función se encarga de realizar la asignación de una persona a un equipo
     * 
     * @param idEquipo ID del equipo
     * @param idPersona ID de la persona
     * @return true si la asignación fue exitosa, false en caso contrario
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
            System.out.println("No se pudo asignar el jugador al equipo, error => "+ e.getMessage());
        }
        
        return guardado;
    }

    /**
     * Esta función se encarga de generar alineaciones separando los jugadores en dos listas
     * 
     * @param jugadores Lista de jugadores a dividir
     * @return Objeto EquiposDatos con dos equipos
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
            System.out.println("No se pudo generar las alineaciones, error => "+ e.getMessage());
        }
        
        return equipos;
    } 
    
    /**
     * Esta función se encarga de consultar el código del último partido jugado
     * 
     * @return ID del último partido
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
            System.out.println("No se pudo obtener la información acerca del partido, error => " + e.getMessage());
        }
        
        return IdPartido;
    }
    
    /**
     * Esta función se encarga de consultar el código del último partido activo
     * 
     * @return ID del último partido activo
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
            System.out.println("No se pudo obtener la información acerca del último partido activo, error => " + e.getMessage());
        }
        
        return IdPartido;
    }
    
    /**
     * Esta función se encarga de consultar un partido por su código
     * 
     * @param idPartido ID del partido a consultar
     * @return ID del partido consultado
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
            System.out.println("No se pudo obtener la información acerca del partido, error => " + e.getMessage());
        }
        
        return IdPartido;
    }
}