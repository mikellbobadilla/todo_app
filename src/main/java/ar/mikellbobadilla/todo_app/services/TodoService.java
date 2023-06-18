package ar.mikellbobadilla.todo_app.services;

import ar.mikellbobadilla.todo_app.dto.CreateTodoDTO;
import ar.mikellbobadilla.todo_app.dto.TodoDTO;
import ar.mikellbobadilla.todo_app.dto.UserDTO;
import ar.mikellbobadilla.todo_app.entities.Todo;
import ar.mikellbobadilla.todo_app.entities.User;
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

  public TodoDTO create(CreateTodoDTO createTodoDTO, String username) {

    User user = userRepository.findByUsername(username)
            .orElseThrow(()-> new UsernameNotFoundException("User not found!"));

    Todo t = new Todo(createTodoDTO.title(), createTodoDTO.content(), createTodoDTO.status(), user, new Date());

    return new TodoDTO(
        todoRepository.save(t).getId(),
        t.getTitle(),
        t.getContent(),
        t.getStatus(),
        t.getCreateAt()
    );
  }

  public List<TodoDTO> getAll(String username){
    if(userRepository.findByUsername(username).isEmpty()){
      throw new UsernameNotFoundException("This user doesn't exists!");
    }
    return parseTodos(todoRepository.getAllByUser(username));
  }

  private List<TodoDTO> parseTodos(List<Todo> todos){
    List<TodoDTO> todosResponse = new ArrayList<>();
    for(Todo todo: todos){
      todosResponse.add(
        new TodoDTO(
          todo.getId() ,
          todo.getTitle(),
          todo.getContent(),
          todo.getStatus(),
          todo.getCreateAt()
        )
      );
    }
    return todosResponse;
  }

}
