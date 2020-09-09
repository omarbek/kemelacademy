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
@Table(name = "files")
public class FileEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private LessonEntity lesson = new LessonEntity();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_type_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private FileTypeEntity fileType = new FileTypeEntity();
    
    private String name;
    
    @OneToOne(mappedBy = "file")
    private TestEntity test;
    
    @ManyToMany(mappedBy = "files")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserTestEntity> userTests = new HashSet<>();
    
    @OneToOne(mappedBy = "certificate")
    private CourseEntity course;
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        FileEntity that = (FileEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(lesson, that.lesson) &&
                Objects.equals(fileType, that.fileType) &&
                Objects.equals(name, that.name) &&
                Objects.equals(test, that.test) &&
                Objects.equals(userTests, that.userTests) &&
                Objects.equals(course, that.course);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
    
}
