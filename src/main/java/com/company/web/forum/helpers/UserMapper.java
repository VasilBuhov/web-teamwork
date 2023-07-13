package com.company.web.forum.helpers;

import com.company.web.forum.models.User;
import com.company.web.forum.models.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName()); // Set the first name
        userDto.setLastName(user.getLastName()); // Set the last name
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public List<UserDto> toDtoList(List<User> users) {
        List<UserDto> list = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = toDtoNoPassword(user);
            list.add(userDto);
        }
        return list;
    }


    public User fromDto(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName()); // Set the first name
        user.setLastName(userDto.getLastName()); // Set the last name
        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto toDtoNoPassword(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName()); // Set the first name
        userDto.setLastName(user.getLastName()); // Set the last name
//        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
