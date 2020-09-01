package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserCourseDto {

    private long id;
    private Boolean finished = false;
    private Set<UserDto> user = new HashSet<>();
    private Set<CourseDto> courses = new HashSet<>();


}
