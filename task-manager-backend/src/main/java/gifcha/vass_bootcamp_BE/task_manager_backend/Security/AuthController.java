package gifcha.vass_bootcamp_BE.task_manager_backend.Security;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import gifcha.vass_bootcamp_BE.task_manager_backend.User.User;
import gifcha.vass_bootcamp_BE.task_manager_backend.User.UserService;
import jakarta.servlet.http.HttpServletResponse;


@CrossOrigin(origins = "https://localhost:4200",  allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Value("${app.cookie.expiration.sec}")
  private long cookieExpirationTime; // set to 2 hours

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  private AuthenticationManager authenticationManager;
  private JwtUtil jwtUtil;
  private CustomUserDetailsService userDetailsService;
  private UserService userService;

  public AuthController(
    AuthenticationManager authManager,
    JwtUtil jwtUtil,
    CustomUserDetailsService userDetailsService,
    UserService userService)
  {
    this.authenticationManager = authManager;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequest request, HttpServletResponse response) {
    try {
      Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
      );

      String token = jwtUtil.generateToken(request.getUsername());
      logger.info("Authentication successful for user: {}", request.getUsername());

      // Create auth cookie
      ResponseCookie cookie = jwtUtil.createCookie(token);
      response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

      return ResponseEntity.ok(auth);
    }
    catch (Exception e) {
      logger.error("Authentication failed for user: {}", request.getUsername(), e);
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/is-valid")
  public Boolean isValid(@CookieValue(value = "Auth-Token", required = false) String token) { // returns true if auth token is valid
    if (token == null) {
      logger.info("Missing token!");
      return false;
    }

    try {
      String username = jwtUtil.extractUsername(token);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      boolean isValid = jwtUtil.validateToken(token, userDetails);

      if (!isValid) {
        logger.warn("Token is invalid: " + token);
      }

      return isValid;
    } 
    catch (Exception e) {
      logger.error("Error validating token", e);
      return false;
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody RegisterRequest request, HttpServletResponse response) {
    try {
      // create user
      User user = new User(request.getUsername(), request.getPassword(), request.getFirstName(), request.getLastName());
      user = this.userService.addUser(user);

      // return login
      LoginRequest loginReq = new LoginRequest(request.getUsername(), request.getPassword());
      return login(loginReq, response);
    }
    catch (Exception e) {
      logger.error("Registration failed for user: {}", request.getUsername(), e);
      return ResponseEntity.badRequest().build();
    }
  }
}
