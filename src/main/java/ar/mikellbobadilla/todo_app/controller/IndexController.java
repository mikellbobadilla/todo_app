package ar.mikellbobadilla.todo_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

  @RequestMapping("/")
  public ResponseEntity<Map<String, Object>> indexPage(){

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Principal Page");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
