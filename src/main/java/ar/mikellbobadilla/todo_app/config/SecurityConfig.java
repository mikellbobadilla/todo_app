package ar.mikellbobadilla.todo_app.config;

import ar.mikellbobadilla.todo_app.filters.JwtFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final AuthenticationProvider provider;
  private final JwtFilter filter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

    /* SpringSecurity 6.1 */
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(
        httpRequest -> {
          httpRequest.requestMatchers("/api/auth/**").permitAll();
          httpRequest.anyRequest().authenticated();
        })
      .sessionManagement(
        sessionManagement -> {
          sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      })
      .authenticationProvider(provider)
      .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
