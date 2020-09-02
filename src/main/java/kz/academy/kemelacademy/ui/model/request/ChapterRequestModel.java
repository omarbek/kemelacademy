package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
@Data
public class ChapterRequestModel {
    
    private Long courseId;
    private Integer chapterNo;
    private String name;
    
}
