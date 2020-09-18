package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-23
 * @project kemelacademy
 */
@Data
public class UserHomeWorkDto implements Serializable {
    
    private Long id;
    private UserDto user = new UserDto();
    private LessonDto homeWork = new LessonDto();
    private HomeWorkStatusDto homeWorkStatus = new HomeWorkStatusDto();
    private Integer grade;
    private String comment;
    private FileDto file = new FileDto();
    
}
