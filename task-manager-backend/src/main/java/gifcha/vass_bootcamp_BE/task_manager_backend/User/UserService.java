package gifcha.vass_bootcamp_BE.task_manager_backend.User;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

    User addUser(User user) {
        return userRepository.save(user);
	}

	User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
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
