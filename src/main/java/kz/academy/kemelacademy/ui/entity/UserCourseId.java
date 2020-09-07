package kz.academy.kemelacademy.ui.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-07
 * @project kemelacademy
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseId implements Serializable {
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "course_id")
    private Long courseId;
    
}
