package Vista;

import Datos.AmonestacionesDatos;
import Modelo.AmonestacionesModel;
import Modelo.BaseDeDatos;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarAmonestacion extends JPanel {
    private BaseDeDatos baseDatos;
    private final DefaultTableModel model;
    
    private JTable TableAmonestados;
    private JButton eliminarBtn;
    private JTextField idSancion;
    private JPanel jPanel1;
    private JLabel titleLabel;
    private JLabel idLabel;

    public ListarAmonestacion() {
        model = new DefaultTableModel();
        initComponents();
    }

    private void initComponents() {
        jPanel1 = new JPanel();
        TableAmonestados = new JTable();
        titleLabel = new JLabel("Lista de jugadores amonestados");
        eliminarBtn = new JButton("Eliminar sanción");
        idSancion = new JTextField();
        idLabel = new JLabel("ID sancion: ");
        JScrollPane scrollPane = new JScrollPane(TableAmonestados);
        
        setupStyles();
        
        setupLayout(scrollPane);
        
        eliminarBtn.addActionListener(e -> eliminarSancion());
    }

    private void setupStyles() {
        jPanel1.setBackground(new java.awt.Color(180, 230, 250));
        
        titleLabel.setFont(new java.awt.Font("Segoe UI", 0, 24));
        titleLabel.setForeground(new java.awt.Color(52, 160, 219));
        
        eliminarBtn.setBackground(new java.awt.Color(52, 160, 219));
        eliminarBtn.setFont(new java.awt.Font("Segoe UI", 0, 14));
        eliminarBtn.setForeground(java.awt.Color.WHITE); 
        
        idSancion.setBackground(java.awt.Color.WHITE);
        idSancion.setFont(new java.awt.Font("Segoe UI", 0, 18));
        idSancion.setForeground(java.awt.Color.BLACK);
        idSancion.setHorizontalAlignment(JTextField.CENTER);
        
        idLabel.setFont(new java.awt.Font("Segoe UI", 0, 18));
        idLabel.setForeground(new java.awt.Color(52, 160, 219));
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setupLayout(JScrollPane scrollPane) {
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(127, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(eliminarBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(idSancion)
                    .addComponent(idLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 453, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(idLabel)
                        .addGap(18, 18, 18)
                        .addComponent(idSancion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(eliminarBtn)
                        .addGap(86, 86, 86))))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    private void eliminarSancion() {
        String sancionId = this.idSancion.getText();
        if (sancionId == null || sancionId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe indicar el identificador si desea eliminar una sancion.");
            return;
        }
        
        AmonestacionesModel modeloAmonestaciones = new AmonestacionesModel(this.baseDatos);
        boolean registrado = modeloAmonestaciones.quitarAmonestacionJugador(Integer.parseInt(sancionId));
        
        JOptionPane.showMessageDialog(null, (registrado ? "S" : "No s") + "e ha actualizado la informacion en el sistema");
        JOptionPane.showMessageDialog(null, "Para actualizar la informacion de click sobre el boton del menu para actualizar");
        
        this.idSancion.setText("");
    }

    public void llenarTabla(List<AmonestacionesDatos> datos) {
        model.setRowCount(0);
        model.setColumnCount(0);
        
        String[] columnas = {"id", "id jugador", "nombre jugador", "id partido", "fecha partido"};
        for (String columna : columnas) {
            model.addColumn(columna);
        }
        
        for (AmonestacionesDatos registro : datos) {
            model.addRow(new Object[]{
                registro.getId(),
                registro.getIdPersona(),
                registro.getNombreJugador(),
                registro.getIdPartido(),
                registro.getFechaJuego()
            });
        }
        
        this.TableAmonestados.setModel(model);
    }

    public void setBaseDatos(BaseDeDatos baseDatos) {
        this.baseDatos = baseDatos;
    }
}