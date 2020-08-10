package kz.academy.kemelacademy.ui.entity;

import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.utils.LocaleUtils;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
@Data
@MappedSuperclass
public abstract class AbstractNameEntity {
    
    @Id
    @GeneratedValue
    private long id;
    
    @Column(nullable = false)
    private String nameKz;
    
    @Column(nullable = false)
    private String nameRu;
    
    @Column(nullable = false)
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
