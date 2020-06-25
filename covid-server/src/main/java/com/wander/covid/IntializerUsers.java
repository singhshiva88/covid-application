package com.wander.covid;

import com.wander.covid.model.User;
import com.wander.covid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class IntializerUsers
{
  @Autowired
  private UserService userService;

  @PostConstruct
  public void initialize() {
    userService.saveUser(new User("firstname1", "lastname1", "user1", "pass1"));
    userService.saveUser(new User("firstname2", "lastname2", "user2", "pass2"));
  }
}
