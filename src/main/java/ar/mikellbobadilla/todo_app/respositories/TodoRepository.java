package ar.mikellbobadilla.todo_app.respositories;

import ar.mikellbobadilla.todo_app.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  @Query(value = """
          SELECT t.* FROM todos t \s
          INNER JOIN users u ON t.user_id = u.id \s
          WHERE user_id = ?1 \s
          """, nativeQuery = true)
  List<Todo> getAllByUser(Long userId);
}
