package Vista;

import Datos.JugadorDatos;
import Modelo.BaseDeDatos;
import Modelo.CalificacionModel;
import Modelo.JugadorModel;
import Modelo.PartidosModel;
import javax.swing.*;
import java.awt.*;

public class Calificacion extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(180, 230, 250);
    private static final Color PRIMARY_COLOR = new Color(52, 160, 219);
    private static final Color BUTTON_COLOR = new Color(52, 160, 219);
    private static final Color WHITE_COLOR = Color.WHITE;
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private BaseDeDatos baseDatos;
    private JugadorDatos usuarioInfo;
    private JugadorDatos usuarioCalificado;

    private JTextField idBuscado;
    private JTextField nombre;
    private JTextField calificacion;
    private JTextField observacion;
    private JButton btnBuscar;
    private JButton btnCalificar;

    public Calificacion() {
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        setBackground(WHITE_COLOR);
        
        idBuscado = createTextField(250, 30);  
        nombre = createReadOnlyTextField(200, 25);  
        calificacion = createTextField(100, 25);  
        observacion = createTextField(300, 60);  

        btnBuscar = createButton("Buscar", this::performPlayerSearch);
        btnCalificar = createButton("Calificar", this::performRating);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(WHITE_COLOR);
        
        // Search Section
        JPanel searchPanel = createSearchPanel();
        searchPanel.setBackground(WHITE_COLOR);
        
        // Rating Section
        JPanel ratingPanel = createRatingPanel();
        ratingPanel.setBackground(WHITE_COLOR);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(ratingPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(WHITE_COLOR);
        searchPanel.add(new JLabel("ID Jugador: "));
        searchPanel.add(idBuscado);
        searchPanel.add(btnBuscar);
        return searchPanel;
    }

    private JPanel createRatingPanel() {
        JPanel ratingPanel = new JPanel(new GridBagLayout());
        ratingPanel.setBackground(WHITE_COLOR);
        ratingPanel.setBorder(BorderFactory.createTitledBorder("Calificación"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        ratingPanel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ratingPanel.add(nombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        ratingPanel.add(new JLabel("Calificación (1-10):"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ratingPanel.add(calificacion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        ratingPanel.add(new JLabel("Observaciones:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        ratingPanel.add(new JScrollPane(observacion), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        ratingPanel.add(btnCalificar, gbc);

        return ratingPanel;
    }

    private JTextField createTextField(int width, int height) {
        JTextField textField = new JTextField();
        textField.setBackground(Color.WHITE);
        textField.setPreferredSize(new Dimension(width, height));
        return textField;
    }

    private JTextField createReadOnlyTextField(int width, int height) {
        JTextField textField = createTextField(width, height);
        textField.setEditable(false);
        return textField;
    }

    private JButton createButton(String label, Runnable action) {
        JButton button = new JButton(label);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(WHITE_COLOR);
        button.setFont(REGULAR_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185)); 
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        
        button.addActionListener(e -> action.run());
        return button;
    }

    private void performPlayerSearch() {
        String searchId = idBuscado.getText();
        if (isInvalidInput(searchId)) {
            showMessage("Debe indicar un identificador para realizar la búsqueda");
            return;
        }

        JugadorModel playerModel = new JugadorModel(baseDatos);
        usuarioCalificado = playerModel.ConsultarInformacionJugador(Integer.parseInt(searchId));
        nombre.setText(usuarioCalificado.getNombre());
    }

    private void performRating() {
        if (isInvalidInput(nombre.getText())) {
            showMessage("Debe consultar un jugador para generar la calificación");
            return;
        }

        PartidosModel matchModel = new PartidosModel(baseDatos);
        int lastMatchId = matchModel.CodigoUltimoPartido();
        int activeMatchId = matchModel.CodigoUltimoPartidoActivo();

        if (!validateMatchConditions(lastMatchId, activeMatchId)) return;

        int ratingValue = parseRating();
        if (ratingValue == -1) return;

        String observations = observacion.getText();
        CalificacionModel ratingModel = new CalificacionModel(baseDatos);
        boolean registered = ratingModel.GenerarCalificacion(
            usuarioCalificado.getId(), 
            usuarioInfo.getId(), 
            lastMatchId, 
            ratingValue, 
            observations
        );

        showMessage(registered ? "Se ha registrado la calificación" : "No se pudo registrar la calificación");
        resetForm();
    }

    private boolean validateMatchConditions(int lastMatchId, int activeMatchId) {
        if (lastMatchId <= 0) {
            showMessage("Debe existir un partido para realizar este proceso");
            return false;
        }
        if (lastMatchId != activeMatchId) {
            showMessage("No se puede calificar ya que no hay partidos activos");
            return false;
        }
        return true;
    }

    private int parseRating() {
        String ratingText = calificacion.getText();
        if (isInvalidInput(ratingText)) {
            showMessage("Debe agregar un valor en la calificación");
            return -1;
        }

        int rating = Integer.parseInt(ratingText);
        if (rating < 1 || rating > 10) {
            showMessage("La calificación debe estar entre 1 y 10");
            return -1;
        }
        return rating;
    }

    private boolean isInvalidInput(String input) {
        return input == null || input.trim().isEmpty();
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void resetForm() {
        calificacion.setText("");
        nombre.setText("");
        observacion.setText("");
        idBuscado.setText("");
    }

    public void setBaseDatos(BaseDeDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    public void setUsuarioInfo(JugadorDatos usuarioInfo) {
        this.usuarioInfo = usuarioInfo;
    }
}