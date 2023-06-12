package ar.mikellbobadilla.todo_app.controller;

import ar.mikellbobadilla.todo_app.dto.AuthDTO;
import ar.mikellbobadilla.todo_app.dto.JwtDTO;
import ar.mikellbobadilla.todo_app.dto.RegisterDTO;
import ar.mikellbobadilla.todo_app.dto.UserDTO;
import ar.mikellbobadilla.todo_app.entities.User;
import ar.mikellbobadilla.todo_app.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

  private static final Logger logger = Logger.getLogger(AuthController.class.getName());
  private final AuthService authService;

  @PostMapping("/authenticate")
  public ResponseEntity<JwtDTO> authenticate(@RequestBody AuthDTO authDTO){
    String token = authService.authenticate(authDTO);
    logger.info("User Authenticated!");
    return new ResponseEntity<>(new JwtDTO(token), HttpStatus.OK);
  }

  @PostMapping("/register")
  public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO registerDTO){
    User user = authService.register(registerDTO);
    UserDTO response = new UserDTO(user.getId(), user.getUsername(), user.getName());
    logger.info("User Created!");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
