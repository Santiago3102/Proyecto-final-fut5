package Vista;

import Datos.EquiposDatos;
import Datos.JugadorDatos;
import Modelo.BaseDeDatos;
import Modelo.PartidosModel;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class CrearPartido extends JPanel {
   
    private static final Color PRIMARY_COLOR = new Color(52, 160, 219); // Button background color
    private static final Color SECONDARY_COLOR = new Color(180, 230, 250); // Scroll pane, text field background
    private static final Color TEXT_COLOR = new Color(0, 51, 102);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.PLAIN, 18);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private BaseDeDatos baseDatos;
    private List<JugadorDatos> jugadores;
    private EquiposDatos equipos;
    private final JTable equipoATable;
    private final JTable equipoBTable;
    private final JTextField fechaField;
    private final DefaultTableModel equipoAModel;
    private final DefaultTableModel equipoBModel;

    public CrearPartido() {
        equipoAModel = createTableModel();
        equipoBModel = createTableModel();
        equipoATable = createTable(equipoAModel);
        equipoBTable = createTable(equipoBModel);
        fechaField = createStyledTextField();

        initComponents();
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        JButton btnListar = createStyledButton("Listar equipos", e -> listarEquipos());
        JButton btnGenerar = createStyledButton("Generar partido", e -> generarPartido());
        JLabel labelEquipoA = createStyledLabel("Equipo A", REGULAR_FONT);
        JLabel labelEquipoB = createStyledLabel("Equipo B", REGULAR_FONT);
        JLabel labelFecha = createStyledLabel("Fecha juego:", HEADER_FONT);
        JScrollPane scrollA = new JScrollPane(equipoATable);
        scrollA.setBackground(SECONDARY_COLOR);
        JScrollPane scrollB = new JScrollPane(equipoBTable);
        scrollB.setBackground(SECONDARY_COLOR);

        layout.setHorizontalGroup(layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                .addGap(6)
                .addGroup(layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnListar, 265, 265, 265)
                        .addGap(27)
                        .addComponent(btnGenerar, 265, 265, 265))
                    .addComponent(scrollA, 540, 540, 540)
                    .addComponent(scrollB, 540, 540, 540)
                    .addComponent(labelEquipoB)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelEquipoA)
                        .addGap(139)
                        .addComponent(labelFecha)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechaField, 161, 161, 161))))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(btnListar, 58, 58, 58)
                .addComponent(btnGenerar, 58, 58, 58))
            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelEquipoA)
                .addComponent(labelFecha)
                .addComponent(fechaField, 27, 27, 27))
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(scrollA, 166, 166, 166)
            .addGap(18)
            .addComponent(labelEquipoB)
            .addGap(5)
            .addComponent(scrollB, 143, 143, 143)
            .addGap(61)
        );

        add(mainPanel, BorderLayout.CENTER);
    }

    private DefaultTableModel createTableModel() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Promedio");
        return model;
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(Color.WHITE);
        table.setFont(REGULAR_FONT);
        table.getTableHeader().setFont(REGULAR_FONT);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setGridColor(Color.GRAY);
        return table;
    }

    private JButton createStyledButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(REGULAR_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE); 
        button.setBorder(null);
        button.addActionListener(listener);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(HEADER_FONT);
        field.setBackground(Color.WHITE); 
        field.setForeground(Color.BLACK);
        return field;
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private void listarEquipos() {
        PartidosModel modeloPartido = new PartidosModel(this.baseDatos);
        equipos = modeloPartido.GenerarAlineaciones(this.jugadores);
        actualizarTablas(equipos);
    }

    private void generarPartido() {
        String fechaStr = fechaField.getText().trim();
        if (fechaStr.isEmpty()) {
            mostrarMensaje("Debe indicar la fecha para el juego");
            return;
        }

        PartidosModel modeloPartido = new PartidosModel(this.baseDatos);
        if (modeloPartido.CodigoUltimoPartidoActivo() != 0) {
            mostrarMensaje("No se puede crear partido ya que ya existe uno activo");
            return;
        }

        if (equipos == null || equipos.getEquipoA().size() < 5 || equipos.getEquipoB().size() < 5) {
            mostrarMensaje("Cantidad de jugadores no alcanzada aun");
            return;
        }

        registrarEquipos(fechaStr);
    }

    private void registrarEquipos(String fecha) {
        PartidosModel modeloPartidos = new PartidosModel(this.baseDatos);
        
        for (JugadorDatos jugador : equipos.getEquipoA()) {
            modeloPartidos.AsignarJugadorEquipo(1, jugador.getId());
        }
        
        for (JugadorDatos jugador : equipos.getEquipoB()) {
            modeloPartidos.AsignarJugadorEquipo(2, jugador.getId());
        }

        boolean registrado = modeloPartidos.InsertarPartido(fecha, 1, 2);
        mostrarMensaje((registrado ? "S" : "No s") + "e ha registrado la información en el sistema");
    }

    private void actualizarTablas(EquiposDatos datos) {
        actualizarModelo(equipoAModel, datos.getEquipoA());
        actualizarModelo(equipoBModel, datos.getEquipoB());
        equipoATable.repaint();
        equipoBTable.repaint();
    }

    private void actualizarModelo(DefaultTableModel model, List<JugadorDatos> jugadores) {
        model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn("id");
        model.addColumn("nombre");
        model.addColumn("promedio");
        
        for (JugadorDatos jugador : jugadores) {
            model.addRow(new Object[]{
                jugador.getId(),
                jugador.getNombre(),
                jugador.getPromedio()
            });
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void setBaseDatos(BaseDeDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    public void setJugadores(List<JugadorDatos> jugadores) {
        this.jugadores = jugadores;
    }
}