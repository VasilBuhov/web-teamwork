package com.company.web.forum.repositories;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();

    List<User> getTopThreeUsers();

    long getUsersCount();
    User getUserById(int id) throws EntityNotFoundException;
    User getUserByEmail(String email) throws EntityNotFoundException;
    User getUserByUsername(String username) throws EntityNotFoundException;
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(int id) throws EntityNotFoundException;
}
