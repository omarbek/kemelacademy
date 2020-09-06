package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

@Data
public class UserCourseRest {
    
    private Long id;
    private CourseRest courseRest;
    private boolean finished;
    
}
