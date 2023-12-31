package com.company.web.forum.controllers.RestController;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.BlockedUserException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.helpers.AuthenticationHelper;
import com.company.web.forum.helpers.UserMapper;
import com.company.web.forum.models.User;
import com.company.web.forum.models.UserDto;
import com.company.web.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserRestController(UserService userService, UserMapper userMapper,
                              AuthenticationHelper authenticationHelper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<UserDto> getAllUsers(@RequestHeader HttpHeaders httpHeaders) throws BlockedUserException {
        try {
            authenticationHelper.tryGetUser(httpHeaders);
            List<User> users = userService.getAllUsers();
            return userMapper.toDtoList(users);
        } catch (BlockedUserException e ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }



    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable int id) {
        try {
            User user = userService.getUserById(id);
            return userMapper.toDtoNoPassword(user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

//    @PostMapping
//    public UserDto createUser(@RequestHeader HttpHeaders headers, @Valid @RequestBody UserDto userDto) {
//        try {
//            authenticationHelper.tryGetUser(headers);
//            User user = userMapper.fromDto(userDto);
//            userService.createUser(user);
//            return userMapper.toDto(user);
//        } catch (AuthorizationException e) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
//        }
//    }
@PostMapping
public UserDto createUser(@RequestHeader HttpHeaders headers, @Valid @RequestBody UserDto userDto) {
    try {
        authenticationHelper.tryGetUser(headers);
        User user = userMapper.fromDto(userDto);
        userService.createUser(user);
        return userMapper.toDto(user);
    } catch (AuthorizationException e) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
    }
}

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestHeader HttpHeaders headers, @PathVariable int id,
                                             @Valid @RequestBody UserDto userDto) {
        try {
            User authenticatedUser= authenticationHelper.tryGetUser(headers);
            User user = userMapper.fromDto(userDto);
            user.setId(id);
            userService.updateUser(authenticatedUser, user);
            return ResponseEntity.ok("User with ID " + id + " has been successfully updated.");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    @PutMapping("/{id}/block")
    public ResponseEntity<String> blockUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User authenticatedUser = authenticationHelper.tryGetUser(headers);
            if (authenticatedUser.getIsAdmin() != 1) {
                throw new AuthorizationException("Only admin can block/unblock users.");
            }

            userService.blockOrUnblockUser(id, true); // Block the user

            return ResponseEntity.ok("User with ID " + id + " has been blocked.");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}/createAdmin")
    public ResponseEntity<String> makeRegularUserAdmin(@RequestHeader HttpHeaders headers,@PathVariable int id){
        try {
            User authenticatedUser = authenticationHelper.tryGetUser(headers);
            if (authenticatedUser.getId()!=4) {
                throw new AuthorizationException("Only admin can make other users admins");
            }
            userService.makeRegularUserAdmin(id);
            return ResponseEntity.ok("User with ID " + id + " is now admin");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<String> unblockUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User authenticatedUser = authenticationHelper.tryGetUser(headers);
            if (authenticatedUser.getIsAdmin() != 1) {
                throw new AuthorizationException("Only admin can block/unblock users.");
            }

            userService.blockOrUnblockUser(id, false); // Unblock the user

            return ResponseEntity.ok("User with ID " + id + " has been unblocked.");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
           User user= authenticationHelper.tryGetUser(headers);
            userService.deleteUser(user,id);
            return ResponseEntity.ok("User with ID " + id + " has been successfully deleted.");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
