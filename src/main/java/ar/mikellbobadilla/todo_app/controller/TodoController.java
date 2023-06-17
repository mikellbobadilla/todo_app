package ar.mikellbobadilla.todo_app.controller;

import ar.mikellbobadilla.todo_app.dto.CreateTodoDTO;
import ar.mikellbobadilla.todo_app.dto.TodoDTO;
import ar.mikellbobadilla.todo_app.entities.Todo;
import ar.mikellbobadilla.todo_app.respositories.UserRepository;
import ar.mikellbobadilla.todo_app.services.TodoService;
import ar.mikellbobadilla.todo_app.services.impl.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
public class TodoController {

  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final TodoService todoService;

  @GetMapping("/all")
  public ResponseEntity<List<TodoDTO>> getAll(HttpServletRequest request){
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    String token = authHeader.substring(7);

    List<TodoDTO> todos = todoService.getAll(jwtService.getSubject(token));

    return new ResponseEntity<>(todos, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<TodoDTO> create(@RequestBody CreateTodoDTO todoDTO, HttpServletRequest request){
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    String token = authHeader.substring(7);
    TodoDTO todo = todoService.create(todoDTO, jwtService.getSubject(token));
    return new ResponseEntity<>(todo, HttpStatus.CREATED);
  }

}
