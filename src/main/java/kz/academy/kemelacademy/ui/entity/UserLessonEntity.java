package kz.academy.kemelacademy.ui.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Omarbek.Dinassil
 * on 2020-12-06
 * @project kemelacademy
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_lessons")
public class UserLessonEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private LessonEntity lesson;
    
    private boolean finished;
    
    @Override
    public String toString() {
        return user.toString() + ", " + lesson.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        UserLessonEntity that = (UserLessonEntity) o;
        return finished == that.finished &&
                Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, finished);
    }
    
}
