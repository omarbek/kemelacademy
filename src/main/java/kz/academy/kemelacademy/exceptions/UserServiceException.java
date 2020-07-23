package kz.academy.kemelacademy.exceptions;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-22
 * @project kemelacademy
 */
public class UserServiceException extends RuntimeException {
    
    public UserServiceException(String message) {
        super(message);
    }
}
