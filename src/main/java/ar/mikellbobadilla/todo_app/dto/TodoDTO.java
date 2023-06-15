package ar.mikellbobadilla.todo_app.dto;

import java.util.Date;

public record TodoDTO(Long id, String title, String content, Boolean status, Date createAt) {
}
