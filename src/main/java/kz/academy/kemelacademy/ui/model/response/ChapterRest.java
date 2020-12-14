package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
@Data
public class ChapterRest {
    
    private Long id;
    private String course;
    private Integer chapterNo;
    private String name;
    private Integer lessonCount;
    private Double duration;
    private List<LessonRest> lessons = new ArrayList<>();
    
}
