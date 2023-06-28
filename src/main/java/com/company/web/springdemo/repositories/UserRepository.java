package com.company.web.springdemo.repositories;

import com.company.web.springdemo.exceptions.EntityNotFoundException;
import com.company.web.springdemo.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByEmail(String email);

    List<User> getAdminUsers();

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

  User getUserByUsername(String username);
}
