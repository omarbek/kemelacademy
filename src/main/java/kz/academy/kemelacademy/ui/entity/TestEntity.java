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
@Table(name = "tests")
public class TestEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LessonEntity lesson = new LessonEntity();
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileEntity file = new FileEntity();
    
    private String description;
    
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private Set<UserTestEntity> userTests = new HashSet<>();
    
    @Override
    public String toString() {
        return description;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        TestEntity that = (TestEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(lesson, that.lesson) &&
                Objects.equals(file, that.file) &&
                Objects.equals(description, that.description) &&
                Objects.equals(userTests, that.userTests);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
    
}
