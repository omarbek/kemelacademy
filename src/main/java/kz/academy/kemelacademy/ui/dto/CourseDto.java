package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Data
public class CourseDto implements Serializable {
    
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
    
}
