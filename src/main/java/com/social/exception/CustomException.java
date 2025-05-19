package com.social.exception;

public class CustomException {
    public static class AuthenticationPasswordException extends Exception {
        public AuthenticationPasswordException(String message) {
            super(message);
        }
    }
    
    
    public  static class ImageInsertionFailedException extends Exception {
        public ImageInsertionFailedException(String message) {
            super(message);
        }
    }
    public static class PostImageInsertionFailedException extends Exception {
        public PostImageInsertionFailedException(String message) {
            super(message);
        }
    }
    public static class ImageNotFoundException extends Exception {
        public ImageNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class UserInsertionException extends Exception {
        public UserInsertionException(String message) {
            super(message);
        }
    }
    
    

}
