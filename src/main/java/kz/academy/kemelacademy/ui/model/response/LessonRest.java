package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
@Data
public class LessonRest {
    
    private Long id;
    private String chapter;
    private Integer lessonNo;
    private String name;
    
}
