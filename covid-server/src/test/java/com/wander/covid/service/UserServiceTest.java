package com.wander.covid.service;

import com.wander.covid.model.User;
import com.wander.covid.repository.UserRepository;
import com.wander.covid.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService = new UserServiceImpl();

  @Test
  void shouldSaveUserSuccessfully()
  {
    final User user = new User("shiva", "singh", "username", "password");
    BDDMockito.given(userRepository.findByUsername(user.getUsername())).willReturn(user);
    userService.saveUser(user);
    Mockito.verify(userRepository).findByUsername(user.getUsername());
  }
}