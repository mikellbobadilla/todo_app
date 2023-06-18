package ar.mikellbobadilla.todo_app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@RestControllerAdvice
public class ExceptionMapper {

  private static final Logger logger = Logger.getLogger(ExceptionMapper.class.getName());

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> errorResponse(Exception exc){
    var status = HttpStatus.INTERNAL_SERVER_ERROR;
    var res = getResponse(status, "Internal Error");
    logger.log(new LogRecord(Level.SEVERE, exc.getMessage()));
    return new ResponseEntity<>(res, status);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> authException(AuthenticationException exc){
    String message = "Bad Credentials";
    HttpStatus status = HttpStatus.FORBIDDEN;
    logger.log(new LogRecord(Level.SEVERE, exc.getMessage()));
    if(exc instanceof UsernameNotFoundException){
      status = HttpStatus.NOT_FOUND;
      message = exc.getMessage();
    }
    ErrorResponse res = getResponse(status, message);
    return new ResponseEntity<>(res, status);
  }

  @ExceptionHandler(UserException.class)
  public ResponseEntity<ErrorResponse> userException(UserException exc){
    logger.log(new LogRecord(Level.SEVERE, exc.getMessage()));
    ErrorResponse res = getResponse(HttpStatus.BAD_REQUEST, exc.getMessage());
    return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TodoException.class)
  public ResponseEntity<ErrorResponse> todoException(TodoException exc){
    logger.log(new LogRecord(Level.SEVERE, exc.getMessage()));
    ErrorResponse res = getResponse(HttpStatus.NOT_FOUND, exc.getMessage());
    return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
  }

  private ErrorResponse getResponse(HttpStatus status, String message){
    return new ErrorResponse(status, message, new Date());
  }
}
