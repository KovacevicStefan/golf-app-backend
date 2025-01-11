package golfResults;

import golfResults.config.emailSender.EmailSendService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GolfResultsApplication {

	private EmailSendService smtp;

	public static void main(String[] args) {
		SpringApplication.run(GolfResultsApplication.class, args);
	}

}
