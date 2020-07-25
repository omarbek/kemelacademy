package kz.academy.kemelacademy.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
public enum NotNullUtils {
    
    NAME_MUST_NOT_BE_NULL("notnullutils.name_mus_not_be_null"),
    
    ;
    
    @Getter
    @Setter
    private String item;
    
    NotNullUtils(String item) {
        this.item=item;
    }
    
    @Component
    public static class BeanInjector {
        
        @Autowired
        private MessageSource messageSource;
        
        @PostConstruct
        public void postConstruct() {
        
        }
    }
    
}
