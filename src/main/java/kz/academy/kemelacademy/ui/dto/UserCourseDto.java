package kz.academy.kemelacademy.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCourseDto {
    
    private Long id;
    private Boolean finished;
    private CourseDto course = new CourseDto();
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        UserCourseDto that = (UserCourseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(finished, that.finished) &&
                Objects.equals(course, that.course);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, finished, course);
    }
    
    @Override
    public String toString() {
        return "UserCourseDto{" +
                "id=" + id +
                ", finished=" + finished +
                ", course=" + course +
                '}';
    }
    
}
