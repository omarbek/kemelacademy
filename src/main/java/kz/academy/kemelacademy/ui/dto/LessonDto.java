package kz.academy.kemelacademy.ui.dto;

import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.utils.LocaleUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
@Data
public class LessonDto implements Serializable {
    
    private Long id;
    private LessonTypeDto lessonTypeDto = new LessonTypeDto();
    private ChapterDto chapterDto = new ChapterDto();
    private Integer lessonNo;
    private Integer duration;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    
    private String url;
    private boolean alwaysOpen;
    
    private String fileName;
    
    private String testFileName;
    private String description;
    
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
