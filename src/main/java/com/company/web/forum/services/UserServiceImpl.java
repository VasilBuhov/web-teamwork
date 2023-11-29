package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.User;
import com.company.web.forum.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final String DELETE_USER_ERROR_MESSAGE =
            "Only admin and user who own profile can delete it .";
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
    @Override
    public List<User> getTopThreeUsers() {
        return userRepository.getTopThreeUsers();
    }

    public long getUsersCount() {
        return userRepository.getUsersCount();
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void createUser(User user) {

        User existingUser = userRepository.getUserByEmail(user.getEmail());
        if (existingUser == null) {
            throw new EntityDuplicateException("User", "email", user.getEmail());
        }

        existingUser = userRepository.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            System.out.println("Something wrong");
            System.out.println("Something wrong_2");
            throw new EntityDuplicateException("User", "username", user.getUsername());
        } if (user.getUsername()== null){
            throw new NullPointerException();
        }

        user.setFirstName(user.getFirstName()); // Set the first name
        user.setLastName(user.getLastName()); // Set the last name
        user.setPassword(user.getPassword());
        userRepository.createUser(user);
    }


    public void updateUser(User authenticatedUser,User user) throws EntityNotFoundException {
        // Check if the user has the permission to modify the user
        checkModifyPermissionsForUpdating(authenticatedUser, user);

        // Perform the update operation
        userRepository.updateUser(user);
    }

    public void deleteUser(User authenticatedUser, int id) throws EntityNotFoundException {
        // Get the user from the repository
        User user = userRepository.getUserById(id);

        // Check if the authenticated user has the same ID as the user to be deleted
        checkModifyPermissionsForDeleting(authenticatedUser, user);

        // Perform the delete operation
        userRepository.deleteUser(id);
    }
    public void blockOrUnblockUser(int userId, boolean block) throws EntityNotFoundException {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User not found", userId);
        }

        if (block) {
            user.setIsAdmin(2); // Block the user
        } else {
            user.setIsAdmin(0); // Unblock the user
        }

        userRepository.updateUser(user);
    }

    @Override
    public void makeRegularUserAdmin(int id) {

    }

    @Override
    public void makeRegularUserAdmin(int id, User authenticatedUser) throws EntityNotFoundException, AuthorizationException {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new EntityNotFoundException("User not found", id);
        }

        // Check if the authenticated user has the permission to make another user an admin
        checkModifyPermissionsForUpdating(authenticatedUser);

        user.setIsAdmin(1); // Set the user as an admin
        userRepository.updateUser(user); // Save the changes to the user
    }


//    @Override
//    public void makeRegularUserAdmin(int id) {
//        User user = userRepository.getUserById(id);
//        if (user == null) {
//            throw new EntityNotFoundException("User not found",id);
//        }
//         user.setIsAdmin(1);
//         userRepository.updateUser(user);
//    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    private void checkModifyPermissionsForUpdating(User authenticatedUser, User user) {
        if (!(authenticatedUser.getIsAdmin() == 1 || authenticatedUser.getId() == user.getId()))
            throw new AuthorizationException(MODIFY_USER_ERROR_MESSAGE);
        }



    private void checkModifyPermissionsForDeleting(User authenticatedUser, User user) {
        if (!(authenticatedUser.getIsAdmin() == 1 || authenticatedUser.getId() == user.getId())) {
            throw new AuthorizationException(DELETE_USER_ERROR_MESSAGE);
        }
    }
    private void checkModifyPermissionsForUpdating(User authenticatedUser) throws AuthorizationException {
        // Check if the authenticated user is an admin or if they are modifying their own profile
        if (authenticatedUser.getIsAdmin() != 1 && authenticatedUser.getIsAdmin() != 2) {
            throw new AuthorizationException("Only admin or blocked user can modify a user.");
        }
    }

}
