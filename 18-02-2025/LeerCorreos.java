import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;

public class LeerCorreos {
    public static void main(String[] args) {
        // **NO almacenar credenciales en el código**
        final String username = System.getenv("EMAIL_USERNAME"); // Usa variable de entorno
        final String password = System.getenv("EMAIL_PASSWORD"); // Usa variable de entorno

        if (username == null || password == null) {
            System.err.println("Error: Credenciales no configuradas. Usa variables de entorno.");
            return;
        }

        // Configuración de propiedades para IMAP con Gmail
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        try {
            // Iniciar sesión
            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");
            store.connect(username, password);

            // Acceder a la bandeja de entrada
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Obtener solo los mensajes no leídos
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            System.out.println("Número de correos NO LEÍDOS en INBOX: " + messages.length);

            // Leer los correos
            for (Message message : messages) {
                System.out.println("--------------------------------------------------");
                System.out.println("Asunto: " + message.getSubject());
                System.out.println("De: " + InternetAddress.toString(message.getFrom()));
                System.out.println("Fecha: " + message.getSentDate());

                // Manejo adecuado del contenido del correo
                Object content = message.getContent();
                if (content instanceof String) {
                    System.out.println("Contenido: " + content);
                } else if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        System.out.println("Parte del contenido: " + bodyPart.getContent());
                    }
                } else {
                    System.out.println("Tipo de contenido no compatible.");
                }
            }

            // Cerrar la conexión
            inbox.close(false);
            store.close();
        } catch (MessagingException e) {
            System.err.println("Error al conectar con el servidor de correo: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error al leer el contenido del correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
