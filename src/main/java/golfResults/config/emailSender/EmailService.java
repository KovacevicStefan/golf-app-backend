package golfResults.config.emailSender;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender emailSender;

    public void sendEmail(Email email){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("kovacevic-stefan@hotmail.com");
        message.setSubject(email.subject());
        message.setText("Ime i prezime: "+email.name()+" "+email.lastName()+"\nEmail: "+email.email()+"\n\n"+ email.message());

        emailSender.send(message);

        System.out.println("Message sent successfully");
    }

    public void sendVerificationEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        emailSender.send(message);
    }

}