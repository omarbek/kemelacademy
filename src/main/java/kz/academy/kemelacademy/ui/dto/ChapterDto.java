package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
@Data
public class ChapterDto implements Serializable {
    
    private Long id;
    private CourseDto courseDto = new CourseDto();
    private Integer chapterNo;
    private String name;
    private Set<LessonDto> lessons = new HashSet<>();
    
    @Override
    public String toString() {
        return name;
    }
    
}
