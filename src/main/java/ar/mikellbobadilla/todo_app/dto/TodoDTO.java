package ar.mikellbobadilla.todo_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record TodoDTO(
  Long id,
  String title,
  String content,
  Boolean status,
  Date createAt
) {
}
