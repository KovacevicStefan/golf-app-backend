package golfResults.user;

import golfResults.exception.ResourceNotFoundException;
import golfResults.tournament.Tournament;
import lombok.AllArgsConstructor;
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

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found"));
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

    public User saveUser(Long id, User user) {
        if (id != null && userRepository.existsById(id)) {
            user.setId(id);
        } else {
            user.setId(null);
        }
        return userRepository.save(user);
    }

    public Optional<User> deleteUser(Long id) {
        User tournament = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find and delete user with id = " + id));
        userRepository.deleteById(id);
        return Optional.ofNullable(tournament);
    }

    public UserResponseDTO userDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .image(user.getImage())
                .build();
    }


    public Boolean doesUserExist(Long id) {
        return userRepository.existsById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
