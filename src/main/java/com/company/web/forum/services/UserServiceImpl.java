package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.models.User;
import com.company.web.forum.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final String MODIFY_USER_ERROR_MESSAGE =
            "Only admin  can modify a user.";
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void createUser(User user) {

        User existingUser = userRepository.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }

        existingUser = userRepository.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            throw new EntityDuplicateException("User", "username", user.getUsername());
        }

        user.setFirstName(user.getFirstName()); // Set the first name
        user.setLastName(user.getLastName()); // Set the last name
        user.setPassword(user.getPassword());
        userRepository.createUser(user);
    }


    public void updateUser(User user) {
        // Check if the user has the permission to modify the user
        checkModifyPermissions(user.getId(), user);

        // Perform the update operation
        userRepository.updateUser(user);
    }

    public void deleteUser(int id) {
        // Get the user from the repository
        User user = userRepository.getUserById(id);

        // Check if the user has the permission to delete the user
        checkModifyPermissions(id, user);

        // Perform the delete operation
        userRepository.deleteUser(id);
    }


    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    private void checkModifyPermissions(int userId, User user) {
        User existingUser = userRepository.getUserById(userId);
        if (!(user.getIsAdmin()==1 || existingUser.equals(user))) {
            throw new AuthorizationException(MODIFY_USER_ERROR_MESSAGE);
        }
    }

}
