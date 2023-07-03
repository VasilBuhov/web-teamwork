package com.company.web.forum.repositories;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users;

    public UserRepositoryImpl() {
        users = new ArrayList<>();
        users.add(new User(1, "John", "Doe","Johnny", "john.doe@example.com", "password123", true));
        users.add(new User(2, "Jane", "Smith","Janny", "jane.smith@example.com", "password456", false));
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public User getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", "email", email));
    }

    @Override
    public List<User> getAdminUsers() {
        return users.stream()
                .filter(User::isAdmin)
                .collect(Collectors.toList());
    }

    @Override
    public void createUser(User user) {
        int nextId = users.size() + 1;
        user.setId(nextId);
        users.add(user);
    }

    @Override
    public void updateUser(User user) {
        User userToUpdate = getUserById(user.getId());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setAdmin(user.isAdmin());
    }

    @Override
    public void deleteUser(int id) {
        User userToDelete = getUserById(id);
        users.remove(userToDelete);
    }
    @Override
    public User getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }
}

