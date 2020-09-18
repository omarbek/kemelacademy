package kz.academy.kemelacademy.ui.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-16
 * @project kemelacademy
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "home_works")
public class HomeWorkEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LessonEntity lesson = new LessonEntity();
    
    private String description;
    
    @OneToMany(mappedBy = "homeWork", cascade = CascadeType.ALL)
    private Set<UserHomeWorkEntity> userHomeWorks = new HashSet<>();
    
    @Override
    public String toString() {
        return description;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        HomeWorkEntity that = (HomeWorkEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(lesson, that.lesson) &&
                Objects.equals(description, that.description) &&
                Objects.equals(userHomeWorks, that.userHomeWorks);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
    
}
