package ar.mikellbobadilla.todo_app.filters;

import ar.mikellbobadilla.todo_app.exceptions.ErrorResponse;
import ar.mikellbobadilla.todo_app.services.impl.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private static final Logger log = Logger.getLogger(JwtFilter.class.getName());

  private final JwtService jwtService;
  private final UserDetailsService detailsService;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull  HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
  /* ---------------------------------------------------------------------------------------------------------------- */

    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String token;
    final String username;

    try{
      if(authHeader == null || !authHeader.startsWith("Bearer ")){
        filterChain.doFilter(request, response);
        return;
      }

      token = authHeader.substring(7);
      username = jwtService.getSubject(token);

      if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
        UserDetails userDetails = detailsService.loadUserByUsername(username);

        if(jwtService.isTokenValid(token, userDetails)){
          var authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
          );

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }

      }

      filterChain.doFilter(request, response);

    }catch (RuntimeException exc){
      log.severe(exc.getMessage());
      ErrorResponse res = new ErrorResponse(HttpStatus.FORBIDDEN, "Session expired", new Date());
      var obj = new ObjectMapper();
      response.setStatus(403);
      response.getWriter().write(obj.writeValueAsString(res));
    }

  }
}
