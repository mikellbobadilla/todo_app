package ar.mikellbobadilla.todo_app.dto;

import java.util.Date;

public record CreateTodoDTO(
        String title,
        String content,
        Boolean status
) {
}
