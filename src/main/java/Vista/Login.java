package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends javax.swing.JFrame {
    private final String ruta = "Imagenes/file.png";
    
    public Login() {
        initComponents();
        setTitle("Inicio de Sesión");
        
        ingresarBtn.setBackground(new Color(61, 128, 185));  
        ingresarBtn.setForeground(Color.WHITE);
        ingresarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ingresarBtn.setBorder(BorderFactory.createEmptyBorder());
        
        ingresarBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ingresarBtn.setBackground(new Color(52, 122, 219));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ingresarBtn.setBackground(new Color(41, 128, 185));
            }
        });
        
        user.setBackground(new Color(180, 230, 250));
        pass.setBackground(new Color(180, 230, 250));
        
        jLabel1.setIcon(new ImageIcon(ruta));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        user = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ingresarBtn = new javax.swing.JButton();
        pass = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(421, 500));  

        jPanel1.setBackground(Color.WHITE);

        user.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pass.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jLabel1.setForeground(new Color(0, 51, 102));
        jLabel1.setText("Usuario: ");

        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        jLabel2.setForeground(new Color(0, 51, 102));
        jLabel2.setText("Constraseña: ");

        ingresarBtn.setText("Ingresar");
        ingresarBtn.setBackground(new Color(204, 204, 204));
        ingresarBtn.setForeground(new Color(0, 0, 0));
        ingresarBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(user)
                            .addComponent(pass, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(ingresarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(206, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ingresarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }                                   

    public JButton getIngresarBtn() {
        return ingresarBtn;
    }

    public void setIngresarBtn(JButton ingresarBtn) {
        this.ingresarBtn = ingresarBtn;
    }

    public JPasswordField getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass.setText(pass);
    }

    public JTextField getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user.setText(user);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
                    
    private javax.swing.JButton ingresarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField pass;
    private javax.swing.JTextField user;                  
}
