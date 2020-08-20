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
    
    MISSING_REQUIRED_FIELD("missing.required.field"),//Пока он только в двух местах использован
    INTERNAL_SERVER_ERROR("internal.server.error"),
    NO_RECORD_FOUND("no.record.found"),
    DID_NOT_SEND_EMAIL("did.not.send.email"),//Кажется здесь нужно еще добавить и неверно отправившихся почту
    EMAIL_ALREADY_EXISTS("email.already.exists"),//how to dont miss the InternalServerError in the cases like this
    PLEASE_SELECT_FILE("please.select.file"),//how to dont miss the InternalServerError in the cases like this
    
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
