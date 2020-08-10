package kz.academy.kemelacademy.ui.dto;

import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.utils.LocaleUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Data
public class CourseDto implements Serializable {
    
    private long id;
    private UserDto author = new UserDto();
    private CategoryDto category = new CategoryDto();
    private LevelDto level = new LevelDto();
    private LanguageDto language = new LanguageDto();
    private Integer price;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private String descriptionKz;
    private String descriptionRu;
    private String descriptionEn;
    private String aboutCourseKz;
    private String aboutCourseRu;
    private String aboutCourseEn;
    
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
