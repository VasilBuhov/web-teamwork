package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    List<User> getTopThreeUsers();

    long getUsersCount();

    User getUserById(int id);

    User getUserByEmail(String email);

    void createUser(User user);


    void updateUser(User authenticatedUser,User user);

     void deleteUser(User authenticatedUser, int id);


    void makeRegularUserAdmin(int id, User authenticatedUser) throws EntityNotFoundException, AuthorizationException;

    User getUserByUsername(String username);
  void blockOrUnblockUser(int userId, boolean block);

    void makeRegularUserAdmin(int id);
}
