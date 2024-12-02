package Vista;

import Datos.JugadorDatos;
import Modelo.AmonestacionesModel;
import Modelo.BaseDeDatos;
import Modelo.JugadorModel;
import Modelo.PartidosModel;
import javax.swing.*;
import java.awt.*;

public class Sancionar extends javax.swing.JPanel {
    
    private static final Color PRIMARY_COLOR = new Color(52, 160, 219);
    private static final Color TEXT_COLOR = new Color(0, 51, 102);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private JugadorDatos usuarioSancionar;
    private BaseDeDatos baseDatos;
    
    private JPanel Sanciones;
    private JPanel dataPanel;
    private JTextField idJUgador;
    private JTextField nombre;
    private JTextField suscripcion;
    private JTextField penalizacion;
    private JTextField promedio;
    private JTextField partido;
    private JButton btnBuscar;
    private JButton btnSancionar;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;

    public Sancionar() {
        initComponents();
    }

    public void setBaseDatos(BaseDeDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    private void initComponents() {
      
        Sanciones = new JPanel();
        dataPanel = new JPanel();
        idJUgador = new JTextField();
        nombre = new JTextField();
        suscripcion = new JTextField();
        penalizacion = new JTextField();
        promedio = new JTextField();
        partido = new JTextField();
        btnBuscar = new JButton();
        btnSancionar = new JButton();
        
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();

        setupPanels();
        setupFields();
        setupLabels();
        setupButtons();
        layoutComponents();
        
        nombre.setEditable(false);
        promedio.setEditable(false);
        suscripcion.setEditable(false);
        penalizacion.setEditable(false);
    }

    private void setupPanels() {
        setBackground(Color.WHITE);
        Sanciones.setBackground(Color.WHITE);
        dataPanel.setBackground(Color.WHITE);
        dataPanel.setBorder(BorderFactory.createTitledBorder(null, "Datos del jugador", 
            javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new Font("Dialog", 0, 11), Color.BLACK));
    }

    private void setupFields() {
        JTextField[] fields = {idJUgador, nombre, suscripcion, penalizacion, promedio, partido};
        for (JTextField field : fields) {
            field.setBackground(Color.WHITE);
            field.setFont(REGULAR_FONT);
            field.setForeground(Color.BLACK);
            field.setHorizontalAlignment(JTextField.LEFT);
        }
    }

    private void setupLabels() {
        jLabel1.setFont(HEADER_FONT);
        jLabel1.setForeground(TEXT_COLOR);
        jLabel1.setText("ID Jugador: ");

        JLabel[] labels = {jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7};
        String[] texts = {"Nombre: ", "", "Tipo de suscripción: ", "Penalizaciones: ", 
                         "Promedio: ", "Partido"};
        
        for (int i = 0; i < labels.length; i++) {
            labels[i].setFont(REGULAR_FONT);
            labels[i].setForeground(TEXT_COLOR);
            labels[i].setText(texts[i]);
        }
    }

    private void setupButtons() {
        btnBuscar.setBackground(PRIMARY_COLOR);
        btnBuscar.setFont(REGULAR_FONT);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(null);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(evt -> btnBuscarActionPerformed(evt));

        btnSancionar.setBackground(PRIMARY_COLOR);
        btnSancionar.setForeground(Color.WHITE);
        btnSancionar.setText("Sancionar");
        btnSancionar.setBorder(null);
        btnSancionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSancionar.addActionListener(evt -> btnSancionarActionPerformed(evt));
    }

    private void layoutComponents() {
        GroupLayout dataPanelLayout = new GroupLayout(dataPanel);
        dataPanel.setLayout(dataPanelLayout);
        
        dataPanelLayout.setHorizontalGroup(
            dataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(dataPanelLayout.createSequentialGroup()
                .addGap(24)
                .addGroup(dataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(dataPanelLayout.createSequentialGroup()
                        .addGroup(dataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2))
                        .addGap(12)
                        .addGroup(dataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(dataPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(promedio, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
                                .addComponent(penalizacion, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
                                .addComponent(suscripcion, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
                                .addComponent(partido, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
                                .addComponent(nombre, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnSancionar, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        
        dataPanelLayout.setVerticalGroup(
            dataPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(dataPanelLayout.createSequentialGroup()
                .addGap(28)
                .addGroup(dataPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(20)
                .addGroup(dataPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(suscripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18)
                .addGroup(dataPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(penalizacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18)
                .addGroup(dataPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(promedio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dataPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(partido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(52)
                .addComponent(btnSancionar, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                .addGap(20))
        );

        GroupLayout mainLayout = new GroupLayout(Sanciones);
        Sanciones.setLayout(mainLayout);
        
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addGap(40)
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(dataPanel, GroupLayout.PREFERRED_SIZE, 487, GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(49)
                        .addComponent(idJUgador, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
                        .addGap(38)
                        .addComponent(btnBuscar, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addGap(63)
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(idJUgador, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                .addGap(35)
                .addComponent(dataPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(Sanciones, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Sanciones, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idJUgador.getText() == null || idJUgador.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el identificador del jugador a sancionar");
            return;
        }
        
        try {
            JugadorModel modeloJugador = new JugadorModel(this.baseDatos);
            this.usuarioSancionar = modeloJugador.ConsultarInformacionJugador(
                Integer.parseInt(idJUgador.getText()));

            nombre.setText(this.usuarioSancionar.getNombre());
            penalizacion.setText(String.valueOf(this.usuarioSancionar.getCantidadPenalizaciones()));
            promedio.setText(String.valueOf(this.usuarioSancionar.getPromedio()));
            suscripcion.setText(this.usuarioSancionar.getTipoSuscripcion() == 1 ? "FRECUENTE" : "OCACIONAL");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido");
        }
    }

    private void btnSancionarActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.usuarioSancionar == null) {
            JOptionPane.showMessageDialog(null, "Debe consultar al jugador a sancionar");
            return;
        }
        
        if (partido.getText() == null || partido.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe indicar el partido en cuestión");
            return;
        }
        
        try {
            PartidosModel modeloPartido = new PartidosModel(this.baseDatos);
            int idPartido = Integer.parseInt(partido.getText());
            
            if (modeloPartido.ConsultarPartido(idPartido) == 0) {
                JOptionPane.showMessageDialog(null, "Debe indicar un partido válido");
                return;
            }
            
            AmonestacionesModel modeloAmonestar = new AmonestacionesModel(this.baseDatos);
            boolean registrado = modeloAmonestar.amonestarJugador(
                this.usuarioSancionar.getId(), idPartido);

            JOptionPane.showMessageDialog(null, 
                (registrado ? "S" : "No s") + "e ha registrado la información en el sistema");
            
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID del partido debe ser un número válido");
        }
    }

    private void clearFields() {
        nombre.setText("");
        suscripcion.setText("");
        penalizacion.setText("");
        promedio.setText("");
        partido.setText("");
        idJUgador.setText("");
        this.usuarioSancionar = null;
    }
}