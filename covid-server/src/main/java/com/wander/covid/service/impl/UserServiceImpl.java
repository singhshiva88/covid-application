package com.wander.covid.service.impl;

import com.wander.covid.model.User;
import com.wander.covid.repository.RoleRepository;
import com.wander.covid.repository.UserRepository;
import com.wander.covid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService
{
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public User findByUsername(String username)
  {
    return userRepository.findByUsername(username);
  }

  @Override
  public void saveUser(User user)
  {
    if (findByUsername(user.getUsername()) == null) {
      userRepository.save(user);
    } else {
      System.out.println("User Already extsts................................");
    }
  }

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
  {
    User user = userRepository.findByUsername(userName);
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }
}
