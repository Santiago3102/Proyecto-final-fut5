
package Datos;

public class SesionJugador {
    private static UsuarioDatos usuario;
    private static JugadorDatos jugador;
    
    public static void InicioSesion(UsuarioDatos nuevoUsuario, JugadorDatos nuevoJugador){
        usuario = nuevoUsuario;
        jugador = nuevoJugador;
    }

    public static UsuarioDatos getUsuario() {
        return usuario;
    }

    public static JugadorDatos getJugador() {
        return jugador;
    }
 
}
