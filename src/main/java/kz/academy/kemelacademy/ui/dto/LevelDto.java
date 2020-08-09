package kz.academy.kemelacademy.ui.dto;

import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.utils.LocaleUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-04
 * @project kemelacademy
 */
@Data
public class LevelDto implements Serializable {
    
    private Long id;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    
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
