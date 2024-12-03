package Util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {
    private static final String USUARIO = "santiagogomez3102@gmail.com";
    private static final String CLAVE = "eidt pfne ivae goof";

    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        // Configuración de propiedades para Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Sesión con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USUARIO, CLAVE);
            }
        });

        try {
            // Crear mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USUARIO));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);

            // Enviar mensaje
            Transport.send(message);

            System.out.println("Correo enviado exitosamente a: " + destinatario);
        } 
        catch (MessagingException e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
        }
    }
}