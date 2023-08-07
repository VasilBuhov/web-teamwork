package com.company.web.forum.controllers.MvcController;

import com.company.web.forum.exceptions.AuthenticationFailureException;
import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDuplicateException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.AuthenticationHelper;
import com.company.web.forum.helpers.UserMapper;
import com.company.web.forum.models.Post;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;
import com.company.web.forum.models.UserDto;
import com.company.web.forum.services.PostService;
import com.company.web.forum.services.TopicService;
import com.company.web.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserMvcController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationController authenticationController;
    private final AuthenticationHelper authenticationHelper;
    private final TopicService topicService;
    private final PostService postService;

    @Autowired
    public UserMvcController(UserService userService, UserMapper userMapper, AuthenticationController authenticationController,
                             AuthenticationHelper authenticationHelper, TopicService topicService, PostService postService) {
        this.userService = userService;
        this.userMapper=userMapper;
        this.authenticationController=authenticationController;

        this.authenticationHelper = authenticationHelper;
        this.topicService = topicService;
        this.postService = postService;
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
            return "show_user";
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
            if(errors.hasErrors()){
                model.addAttribute("errorMessage", "Please fill in all required fields.");
                return "user-new";
            }
        try {
            User newUser = userMapper.fromDto(userDto);
            userService.createUser(newUser);
            return "redirect:/" ;
        } catch (EntityDuplicateException e) {
            model.addAttribute("alreadyExists", e.getMessage());
        }

        return "AlreadyExistsView";
    }
    @GetMapping("/delete")
    public String showDeleteUserPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username != null) {
            // Assuming that 'userService.getUserByUsername' retrieves the user based on the username
            User user = userService.getUserByUsername(username);
            if (user != null) {
                model.addAttribute("user", user);
                return "delete-user";
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username != null) {
            try {
                User authenticatedUser = userService.getUserByUsername(username);
                userService.deleteUser(authenticatedUser, id);

                // Redirect to the home page after successful deletion
                return "redirect:/";
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            } catch (AuthenticationFailureException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username != null) {
            try {
                User user = userService.getUserByUsername(username);
                model.addAttribute("user", userMapper.toDto(user));
                return "edit_user";
            } catch (EntityNotFoundException e) {
                model.addAttribute("error", "User not found");
                return "NotFoundView";
            }
        } else {
            return "redirect:/auth/login"; // Redirect to the login page if not logged in
        }
    }

    @PostMapping("/profile")
    public String updateUserProfile(@Valid @ModelAttribute("user") UserDto userDto, BindingResult errors, Model model, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username != null) {
            if (errors.hasErrors()) {
                return "edit_user"; // Stay on the profile page with error messages
            }
            try {
                User authenticatedUser = userService.getUserByUsername(username);
                User user = userMapper.fromDto(userDto);
                user.setId(authenticatedUser.getId()); // Set the ID of the authenticated user

                userService.updateUser(authenticatedUser, user);

                return "UpdateSuccessView"; // Redirect to the profile page after successful update
            } catch (EntityNotFoundException e) {
                model.addAttribute("error", "User not found");
                return "NotFoundView";
            } catch (AuthorizationException e) {
                model.addAttribute("error", "Unauthorized access");
                return "UnauthorizedView";
            }
        } else {
            return "redirect:/auth/login"; // Redirect to the login page if not logged in
        }
    }
    @GetMapping("/topics-posts/{id}")
    public String getUserProfile(@PathVariable int id, Model model) {
        try {
            // Get the user by ID
            User user = userService.getUserById(id);
            model.addAttribute("user", user);

            // Get the topics created by the user
            List<Topic> userTopics = topicService.getTopicsByUser(user);

            model.addAttribute("userTopics", userTopics);

            // Get the posts created by the user
            List<Post> userPosts = postService.getPostsByUser(user);
            model.addAttribute("userPosts", userPosts);

            return "user-topics-posts"; // The name of the Thymeleaf template file
        } catch (EntityNotFoundException e) {
            // Handle the exception as needed, e.g., show a not found page
            return "NotFoundView";
        }
    }
    @GetMapping("/admin-panel")
    public String showAdminPanel(Model model, HttpSession session) {
        if (authenticationHelper.isAdmin(session)) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "admin_panel"; // View for the admin panel
        } else {
            return "UnauthorizedView"; // Redirect to home view if not an admin
        }
    }
    @PostMapping("/{id}/block")
    public String blockUser(@PathVariable int id, HttpSession session) {
        if (authenticationHelper.isAdmin(session)) {
            try {
                userService.blockOrUnblockUser(id, true);
                return "redirect:/users/admin-panel"; // Redirect to the admin panel after blocking user
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
        } else {
            return "redirect:/"; // Redirect to home view if not an admin
        }
    }

    @PostMapping("{id}/unblock")
    public String unblockUser(@PathVariable int id, HttpSession session) {
        if (authenticationHelper.isAdmin(session)) {
            try {
                userService.blockOrUnblockUser(id, false);
                return "redirect:/users/admin-panel"; // Redirect to the admin panel after unblocking user
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
        } else {
            return "redirect:/"; // Redirect to home view if not an admin
        }
    }
    @PostMapping("/{id}/admin")
    public String makeUserAdmin(@PathVariable int id, HttpSession session) {
        if (authenticationHelper.isAdmin(session)) {
            try {
                User authenticatedUser = getAuthenticatedUser(session);
                userService.makeRegularUserAdmin(id,authenticatedUser);
                return "redirect:/users/admin-panel"; // Redirect to the admin panel after setting user as admin
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            } catch (AuthorizationException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
            }
        } else {
            return "redirect:/"; // Redirect to home view if not an admin
        }
    }
    @PostMapping("/{id}/delete")
    public String adminDeleteUser(@PathVariable int id, HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username != null) {
            try {
                User authenticatedUser = userService.getUserByUsername(username);
                userService.deleteUser(authenticatedUser, id);

                // Redirect to the admin panel after successful deletion
                return "redirect:/users/admin-panel";
            } catch (EntityNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            } catch (AuthenticationFailureException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
    }
    private User getAuthenticatedUser(HttpSession session) {
        String username = (String) session.getAttribute("currentUser");
        if (username != null) {
            return userService.getUserByUsername(username);
        }
        return null;
    }


}