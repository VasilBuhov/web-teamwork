package com.company.web.forum.helpers;

import com.company.web.forum.exceptions.AuthenticationFailureException;
import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.BlockedUserException;
import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.User;
import com.company.web.forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication.";
    public static final String AUTHENTIFICATION_FAILURE_MESSAGE = "Wrong username or password.";

    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public User tryGetUser(HttpHeaders headers) throws BlockedUserException {


        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        try {
            String userInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            String username = getUsername(userInfo);
            String password = getPassword(userInfo);
            User user = userService.getUserByUsername(username);
            if (user.getIsAdmin() == 2) {
                throw new BlockedUserException("Blocked user can not perform any action");
            }
            if (!user.getPassword().equals(password)) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }

            return user;
        } catch (BlockedUserException e){
            throw new BlockedUserException("Blocked user can not perform any action");
        }
         catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }

    private String getUsername(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(0, firstSpace);
    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(firstSpace + 1);
    }

    public  User verifyAuthentication(String username, String password){
        try {
            User user = userService.getUserByUsername(username);

            if(!user.getPassword().equals(password))
            {
                throw new AuthenticationFailureException(AUTHENTIFICATION_FAILURE_MESSAGE);
            }
            return user;
        } catch (EntityNotFoundException e){
            throw new AuthenticationFailureException(AUTHENTIFICATION_FAILURE_MESSAGE);
        }
    }
}
