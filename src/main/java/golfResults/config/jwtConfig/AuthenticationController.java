package golfResults.config.jwtConfig;

import golfResults.user.*;
import golfResults.user.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(dto));
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO dto) {
        return new LoginResponseDTO(
                jwtService.generateToken(authService.authenticate(dto)),
                jwtService.getExpirationTime());
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyDTO dto) {
        authService.verifyUser(dto);
        return ResponseEntity.ok(Map.of(
                "message", "Account verified successfully."
            ) //izmeni obavezno
        );
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resend(@RequestParam String email) {
        authService.resendVerificationCode(email);
        return ResponseEntity.ok(Map.of(
                "message", "Verification code sent"
        ));
    }
}