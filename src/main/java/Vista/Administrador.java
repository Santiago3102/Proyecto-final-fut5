package Vista;

import Datos.JugadorDatos;
import Modelo.AmonestacionesModel;
import Modelo.BaseDeDatos;
import Modelo.JugadorModel;

import java.awt.*;
import javax.swing.*;

public class Administrador extends JFrame {

    private JugadorDatos usuarioInfo;
    private BaseDeDatos baseDatos;
    private JButton btnCrear;
    private JButton btnSancionar;
    private JButton btnAgregar;
    private JButton btnListarA;
    private JButton SalirBtn;
    private JButton btnFinalizarPartido;
    private JPanel Panel;

    public Administrador() {
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

    public JButton getSalirBtn() {
        return SalirBtn;
    }

    public void setSalirBtn(JButton SalirBtn) {
        this.SalirBtn = SalirBtn;
    }

    public JButton getBtnFinalizarPartido() {
        return btnFinalizarPartido;
    }

    public void setBtnFinalizarPartido(JButton btnFinalizarPartido) {
        this.btnFinalizarPartido = btnFinalizarPartido;
    }

    private void initComponents() {
        btnCrear = new JButton("Crear Partido");
        btnSancionar = new JButton("Menú sanciones");
        btnAgregar = new JButton("Agregar Jugador");
        btnListarA = new JButton("Listar Amonestaciones");
        SalirBtn = new JButton("SALIR");
        btnFinalizarPartido = new JButton("Finalizar Partido");
        
        Panel = new JPanel();

        Color primaryColor = new Color(52, 160, 219);       
        Color textColor = Color.WHITE;
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);

        JButton[] buttons = {btnCrear, btnSancionar, btnAgregar, btnListarA, btnFinalizarPartido};
        for (JButton button : buttons) {
            button.setBackground(primaryColor);
            button.setForeground(textColor);
            button.setFont(buttonFont);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(41, 128, 185));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(primaryColor);
                }
            });
        }

        // Estilo específico para botón de Salir
        SalirBtn.setBackground(new Color(231, 76, 60));  // Rojo
        SalirBtn.setForeground(Color.WHITE);
        SalirBtn.setFont(buttonFont);
        SalirBtn.setFocusPainted(false);
        SalirBtn.setBorderPainted(false);
        SalirBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SalirBtn.setBackground(new Color(192, 57, 43));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SalirBtn.setBackground(new Color(231, 76, 60));
            }
        });

        btnCrear.addActionListener(evt -> btnCrearActionPerformed(evt));
        btnSancionar.addActionListener(evt -> btnSancionarActionPerformed(evt));
        btnAgregar.addActionListener(evt -> btnAgregarActionPerformed(evt));
        btnListarA.addActionListener(evt -> btnListarAActionPerformed(evt));
        SalirBtn.addActionListener(evt -> SalirBtnActionPerformed(evt));
        btnFinalizarPartido.addActionListener(evt -> btnFinalizarPartidoActionPerformed(evt));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1, 10, 10));
        leftPanel.setBackground(new Color(180, 230, 250)); 
        
        JLabel titleLabel = new JLabel("Administrador", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        leftPanel.add(titleLabel);

        leftPanel.add(btnCrear);
        leftPanel.add(btnFinalizarPartido);
        leftPanel.add(btnSancionar);
        leftPanel.add(btnAgregar);
        leftPanel.add(btnListarA);
        leftPanel.add(SalirBtn);

        Panel.setBackground(Color.WHITE);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(Panel, BorderLayout.CENTER);

        setTitle("Panel de Administración");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {
        CrearPartido c = new CrearPartido();
        c.setBaseDatos(this.baseDatos);
        JugadorModel modeloJugador = new JugadorModel(this.baseDatos);
        c.setJugadores(modeloJugador.ListadoJugadores());
        ShowPanel(c);
    }

    private void btnSancionarActionPerformed(java.awt.event.ActionEvent evt) {
        Sancionar s = new Sancionar();
        s.setBaseDatos(this.baseDatos);
        ShowPanel(s);
    }

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        AgregarJugador aj = new AgregarJugador();
        aj.setBaseDatos(this.baseDatos);
        aj.setUsuarioInfo(this.usuarioInfo);
        ShowPanel(aj);
    }

    private void btnListarAActionPerformed(java.awt.event.ActionEvent evt) {
        ListarAmonestacion la = new ListarAmonestacion();
        la.setBaseDatos(this.baseDatos);
        AmonestacionesModel modeloAmonestaciones = new AmonestacionesModel(this.baseDatos);
        la.llenarTabla(modeloAmonestaciones.ListadoAmonestaciones());
        ShowPanel(la);
    }

    private void SalirBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private void btnFinalizarPartidoActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Partido finalizado");
    }

    private void ShowPanel(JPanel p) {
        p.setLayout(new GridLayout(1, 1));
        p.setSize(581, 530);
        p.setLocation(0, 0);

        Panel.removeAll();
        Panel.add(p, BorderLayout.CENTER);
        pack();
        Panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Administrador().setVisible(true);
        });
    }
}