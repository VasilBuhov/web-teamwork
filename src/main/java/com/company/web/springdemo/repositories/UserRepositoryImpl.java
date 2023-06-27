package com.company.web.springdemo.repositories;

import com.company.web.springdemo.exceptions.EntityNotFoundException;
import com.company.web.springdemo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users;

    @Autowired
    public UserRepositoryImpl() {
        users = new ArrayList<>();
        users.add(new User(1, "pesho", "pass1", true));
        users.add(new User(2, "tosho", "pass2", false));
        users.add(new User(3, "gosho", "pass3", false));
    }

    @Override
    public List<User> get() {
        return users;
    }

    @Override
    public User get(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    @Override
    public User get(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));
    }

}
