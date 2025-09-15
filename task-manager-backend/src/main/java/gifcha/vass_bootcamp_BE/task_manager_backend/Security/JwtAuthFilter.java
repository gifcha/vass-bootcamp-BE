package gifcha.vass_bootcamp_BE.task_manager_backend.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.springframework.web.util.WebUtils;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private JwtUtil jwtUtil;
  private UserDetailsService userDetailsService;

  @Value("${app.cookie.name}")
  private String cookieName;

  public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
      )

    throws ServletException, IOException {
      Cookie authCookie = WebUtils.getCookie(request, cookieName);
      logger.info("cookie: " + authCookie);

      if (authCookie != null) {
        String token = authCookie.getValue();
        String username = jwtUtil.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);

          // validate token
          if (jwtUtil.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);

            // refresh token and reset cookie
            token = jwtUtil.refreshToken(token);
            ResponseCookie cookie = jwtUtil.createCookie(token);
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
          }
        }
      }

      filterChain.doFilter(request, response);
  }
}
