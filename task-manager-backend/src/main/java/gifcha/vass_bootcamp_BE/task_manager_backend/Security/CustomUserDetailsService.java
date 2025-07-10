package gifcha.vass_bootcamp_BE.task_manager_backend.Security;

import gifcha.vass_bootcamp_BE.task_manager_backend.User.User;
import gifcha.vass_bootcamp_BE.task_manager_backend.User.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserByUsername(String username) {
		return userRepository.getByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}
