package ar.mikellbobadilla.todo_app.dto;

import java.util.Date;

public record TodoDTO(String title, String content, Boolean status, Date createAt) {
}
