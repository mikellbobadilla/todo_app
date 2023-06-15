package ar.mikellbobadilla.todo_app.services;

import ar.mikellbobadilla.todo_app.dto.TodoDTO;
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

  public Todo create(TodoDTO todoDTO, String username)throws UsernameNotFoundException{
    User user = userRepository.findByUsername(username)
      .orElseThrow(()-> new UsernameNotFoundException("User Not Found!"));
    Todo newTodo = new Todo(todoDTO.title(), todoDTO.content(), false, user, new Date());

    return todoRepository.save(newTodo);
  }

  public List<TodoDTO> getAll(String username){
    User user = userRepository.findByUsername(username)
      .orElseThrow(()-> new UsernameNotFoundException("User Not Found!"));
    System.out.println(user.getId());
    return parseTodos(todoRepository.getAllByUser(user.getId()));
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
