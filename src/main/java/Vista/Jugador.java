package Vista;

import Datos.JugadorDatos;
import Modelo.BaseDeDatos;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class Jugador extends JFrame {
    private JugadorDatos usuarioInfo;
    private BaseDeDatos baseDatos;
    
    private JPanel jPanel1;
    private JPanel Panel2;
    private JButton btnCalificar;
    private JButton btnRegistro;
    private JButton BtnSalir;
    private JLabel jLabel1;

    public Jugador() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        jPanel1 = new JPanel();
        Panel2 = new JPanel();
        btnCalificar = new JButton();
        jLabel1 = new JLabel();
        btnRegistro = new JButton();
        BtnSalir = new JButton();

        jPanel1.setBackground(new java.awt.Color(180, 230, 250));
        Panel2.setBackground(java.awt.Color.WHITE);
        
        setupButton(btnCalificar, "Calificar", new java.awt.Color(52, 160, 219));
        setupButton(btnRegistro, "Registrar participación", new java.awt.Color(52, 160, 219));
        setupButton(BtnSalir, "SALIR", new java.awt.Color(231, 76, 60)); // Botón rojo para salir

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24));
        jLabel1.setForeground(new java.awt.Color(52, 160, 219));
        jLabel1.setText("Jugador");

        setupLayout();
        
        setupListeners();

        pack();
    }

    private void setupButton(JButton button, String text, java.awt.Color backgroundColor) {
        button.setBackground(backgroundColor);
        button.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        button.setForeground(java.awt.Color.WHITE);
        button.setText(text);
        button.setBorder(null);
        button.setFocusPainted(false);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button == btnCalificar || button == btnRegistro) {
                    button.setBackground(new java.awt.Color(41, 128, 185)); // Tono más oscuro de azul
                } else if (button == BtnSalir) {
                    button.setBackground(new java.awt.Color(192, 57, 43)); // Tono más oscuro de rojo
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
    }

    private void setupLayout() {
        GroupLayout Panel2Layout = new GroupLayout(Panel2);
        Panel2.setLayout(Panel2Layout);
        Panel2Layout.setHorizontalGroup(
            Panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );
        Panel2Layout.setVerticalGroup(
            Panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegistro, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel1))
                    .addComponent(BtnSalir, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCalificar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel2, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(14, 14, 14)
                .addComponent(btnRegistro, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCalificar, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addComponent(BtnSalir, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(Panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }

    private void setupListeners() {
        btnCalificar.addActionListener(e -> {
            Calificacion cl = new Calificacion();
            cl.setUsuarioInfo(this.usuarioInfo);
            cl.setBaseDatos(this.baseDatos);
            ShowPanel(cl);
        });

        btnRegistro.addActionListener(e -> {
            Participacion p = new Participacion();
            p.setId(String.valueOf(this.usuarioInfo.getId()));
            p.setNombre(this.usuarioInfo.getNombre());
            p.setBaseDatos(this.baseDatos);
            ShowPanel(p);
        });

        BtnSalir.addActionListener(e -> dispose());
    }

    private void ShowPanel(JPanel p) {
        p.setLayout(new GridLayout(1, 1));
        p.setSize(551, 521);
        p.setLocation(0, 0);

        Panel2.removeAll();
        Panel2.add(p, BorderLayout.CENTER);
        pack();
        Panel2.repaint();
    }

    public JugadorDatos getUsuarioInfo() { return usuarioInfo; }
    public void setUsuarioInfo(JugadorDatos usuarioInfo) { this.usuarioInfo = usuarioInfo; }
    public void setBaseDatos(BaseDeDatos baseDatos) { this.baseDatos = baseDatos; }
    public JButton getBtnSalir() { return BtnSalir; }
    public void setBtnSalir(JButton BtnSalir) { this.BtnSalir = BtnSalir; }
}