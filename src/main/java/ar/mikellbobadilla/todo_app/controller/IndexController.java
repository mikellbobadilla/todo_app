package ar.mikellbobadilla.todo_app.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class IndexController {

  private static final Logger logger = Logger.getLogger(IndexController.class.getName());

  @RequestMapping("/")
  public ResponseEntity<Map<String, Object>> indexPage(HttpServletRequest request){

    String auth = request.getHeader("Authorization");

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Principal Page");
    logger.info("Authorization: " + auth);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
