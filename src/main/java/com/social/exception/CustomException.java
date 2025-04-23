package com.social.exception;

public class CustomException {
    public static class AuthenticationPasswordException extends Exception {
        public AuthenticationPasswordException(String message) {
            super(message);
        }
    }

}
