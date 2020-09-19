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
    private ChapterDto chapterDto = new ChapterDto();
    private Integer lessonNo;
    private String name;
    
    @Override
    public String toString() {
        return name;
    }
    
}
