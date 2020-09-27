package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-23
 * @project kemelacademy
 */
@Data
public class FileDto implements Serializable {
    
    private Long id;
    private LessonDto lesson = new LessonDto();
    private FileTypeDto fileType = new FileTypeDto();
    private String name;
    private LessonDto test = new LessonDto();
    private List<UserHomeWorkDto> userTests = new ArrayList<>();
    
}
