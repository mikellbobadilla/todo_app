package ar.mikellbobadilla.todo_app.respositories;

import ar.mikellbobadilla.todo_app.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  @Query(value = """
          select t.* from todos t \s
          inner join users u on t.user_id = u.id \s
          where u.username = ?1 \s
          """, nativeQuery = true)
  List<Todo> getAllByUser(String username);
}
