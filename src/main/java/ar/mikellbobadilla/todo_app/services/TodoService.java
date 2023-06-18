package ar.mikellbobadilla.todo_app.services;

import ar.mikellbobadilla.todo_app.dto.TodoRequestDTO;
import ar.mikellbobadilla.todo_app.dto.TodoDTO;
import ar.mikellbobadilla.todo_app.dto.TodoResponseDTO;
import ar.mikellbobadilla.todo_app.dto.UserDTO;
import ar.mikellbobadilla.todo_app.entities.Todo;
import ar.mikellbobadilla.todo_app.entities.User;
import ar.mikellbobadilla.todo_app.exceptions.TodoException;
import ar.mikellbobadilla.todo_app.respositories.TodoRepository;
import ar.mikellbobadilla.todo_app.respositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;
  private final UserRepository userRepository;

  public TodoDTO create(TodoRequestDTO todoRequestDTO, String username) {

    User user = userRepository.findByUsername(username)
      .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

    Todo t = new Todo(todoRequestDTO.title(), todoRequestDTO.content(), todoRequestDTO.status(), user, new Date());

    return new TodoDTO(
        todoRepository.save(t).getId(),
        t.getTitle(),
        t.getContent(),
        t.getStatus(),
        t.getCreateAt()
    );
  }

  public TodoResponseDTO getAll(String username) {
    User user = userRepository.findByUsername(username)
      .orElseThrow(()-> new UsernameNotFoundException("User not found!"));
    return parseTodos(user);
  }

  public TodoDTO get(Long id, String username) throws TodoException{
    Todo t = todoRepository.getByUser(id, username)
      .orElseThrow(()-> new TodoException("Todo not found!"));
    return parseTodoDTO(t);
  }

  /* Parse Objects */
  private TodoResponseDTO parseTodos (User user) {
    UserDTO u = new UserDTO(user.getId(), user.getUsername());
    List<TodoDTO> todos  = new ArrayList<>();
    for(Todo todo : user.getTodos()){
      todos.add(new TodoDTO(todo.getId(), todo.getTitle(), todo.getContent(), todo.getStatus(), todo.getCreateAt()));
    }
    return new TodoResponseDTO(u, todos);
  }

  private TodoDTO parseTodoDTO(Todo t){
    return new TodoDTO(t.getId(), t.getTitle(), t.getContent(), t.getStatus(), t.getCreateAt());
  }
}
