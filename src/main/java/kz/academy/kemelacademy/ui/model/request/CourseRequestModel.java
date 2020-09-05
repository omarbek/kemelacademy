package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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
    private String name;
    private String description;
    private String requirements;
    private String learns;
    private Long courseStatusId;
    private Set<Long> pupils = new HashSet<>();
    
}
