package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.UserMapper;
import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.User;
import com.company.web.forum.models.UserDto;
import com.company.web.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/users")
public class UserMvcController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserMvcController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper=userMapper;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/{id}")
    public String showSingleUser(@PathVariable int id, Model model) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "UsersView";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "NotFoundView";
        }
    }
    @GetMapping("/new")
    public String showNewUserPage(Model model){
        model.addAttribute("user", new UserDto());
        return "user-new";
    }

    @PostMapping("/new")
    public String createUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult errors,Model model) {
//            if(errors.hasErrors()){
//                return "user-new";
//            }
        try {
            User newUser = userMapper.fromDto(userDto);
            userService.createUser(newUser);
            return "redirect:/users";
        } catch (EntityDuplicateException e) {
            model.addAttribute("alreadyExists", e.getMessage());
        }
        return "AlreadyExistsView";
    }
}
