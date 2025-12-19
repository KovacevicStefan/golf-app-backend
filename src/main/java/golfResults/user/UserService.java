package golfResults.user;

import golfResults.exception.types.ResourceNotFoundException;
import golfResults.user.dto.UserResponseDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserResponseDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return UserResponseDTO.builder()
                .id(currentUser.getId()).email(currentUser.getEmail()).firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName()).username(currentUser.getUsername()).image(currentUser.getImage()).build();
    }

    public UserResponseDTO getUserById(Long id) {
        return userDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user with email: " + email));
    }

    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user with username: " + username));
        return userDto(user);
    }

    @Transactional
    public User saveUser(Long id, User user) {
        if (id != null && userRepository.existsById(id)) {
            user.setId(id);
        } else {
            user.setId(null);
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User tournament = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find and delete user with id = " + id));
        userRepository.deleteById(id);
    }

    public UserResponseDTO userDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .image(user.getImage())
                .enabled(user.isEnabled())
                .build();
    }

    public Boolean doesUserExist(Long id) {
        return userRepository.existsById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
