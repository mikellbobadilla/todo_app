package ar.mikellbobadilla.todo_app.dto;

import java.util.List;

public record TodoResponseDTO(UserDTO owner, List<TodoDTO> todos) {
}
