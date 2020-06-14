package com.wander.covid.service;

import com.wander.covid.model.User;

public interface UserService
{
  User findByUsername(String username);

  void saveUser(User user);
}
