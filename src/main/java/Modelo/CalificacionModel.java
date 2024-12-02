package Modelo;

public class CalificacionModel {
    
    BaseDeDatos baseDatos;
    
    public CalificacionModel(BaseDeDatos bd) {
        this.baseDatos = bd;
    }
    
    /**
     * esta funcion se encarga de realizar la insercion de la calificacion dada a un jugador
     * 
     * @param idCalificado
     * @param idCalificador
     * @param idPartido
     * @param calificacion
     * @param comentario
     * @return 
     */
    public boolean GenerarCalificacion(int idCalificado, int idCalificador, int idPartido, int calificacion, String comentario) {
        
        boolean calificado = false;
        
        try {
            String query = "INSERT INTO ESTADISTICAS_CALIFICACIONES (`PARTIDO_CALIFICADO`, `ID_CALIFICADOR`, `ID_CALIFICADO`, `CALIFICACION`, `CRITICA`) VALUES (?, ?, ?, ?, ?)";
            Object[] parametros = { idPartido, idCalificador, idCalificado, calificacion, comentario };
            
            int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
            
            if (filasAfectadas > 0) {
                calificado = true;
                this.ActualizarPromedio(idCalificado);
            }
        } 
        catch (Exception e) {
            System.out.println("No se pudo generar la calificacion al jugador, error => " + e.getMessage());
        }
        
        return calificado;
    }
    
    /**
     * esta funcion se encarga de realizar la actualizacion del promedio del jugador 
     * usando los ultimos 10 partidos 
     * @param idJugador
     * @return 
     */
    public boolean ActualizarPromedio(int idJugador) {
        
        boolean calificado = false;
        
        try {
            String query = """
                           UPDATE estadisticas_promedio 
                           SET PROMEDIO = (
                           SELECT AVG(CALIFICACION) AS promedio_valor
                           FROM (
                               SELECT estadisticas_calificaciones.CALIFICACION 
                               FROM estadisticas_calificaciones
                               WHERE estadisticas_calificaciones.ID_CALIFICADO = ?
                               ORDER BY estadisticas_calificaciones.PARTIDO_CALIFICADO DESC
                               LIMIT 10
                           ) AS subquery)
                           WHERE estadisticas_promedio.ID_PERSONA = ?
                           """
            ;
            Object[] parametros = { idJugador, idJugador };
            
            int filasAfectadas = this.baseDatos.RealizarActualizacion(query, parametros);
            
            if (filasAfectadas > 0) {
                calificado = true;
            }
        } 
        catch (Exception e) {
            System.out.println("No se pudo gactualizar el promedio, error => " + e.getMessage());
        }
        
        return calificado;
        
    }
}   
