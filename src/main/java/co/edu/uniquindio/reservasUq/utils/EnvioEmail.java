package co.edu.uniquindio.reservasUq.utils;

import co.edu.uniquindio.reservasUq.servicio.Notificacion;
import lombok.AllArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

@AllArgsConstructor
public class EnvioEmail implements Notificacion {

    private String destinatario, asunto, mensaje;


    @Override
    public void enviarNotificacion() {


        Email email = EmailBuilder.startingBlank()
                .from("bookyourstay34@gmail.com")
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje)
                .buildEmail();


        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "bookyourstay34@gmail.com", "dhwl lyht ucbg zgmh")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {


            mailer.sendMail(email);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}