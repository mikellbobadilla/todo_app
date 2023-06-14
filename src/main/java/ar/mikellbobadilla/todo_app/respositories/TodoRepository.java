package ar.mikellbobadilla.todo_app.respositories;

import ar.mikellbobadilla.todo_app.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  @Query(value = "select t.title, t.content, t.status, t.created from todos t where t.user_id = :userId")
  List<Todo> getAllByUser(Long userId);
}
