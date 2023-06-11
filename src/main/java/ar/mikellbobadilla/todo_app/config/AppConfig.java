package ar.mikellbobadilla.todo_app.config;

import ar.mikellbobadilla.todo_app.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

  private final UserRepository userRepository;


  @Bean
  public UserDetailsService detailsService(){
    return username -> userRepository.findByUsername(username)
      .orElseThrow(()-> new UsernameNotFoundException("User: " + username + " not found!"));
  }

  @Bean
  public PasswordEncoder passEncoder(){
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  public AuthenticationProvider provider(){
    var provider = new DaoAuthenticationProvider(passEncoder());
    provider.setUserDetailsService(detailsService());
    return provider;
  }

  @Bean
  public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception{
    return configuration.getAuthenticationManager();
  }

}
