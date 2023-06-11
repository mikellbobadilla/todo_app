package ar.mikellbobadilla.todo_app.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Date;

public record ErrorResponse(
  HttpStatus status,
  String msg,
  Date timestamp
) {
}
