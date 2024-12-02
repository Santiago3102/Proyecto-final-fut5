package Vista;

import Datos.JugadorDatos;
import Datos.SesionJugador;
import Modelo.BaseDeDatos;
import Modelo.JugadorModel;
import javax.swing.*;
import java.awt.*;

public class Participacion extends javax.swing.JPanel {
  
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    private static final Color PRIMARY_COLOR = new Color(52, 160, 219);
    private static final Color BUTTON_COLOR = new Color(52, 160, 219);
    private static final Color WHITE_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private BaseDeDatos baseDatos;
    private JTextField id;
    private JTextField nombre;
    private JComboBox<String> forma;
    private JButton registrar;
    private JPanel jPanel1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;

    public Participacion() {
        initComponents();
        setupInitialState();
        loadPlayerData();
    }

    private void setupInitialState() {
        id.setEditable(false);
        nombre.setEditable(false);
    }

    private void loadPlayerData() {
        JugadorDatos jugador = SesionJugador.getJugador();
        if (jugador != null) {
            id.setText(String.valueOf(jugador.getId()));
            nombre.setText(jugador.getNombre());
        }
    }

    public void setBaseDatos(BaseDeDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setBackground(BACKGROUND_COLOR);
        
        jPanel1 = new JPanel();
        jPanel1.setBackground(BACKGROUND_COLOR);
        
        jLabel2 = new JLabel("Registro");
        jLabel2.setFont(TITLE_FONT);
        jLabel2.setForeground(PRIMARY_COLOR);
        
        forma = new JComboBox<>(new String[]{"Seleccione", "Solidario", "Estandar"});
        forma.setBackground(Color.WHITE);
        forma.setFont(REGULAR_FONT);
        forma.setForeground(Color.BLACK);
        
        jLabel1 = new JLabel("Elegir forma de participación: ");
        jLabel1.setFont(REGULAR_FONT);
        jLabel1.setForeground(PRIMARY_COLOR);
        
        jLabel3 = new JLabel("ID: ");
        jLabel3.setFont(REGULAR_FONT);
        jLabel3.setForeground(PRIMARY_COLOR);
        
        jLabel5 = new JLabel("Nombre: ");
        jLabel5.setFont(REGULAR_FONT);
        jLabel5.setForeground(PRIMARY_COLOR);
        
        id = new JTextField();
        id.setBackground(Color.WHITE);
        id.setFont(REGULAR_FONT);
        
        nombre = new JTextField();
        nombre.setBackground(Color.WHITE);
        nombre.setFont(REGULAR_FONT);
        
        registrar = new JButton("Registrarse");
        registrar.setBackground(BUTTON_COLOR);
        registrar.setForeground(WHITE_COLOR);
        registrar.setFont(REGULAR_FONT);
        registrar.setBorderPainted(false);
        registrar.setFocusPainted(false);
        registrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        registrar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registrar.setBackground(new Color(41, 128, 185)); 
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registrar.setBackground(BUTTON_COLOR);
            }
        });
        
        registrar.addActionListener(evt -> registrarActionPerformed(evt));

        jPanel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(jLabel2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        jPanel1.add(jLabel3, gbc);

        gbc.gridx = 1;
        jPanel1.add(id, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        jPanel1.add(jLabel5, gbc);

        gbc.gridx = 1;
        jPanel1.add(nombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        jPanel1.add(jLabel1, gbc);

        gbc.gridx = 1;
        jPanel1.add(forma, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        jPanel1.add(registrar, gbc);

        setLayout(new BorderLayout());
        add(jPanel1, BorderLayout.CENTER);
    }

    private void registrarActionPerformed(java.awt.event.ActionEvent evt) {
        String idJugador = this.id.getText();
        String tipoForma = (String) this.forma.getSelectedItem();
        
        int formaId = getFormaId(tipoForma);
        
        if (formaId == 0) {
            mostrarMensaje("Debe seleccionar una opcion para la forma de participacion");
            return;
        }

        registrarParticipacion(idJugador, formaId);
    }

    private int getFormaId(String tipoForma) {
        if (tipoForma == null) return 0;
        
        return switch (tipoForma.toUpperCase()) {
            case "SOLIDARIO" -> 2;
            case "ESTANDAR" -> 1;
            default -> 0;
        };
    }

    private void registrarParticipacion(String idJugador, int formaId) {
        JugadorModel modeloJugador = new JugadorModel(this.baseDatos);
        boolean registrado = modeloJugador.RegistrarParticipacion(
            Integer.parseInt(idJugador), 
            formaId
        );

        mostrarMensaje((registrado ? "S" : "No s") + "e ha registrado su participacion en el sistema");
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public JComboBox<String> getForma() { return forma; }
    public void setForma(JComboBox<String> forma) { this.forma = forma; }
    public JTextField getId() { return id; }
    public void setId(String id) { this.id.setText(id); this.repaint(); }
    public JTextField getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre.setText(nombre); this.repaint(); }
    public JButton getRegistrar() { return registrar; }
    public void setRegistrar(JButton registrar) { this.registrar = registrar; }
}