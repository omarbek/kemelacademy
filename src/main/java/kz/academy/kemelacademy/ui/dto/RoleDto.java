package kz.academy.kemelacademy.ui.dto;

import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.utils.LocaleUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
@Data
public class RoleDto implements Serializable {
    
    private long id;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private String name;
    private Set<UserDto> users = new HashSet<>();
    
    @Override
    public String toString() {
        String ret;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            ret = nameKz;
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            ret = nameRu;
        } else {
            ret = nameEn;
        }
        return ret;
    }
    
}
