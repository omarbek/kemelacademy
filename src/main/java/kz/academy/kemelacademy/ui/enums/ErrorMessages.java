package kz.academy.kemelacademy.ui.enums;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-22
 * @project kemelacademy
 */
public enum ErrorMessages {
    
    MISSING_REQUIRED_FIELD("Missing required field"),//todo
    INTERNAL_SERVER_ERROR("internal.server.error"),
    NO_RECORD_FOUND("no.record.found"),
    DID_NOT_SEND_EMAIL("Did not send email"),//todo
    EMAIL_ALREADY_EXISTS("Email already exists"),//todo
    
    ;
    
    @Setter
    private String errorMessage;
    
    @Setter
    private MessageSource messageSource;
    
    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getErrorMessage() {
        return messageSource.getMessage(errorMessage, null, LocaleContextHolder.getLocale());
    }
    
    @Component
    public static class ErrorMessagesServiceInjector {
        
        @Autowired
        private MessageSource messageSource;
        
        @PostConstruct
        public void postConstruct() {
            for (ErrorMessages errorMessage: EnumSet.allOf(ErrorMessages.class)) {
                errorMessage.setMessageSource(messageSource);
            }
        }
    }
    
}
