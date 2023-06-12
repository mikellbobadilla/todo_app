package ar.mikellbobadilla.todo_app.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "todos")
public class Todo {

  public Todo(String title, String content, Boolean status, User user, Date createAt) {
    this.title = title;
    this.content = content;
    this.status = status;
    this.user = user;
    this.createAt = createAt;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;
  private Boolean status;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  @Column(name = "created")
  private Date createAt;
}
