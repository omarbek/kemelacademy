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
    private String name;
    private String description;
    private String requirements;
    private String learns;
    private Integer chapterCount;
    private Integer lessonCount;
    
}
