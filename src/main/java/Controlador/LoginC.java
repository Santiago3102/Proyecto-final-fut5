/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Datos.JugadorDatos;
import Datos.UsuarioDatos;
import Modelo.BaseDeDatos;
import Modelo.JugadorModel;
import Modelo.PartidosModel;
import Modelo.loginModel;
import Vista.Administrador;
import Vista.Jugador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.Login;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author SANTIAGO
 */
public class LoginC implements ActionListener {

    BaseDeDatos baseDatos;
    
    Login viewLogIn;
    Administrador viewAdmin;
    Jugador viewJugador;
    
    loginModel modelLogIn; 
    JugadorModel modeloJugador;
    PartidosModel modeloPartido;
    
    /**
     * constructor de este controlador, este controlador solo esta enfocado en
     * el sistema de ingreso
     * 
     * @throws java.sql.SQLException
    */
    public LoginC() throws SQLException {
        
        this.baseDatos = new BaseDeDatos();
        
        this.viewLogIn = new Login();
        this.viewAdmin = new Administrador();
        this.viewJugador = new Jugador();
        
        this.modelLogIn = new loginModel(this.baseDatos);
        this.modeloJugador = new JugadorModel(this.baseDatos);
        this.modeloPartido = new PartidosModel(this.baseDatos);
        
        this.viewLogIn.getIngresarBtn().addActionListener(this);
        this.viewJugador.getBtnSalir().addActionListener(this);
        this.viewAdmin.getSalirBtn().addActionListener(this);
        this.viewAdmin.getBtnFinalizarPartido().addActionListener(this);
        
    }

    /**
     * esta funcion se encarga de realizar el llamado y mostrar la vista 
     * correspondiente al login, es el primero en ser llamado en el sistema
     * 
     */
    public void iniciar() {
        this.viewLogIn.setTitle("Ingresar");
        this.viewLogIn.setLocationRelativeTo(null);
        this.viewLogIn.setVisible(true);
    }
    
    /**
     * esta funcion se desencadena al ejecutar el evento de seleccion del boton 
     * de ingresar, abriendo la pestaña correspondiente al usuario si este es valido
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == this.viewLogIn.getIngresarBtn()) {
            
            String user = this.viewLogIn.getUser().getText();
            String pass = String.valueOf(this.viewLogIn.getPass().getPassword());
            
            if (user == null || user.equals("")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un usuario");
            }
            else if (pass == null || pass.equals("")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar una contraseña");
            }
            else {
                UsuarioDatos usuario = this.modelLogIn.ingresar(user, pass);

                if (usuario != null) {

                    this.viewLogIn.setVisible(false);

                    JugadorDatos usuarioInfo = this.modeloJugador.ConsultarInformacionJugador(usuario.getPersona());

                    if (usuario.getTipo() == 0) {

                        this.viewAdmin.setTitle("Seccion de admnistrador");
                        this.viewAdmin.setLocationRelativeTo(null);
                        this.viewAdmin.setUsuarioInfo(usuarioInfo);
                        this.viewAdmin.setBaseDatos(this.baseDatos);


                        this.viewAdmin.setVisible(true);
                    }
                    else if (usuario.getTipo() == 1) {


                        this.viewJugador.setTitle("Seccion jugador");
                        this.viewJugador.setLocationRelativeTo(null);
                        this.viewJugador.setUsuarioInfo(usuarioInfo);
                        this.viewJugador.setBaseDatos(this.baseDatos);

                        this.viewJugador.setVisible(true);
                    }

                }
                else {
                    JOptionPane.showMessageDialog(null, "Acceso denegado");
                }
                
            }
            
        }
        
        if (e.getSource() == this.viewAdmin.getSalirBtn()) {
            JOptionPane.showMessageDialog(null, "Hasta la proxima");
            this.viewAdmin.dispose();
            this.viewAdmin.setVisible(false);
            this.LimpiarDatos();
            this.viewLogIn.dispose();
            this.viewLogIn.setVisible(true);
        }
        
        if (e.getSource() == this.viewJugador.getBtnSalir()) {
            JOptionPane.showMessageDialog(null, "Hasta la proxima");
            this.viewJugador.dispose();
            this.viewJugador.setVisible(false);
            this.LimpiarDatos();
            this.viewLogIn.dispose();
            this.viewLogIn.setVisible(true);
        }
    
        if (e.getSource() == this.viewAdmin.getBtnFinalizarPartido()) {
            
            String limpiarFormaParticipacion = "TRUNCATE TABLE CONFIGURACION_FORMA_PARTICIPACION";
            String limpiarJugadoresEquipo = "TRUNCATE TABLE TEMPORADA_JUGADOR_EQUIPO";
            
            Object[] parametros = {  };
                    
            this.baseDatos.RealizarActualizacion(limpiarFormaParticipacion, parametros);
            this.baseDatos.RealizarActualizacion(limpiarJugadoresEquipo, parametros);
            
            int id = this.modeloPartido.CodigoUltimoPartido();
            int idRespuesta = this.modeloPartido.ConsultarPartido(id);
            
            if (idRespuesta > 0) {
                
                String inactivarPartido = "UPDATE TEMPORADA_PARTIDOS SET ESTADO = 0 WHERE ID = ?";

                Object[] parametros2 = { idRespuesta };

                this.baseDatos.RealizarActualizacion(inactivarPartido, parametros2);
                
            }
            
            JOptionPane.showMessageDialog(null, "Se realiza la finalizacion del partido");
        }
    }
    
    private void LimpiarDatos() {
        this.viewLogIn.setUser("");
        this.viewLogIn.setPass("");
    }
    
}
