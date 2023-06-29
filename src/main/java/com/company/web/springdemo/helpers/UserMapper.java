package com.company.web.springdemo.helpers;

import com.company.web.springdemo.models.User;
import com.company.web.springdemo.models.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

        public User fromDto(UserDto userDto) {
            User user = new User();
            user.setId(userDto.getId());
            user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());

        return user;
    }

    public List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
