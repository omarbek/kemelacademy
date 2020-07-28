package kz.academy.kemelacademy.ui.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-22
 * @project kemelacademy
 */
public enum ErrorMessages {
    
    MISSING_REQUIRED_FIELD("Missing required field"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("No record found"),
    DID_NOT_SEND_EMAIL("Did not send email"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    
    ;
    
    @Getter
    @Setter
    private String errorMessage;
    
    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
