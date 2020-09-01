package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

@Data
public class UserCourseRequestModel {

    private Long id;
    private Long user_id;
    private Long course_id;
    private boolean finished;

}
