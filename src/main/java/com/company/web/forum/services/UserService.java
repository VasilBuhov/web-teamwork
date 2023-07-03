package com.company.web.forum.services;

import com.company.web.forum.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(int id);

    User getUserByEmail(String email);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);

   User getUserByUsername(String username);
}
