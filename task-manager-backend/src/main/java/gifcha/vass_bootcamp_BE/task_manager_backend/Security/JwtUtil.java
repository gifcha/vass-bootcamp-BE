package gifcha.vass_bootcamp_BE.task_manager_backend.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts.SIG;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

  private SecretKey key;

  @Value("${app.secret}")
  private String secretString;

  @Value("${app.token.expiration.ms}")
  private long tokenExpirationTime; // set to 2 hours

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username) {
    return Jwts.builder()
      .subject(username)
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
      .signWith(key, SIG.HS256)
      .compact();
  }

  public String refreshToken(String token) {
    Claims claims = Jwts.parser()
      .verifyWith(key)
      .build()
      .parseSignedClaims(token)
      .getPayload();

    Date issuedAt = claims.getIssuedAt();
    long tokenAge = System.currentTimeMillis() - issuedAt.getTime();
  
    if (tokenAge > tokenExpirationTime) {
      // Log out user if token is older than expiration time
      return null;
    }

    return generateToken(claims.getSubject());
  }

  public String extractUsername(String token) {
    Claims claims = Jwts
      .parser()
      .verifyWith(key)
      .build()
      .parseSignedClaims(token)
      .getPayload();

    return claims.getSubject();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return username.equals(userDetails.getUsername());
  }
}
