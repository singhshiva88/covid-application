package com.wander.covid.repository;

import com.wander.covid.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
  User findByUsername(String userName);
}
