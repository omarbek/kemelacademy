package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Data
public class CourseRest {
    
    private long id;
    private String author;
    private String category;
    private String level;
    private String language;
    private Integer price;
    private Double rating;
    private Integer duration;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private String descriptionKz;
    private String descriptionRu;
    private String descriptionEn;
    private String aboutCourseKz;
    private String aboutCourseRu;
    private String aboutCourseEn;
    private Integer chapterCount;
    private Integer lessonCount;
    
}
