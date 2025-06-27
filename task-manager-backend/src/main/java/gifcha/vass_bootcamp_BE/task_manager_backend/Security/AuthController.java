package gifcha.vass_bootcamp_BE.task_manager_backend.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
	
	public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
		this.authenticationManager = authManager;
		this.jwtUtil = jwtUtil;
	}

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            logger.info("Authentication successful for user: {}", request.getUsername());

            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", request.getUsername(), e);
            return ResponseEntity.status(403).build();
        }
    }
}
