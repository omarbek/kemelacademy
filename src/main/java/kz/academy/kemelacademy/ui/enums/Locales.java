package kz.academy.kemelacademy.ui.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
public enum Locales {
    
    KZ("kz"),
    RU("ru"),
    
    ;
    
    @Getter
    @Setter
    private String locale;
    
    Locales(String locale) {
        this.locale = locale;
    }
    
}
