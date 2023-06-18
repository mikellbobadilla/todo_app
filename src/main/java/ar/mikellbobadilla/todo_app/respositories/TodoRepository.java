package ar.mikellbobadilla.todo_app.respositories;

import ar.mikellbobadilla.todo_app.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  @Query(value = """
          select t.* from todos t \s
          inner join users u on t.user_id = u.id \s
          where t.id = ?1 and u.username = ?2  \s
          """, nativeQuery = true)
  Optional<Todo> getByUser(Long id, String username);
}
