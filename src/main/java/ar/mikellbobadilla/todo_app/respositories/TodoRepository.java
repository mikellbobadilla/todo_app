package ar.mikellbobadilla.todo_app.respositories;

import ar.mikellbobadilla.todo_app.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  Optional<Todo> findByTitle(String title);
}
