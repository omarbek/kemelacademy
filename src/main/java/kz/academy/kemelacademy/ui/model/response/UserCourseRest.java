package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserCourseRest {

    private Long id;
    private Set<String> users = new HashSet<>();
    private Set<String> courses = new HashSet<>();
    private boolean finished;

}
