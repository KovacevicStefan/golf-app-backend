package golfResults.config.emailSender;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSendService {

    private JavaMailSender emailSender;

    public void sendEmail(Email email){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("kovacevic-stefan@hotmail.com");
        message.setSubject(email.subject());
        message.setText("Ime i prezime: "+email.name()+" "+email.lastName()+"\nEmail: "+email.email()+"\n\n"+ email.message());

        emailSender.send(message);

        System.out.println("Message sent successfully");
    }
}