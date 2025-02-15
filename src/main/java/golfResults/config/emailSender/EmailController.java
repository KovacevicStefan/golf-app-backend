package golfResults.config.emailSender;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api")
public class EmailController {

    private EmailService emailService;

    @PostMapping("/email")
    public void sendEmail(@RequestBody Email email) {
        emailService.sendEmail(email);
    }
}
