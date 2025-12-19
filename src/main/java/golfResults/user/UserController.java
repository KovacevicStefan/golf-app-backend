package golfResults.user;

import golfResults.user.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponseDTO getAuthenticatedUser() {
        return userService.getAuthenticatedUser();
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    public UserResponseDTO getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User savedUser = userService.saveUser(id, user); // Promeni kasnije
        HttpStatus status = userService.doesUserExist(id) ? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<>(savedUser, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @DeleteMapping("/delete_all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

}
