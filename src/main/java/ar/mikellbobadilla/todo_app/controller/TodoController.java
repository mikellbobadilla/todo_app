package ar.mikellbobadilla.todo_app.controller;

import ar.mikellbobadilla.todo_app.dto.TodoRequestDTO;
import ar.mikellbobadilla.todo_app.dto.TodoDTO;
import ar.mikellbobadilla.todo_app.dto.TodoResponseDTO;
import ar.mikellbobadilla.todo_app.exceptions.TodoException;
import ar.mikellbobadilla.todo_app.respositories.UserRepository;
import ar.mikellbobadilla.todo_app.services.TodoService;
import ar.mikellbobadilla.todo_app.services.impl.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

  private final JwtService jwtService;
  private final TodoService todoService;

  @GetMapping("/all")
  public ResponseEntity<TodoResponseDTO> getAll(HttpServletRequest request){
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    String token = authHeader.substring(7);

    TodoResponseDTO todos = todoService.getAll(jwtService.getSubject(token));

    return new ResponseEntity<>(todos, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<TodoDTO> create(@RequestBody TodoRequestDTO todoDTO, HttpServletRequest request){
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    String token = authHeader.substring(7);
    TodoDTO todo = todoService.create(todoDTO, jwtService.getSubject(token));
    return new ResponseEntity<>(todo, HttpStatus.CREATED);
  }

  @GetMapping("/get")
  public ResponseEntity<TodoDTO> get(@RequestParam("id") Long id, HttpServletRequest request) throws TodoException {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    String token = authHeader.substring(7);
    String username = jwtService.getSubject(token);
    TodoDTO todo = todoService.get(id, username);

    return new ResponseEntity<>(todo, HttpStatus.OK);
  }

}
