package ar.mikellbobadilla.todo_app.dto;

public record TodoRequestDTO(
        String title,
        String content,
        Boolean status
) {
}
