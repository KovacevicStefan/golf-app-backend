package golfResults.config.jwtConfig;

import golfResults.config.emailSender.EmailService;
import golfResults.exception.types.AuthorizationException;
import golfResults.user.*;
import golfResults.user.dto.LoginDTO;
import golfResults.user.dto.RegisterDTO;
import golfResults.user.dto.VerifyDTO;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public User signUp(RegisterDTO input) {

        if (userRepository.existsByEmail(input.email())) {
            throw new AuthorizationException(
                    HttpStatus.BAD_REQUEST,
                    "Email is already in use"
            );
        }

        if (userRepository.existsByUsername(input.username())) {
            throw new AuthorizationException(
                    HttpStatus.BAD_REQUEST,
                    "Username is already taken"
            );
        }

        String encodedPassword = passwordEncoder.encode(input.password());

        User user = User.builder()
                .firstName(input.firstName())
                .lastName(input.lastName())
                .email(input.email())
                .username(input.username())
                .password(encodedPassword)
                .image(input.image())
                .role(Role.ROLE_USER)
                .build();

        user.setVerificationCode(UUID.randomUUID().toString());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        sendVerificationEmail(user);

        return userRepository.save(user);
    }

    public User authenticate(LoginDTO input) {
        User user = userRepository.findByUsername(input.username())
                .orElseThrow(() -> new AuthorizationException(HttpStatus.UNAUTHORIZED, "Bad credentials"));

        if (!passwordEncoder.matches(input.password(), user.getPassword())) {
            throw new AuthorizationException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }

        if (!user.isEnabled()) {
            throw new AuthorizationException(HttpStatus.FORBIDDEN, "Account not verified. Please verify your account.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.username(), input.password())
        );

        return user;
    }

    @Transactional
    public void verifyUser(VerifyDTO input) {
        User user = userRepository.findByVerificationCode(input.token())
                .orElseThrow(() -> new AuthorizationException(
                        HttpStatus.BAD_REQUEST, "Invalid verification code"));

        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AuthorizationException(
                    HttpStatus.BAD_REQUEST, "Verification code has expired");
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiresAt(null);

        userRepository.save(user);
    }

    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.isEnabled()) {
                throw new AuthorizationException(HttpStatus.FORBIDDEN, "Account is already activated.");
            }
            user.setVerificationCode(UUID.randomUUID().toString());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        } else {
            throw new AuthorizationException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    private void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String activationLink = "http://localhost:4200/activate?token=" + user.getVerificationCode();

        String htmlMessage =
                "<html>" +
                        "<body style=\"margin:0; padding:0; font-family:Arial, sans-serif;\">" +

                        "<div style=\"max-width:600px; margin:40px auto; background-color:#ffffff; " +
                        "border-radius:8px; overflow:hidden; box-shadow:0 4px 12px rgba(0,0,0,0.1);\">" +

                        "<div style=\"padding:24px; text-align:center; background-color:#34352a; color:#ffffff;\">" +
                        "<h1 style=\"margin:0; font-size:24px;\">Welcome!</h1>" +
                        "</div>" +

                        "<div style=\"padding:32px; text-align:center;\">" +
                        "<p style=\"font-size:16px; color:#333333; margin-bottom:24px;\">" +
                        "Thank you for creating an account. Please confirm your email address by clicking the button below." +
                        "</p>" +

                        "<a href=\"" + activationLink + "\" " +
                        "style=\"display:inline-block; padding:14px 28px; background-color:#34352a; color:#ffffff; " +
                        "text-decoration:none; font-size:16px; font-weight:bold; border-radius:6px;\">" +
                        "Activate Account" +
                        "</a>" +

                        "<p style=\"font-size:14px; color:#666666; margin-top:32px;\">" +
                        "If you did not create this account, you can safely ignore this email." +
                        "</p>" +
                        "</div>" +

                        "<div style=\"padding:16px; text-align:center; background-color:#34352a; font-size:12px; color:#ffffff;\">" +
                        "© 2025 Golf Results" +
                        "</div>" +

                        "</div>" +
                        "</body>" +
                        "</html>";


        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
