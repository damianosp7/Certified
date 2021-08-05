package com.example.certified.service;

import com.example.certified.model.User;
import com.example.certified.web.dto.UserRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    String save(UserRegistrationDto registrationDto);
    String saveUser(User user);
    String updateUser(User user);
    User getUserById(long id);
    List<User> getAllUsers();
    void deleteUserById(long id);
    Page<User> findPaginate(int pageNo ,int pageSize, String sortField, String sortDirection);


    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    public void updateResetPasswordToken(String token, String email);
    public User getByResetPasswordToken(String token);
    public void updatePassword(User user, String newPassword);
}
