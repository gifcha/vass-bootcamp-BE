package gifcha.vass_bootcamp_BE.task_manager_backend.User;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    User addUser(User user) {
		String encoded = passwordEncoder.encode(user.getPassword());
		user.setPassword(encoded);
        return userRepository.save(user);
	}

	User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
	}

    List<UserDTO> getUserList() {
		return userRepository.findAll().stream()
            .map(user -> new UserDTO(user))
            .toList();
	}

    User updateUser(UUID id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        return userRepository.save(user);
	}

    void deleteUserById(UUID id) {
        userRepository.deleteById(id);
	}
}
