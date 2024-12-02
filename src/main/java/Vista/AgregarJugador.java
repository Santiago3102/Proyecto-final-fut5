package Vista;

import Datos.JugadorDatos;
import Modelo.BaseDeDatos;
import Modelo.JugadorModel;
import javax.swing.*;
import java.awt.*;

public class AgregarJugador extends JPanel {

    private JugadorDatos usuarioInfo;
    private BaseDeDatos baseDatos;

    private JTextField nombre;
    private JTextField correo;
    private JComboBox<String> tipo;
    private JButton agregar;

    public AgregarJugador() {
        initComponents();
    }
    
     public JugadorDatos getUsuarioInfo() {
        return usuarioInfo;
    }

    public void setUsuarioInfo(JugadorDatos usuarioInfo) {
        this.usuarioInfo = usuarioInfo;
    }

    public void setBaseDatos(BaseDeDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    private void initComponents() {
       
        setLayout(new BorderLayout());
        setBackground(new Color(230, 245, 245));  

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(180, 230, 250));  
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 120, 40, 120));

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));  
        nombreLabel.setAlignmentX(CENTER_ALIGNMENT);

        nombre = new JTextField();
        nombre.setFont(new Font("Segoe UI", Font.PLAIN, 16)); 
        nombre.setBackground(new Color(250, 250, 250));  
        nombre.setMaximumSize(new Dimension(1000, 60));  
        nombre.setAlignmentX(CENTER_ALIGNMENT);

        JLabel correoLabel = new JLabel("Correo Electrónico:");
        correoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22)); 
        correoLabel.setAlignmentX(CENTER_ALIGNMENT);

        correo = new JTextField();
        correo.setFont(new Font("Segoe UI", Font.PLAIN, 16)); 
        correo.setBackground(new Color(250, 250, 250));  
        correo.setMaximumSize(new Dimension(1000, 60));  
        correo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel tipoLabel = new JLabel("Tipo:");
        tipoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22)); 
        tipoLabel.setAlignmentX(CENTER_ALIGNMENT);

        tipo = new JComboBox<>(new String[]{"Seleccione", "Ocacional", "Frecuente"});
        tipo.setFont(new Font("Segoe UI", Font.PLAIN, 16));  
        tipo.setBackground(new Color(230, 230, 255));  
        tipo.setMaximumSize(new Dimension(1000, 60));  
        tipo.setAlignmentX(CENTER_ALIGNMENT);

        agregar = new JButton("Agregar");
        agregar.setFont(new Font("Segoe UI", Font.PLAIN, 22));  
        agregar.setBackground(new Color(52, 122, 219));  
        agregar.setForeground(Color.WHITE); 
        agregar.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        agregar.setAlignmentX(CENTER_ALIGNMENT);
        agregar.setMaximumSize(new Dimension(1000, 70));  
        agregar.addActionListener(evt -> agregarActionPerformed());

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(nombreLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(nombre);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(correoLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(correo);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(tipoLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(tipo);
        formPanel.add(Box.createVerticalStrut(40));
        formPanel.add(agregar);

        add(formPanel, BorderLayout.CENTER);
    }

    private void agregarActionPerformed() {
        String nombreJugador = nombre.getText();
        String correoJugador = correo.getText();

        if (nombreJugador == null || nombreJugador.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe indicar el nombre del jugador");
        } else if (correoJugador == null || correoJugador.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe indicar el correo electrónico");
        } else {
            String tipoSelected = (String) tipo.getSelectedItem();
            int tipoJugador = -1;

            if ("Ocacional".equalsIgnoreCase(tipoSelected)) {
                tipoJugador = 0;
            } else if ("Frecuente".equalsIgnoreCase(tipoSelected)) {
                tipoJugador = 1;
            }

            if (tipoJugador == -1) {
                JOptionPane.showMessageDialog(this, "Debe indicar una opción de tipo");
            } else {
                JugadorModel modeloJugador = new JugadorModel(this.baseDatos);
                boolean registrado = modeloJugador.RegistrarJugador(nombreJugador, tipoJugador, correoJugador);

                JOptionPane.showMessageDialog(this, (registrado ? "Se" : "No se") + " ha registrado la información en el sistema");
                nombre.setText("");
                correo.setText("");
            }
        }
    }
}