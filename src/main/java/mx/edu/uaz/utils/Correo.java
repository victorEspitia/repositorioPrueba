package mx.edu.uaz.utils;

import com.vaadin.ui.Notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by jaetmar on 4/3/17.
 */
public class Correo {

    public static boolean sendMail(String usuario, String mail, String cad, String nombre, String app){
        boolean status = false;
        try{

            Properties propiedades = System.getProperties();
            propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
            propiedades.setProperty("mail.smtp.starttls.enable", "true");
            propiedades.setProperty("mail.smtp.port", "587");
            propiedades.setProperty("mail.smtp.auth", "true");

            Session sesion = Session.getDefaultInstance(propiedades, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("jaetmar@gmail.com", "b0112a792");
                }
            });

            MimeMessage mensaje = new MimeMessage(sesion);

            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            mensaje.setSubject("Cuenta Egresados -confirmación-");
            String html="<!DOCTYPE html><html><head><title>Confirmación</title><meta charset=\"UTF-8\"><style type=\"text/css\">h3{text-align: center;}.texto{text-align: justify;width:60%;float: left;padding: 10px 15px 10px 0px;}"+
                    ".container{width: 80%;margin: 10px auto;font-family: arial;font-size:14px;padding: 20px;background-color: rgb(255, 253, 253);box-shadow: 0px 0px 10px 5px silver;border-radius: 6px;}img {width:35%;}</style></head><body>"+
                    "<div class=\"container\"><h3>Confirmación de la cuenta en el Sistema de Seguimiento de Egresados IS</h3>"+
                    "<div class='texto'>Estimad@ <b>"+nombre+" "+app+":</b> <br> Gracias por registrarte, para completar el proceso es necesario activar tu cuenta haciendo click en el siguiente enlace: <a href=\"http://localhost:8080/Mail?user="+cad+"&logs="+usuario+"\">Confirmación de la cuenta</a></div><div><img src=\"http://vignette3.wikia.nocookie.net/leagueoflegends/images/4/42/Jinx_MafiaSkin.jpg/revision/latest?cb=20141116060337\" ></div><br><br><br><h4>Dudas o comentarios: <a href=\"mailto:\"> amgdark@uaz.edu.mx</a></h4></div></body></html>";
            mensaje.setText(html, "ISO-8859-1", "html");

            Transport.send(mensaje);
            status = true;
        }
        catch (MessagingException e) {
            Notification.show("No se puede enviar el correo de confirmación, error: "+e.getMessage().toString(), Notification.Type.ERROR_MESSAGE);
        } finally {
            return status;
        }
    }
}
