package kz.academy.kemelacademy.ui.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user_courses")
public class UserCourseEntity implements Serializable {
    
    @EmbeddedId
    private UserCourseId userCourseId;
    
    @ManyToOne
    @MapsId("userId")
    private UserEntity user;
    
    @ManyToOne
    @MapsId("courseId")
    private CourseEntity course = new CourseEntity();
    
    private Boolean finished;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        UserCourseEntity that = (UserCourseEntity) o;
        return Objects.equals(userCourseId, that.userCourseId) &&
                Objects.equals(user, that.user) &&
                Objects.equals(course, that.course) &&
                Objects.equals(finished, that.finished);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userCourseId, user, finished);
    }
    
    @Override
    public String toString() {
        return "UserCourseEntity{" +
                "userCourseId=" + userCourseId +
                ", user=" + user +
                ", course=" + course +
                ", finished=" + finished +
                '}';
        
        
    }
}
