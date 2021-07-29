package com.example.certified.repository;

import com.example.certified.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    public User findByResetPasswordToken(String token);

}
