package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Data
public class CourseRequestModel {
    
    private Long categoryId;
    private Long levelId;
    private Long languageId;
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
