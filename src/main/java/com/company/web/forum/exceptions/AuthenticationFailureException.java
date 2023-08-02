package com.company.web.forum.exceptions;

public class AuthenticationFailureException extends RuntimeException{
    public AuthenticationFailureException(String message) {
        super(message);
    }
}
