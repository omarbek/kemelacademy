package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

@Data
public class UserCourseDto {
    
    private Long id;
    private Boolean finished;
    private CourseDto course = new CourseDto();
    
}
