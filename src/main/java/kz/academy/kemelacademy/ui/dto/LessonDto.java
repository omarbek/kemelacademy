package kz.academy.kemelacademy.ui.dto;

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
    private String name;
    
    private String url;
    private boolean alwaysOpen;
    
    private String fileName;
    
    private String testFileName;
    private String description;
    
    @Override
    public String toString() {
        return name;
    }
    
}
