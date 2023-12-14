package com.example.sprint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sprint.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>  {
    User findByUserIdAndUserPassword(String userPassword, String UserId);
    List<User> findByUserPasswordAndUserId(String userId, String userPassword);
    User findByUserId(String userId);
}
