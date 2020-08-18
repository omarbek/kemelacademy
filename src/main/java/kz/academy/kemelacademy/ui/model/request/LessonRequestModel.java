package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
@Data
public class LessonRequestModel {
    
    private Long lessonTypeId;
    private Long chapterId;
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
    
}
