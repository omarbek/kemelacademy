package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
@Data
public class LessonRequestModel {
    
    private Long chapterId;
    private Integer lessonNo;
    private String name;
    
}
