package com.wander.covid.controller;

import com.wander.covid.model.User;
import com.wander.covid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
  @Autowired
  private UserService userService;

  @PostMapping(value = "/signup")
  public ResponseEntity<User> create(@RequestBody User user)
  {
    if (userService.findByUsername(user.getUsername()) != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
    }
    userService.saveUser(user);
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }
/*

  @GetMapping(value = "/load")
  public ResponseEntity<?> loadDummyUsers()
  {
    userService.saveUser(new User("firstname1", "lastname1", "username1@gmail.com", "password1"));
    userService.saveUser(new User("firstname2", "lastname2", "username2@gmail.com", "password2"));
    userService.saveUser(new User("firstname3", "lastname3", "username3@gmail.com", "password3"));
    userService.saveUser(new User("firstname4", "lastname4", "username4@gmail.com", "password4"));
    userService.saveUser(new User("firstname5", "lastname5", "username5@gmail.com", "password5"));
    userService.saveUser(new User("firstname6", "lastname6", "username6@gmail.com", "password6"));
    return ResponseEntity.ok().build();
  }
*/
}
