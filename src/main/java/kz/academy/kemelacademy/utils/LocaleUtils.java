package kz.academy.kemelacademy.utils;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
public class LocaleUtils {
    
    public static boolean checkLocale(String locale) {
        return locale.equals(LocaleContextHolder.getLocale().getLanguage());
    }
    
}
