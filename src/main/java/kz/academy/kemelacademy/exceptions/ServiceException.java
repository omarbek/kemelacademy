package kz.academy.kemelacademy.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-22
 * @project kemelacademy
 */
@Slf4j
public class ServiceException extends RuntimeException {
    
    public ServiceException(String message) {
        super(message);
        log.error(message, this);
    }
    
}
