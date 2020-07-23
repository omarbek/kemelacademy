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
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("No record found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATE_RECORD("Could not update record"),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address not verified"),
    
    ;
    
    @Getter
    @Setter
    private String errorMessage;
    
    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
