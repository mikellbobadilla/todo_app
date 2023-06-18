package ar.mikellbobadilla.todo_app.services;

import ar.mikellbobadilla.todo_app.dto.AuthDTO;
import ar.mikellbobadilla.todo_app.dto.RegisterDTO;
import ar.mikellbobadilla.todo_app.entities.User;
import ar.mikellbobadilla.todo_app.exceptions.UserException;
import ar.mikellbobadilla.todo_app.respositories.UserRepository;
import ar.mikellbobadilla.todo_app.services.impl.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final AuthenticationManager authManager;
  private final PasswordEncoder encoder;

  public String authenticate(AuthDTO authDTO) throws AuthenticationException {
    Authentication auth = new UsernamePasswordAuthenticationToken(authDTO.username(), authDTO.password());

    authManager.authenticate(auth);

    User user = userRepository.findByUsername(authDTO.username())
      .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

    return jwtService.createToken(user);
  }

  public User register(RegisterDTO registerDTO) throws UserException {
    if(userRepository.findByUsername(registerDTO.username()).isPresent()){
      throw new UserException("Username already exists!");
    }
    User user = new User(
      registerDTO.username(),
      encoder.encode(registerDTO.password()),
      registerDTO.name()
    );
    return userRepository.save(user);
  }
}
