package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

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
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private Integer lessonCount;
    private Integer duration;
    
}
