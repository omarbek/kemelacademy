package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-23
 * @project kemelacademy
 */
@Data
public class UserTestDto implements Serializable {
    
    private Long id;
    private UserDto user = new UserDto();
    private LessonDto test = new LessonDto();
    private TestStatusDto testStatus = new TestStatusDto();
    private Integer grade;
    private String comment;
    private Set<FileDto> files = new HashSet<>();
    
}
