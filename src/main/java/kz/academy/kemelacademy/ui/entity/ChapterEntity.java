package kz.academy.kemelacademy.ui.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-11
 * @project kemelacademy
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chapters")
public class ChapterEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CourseEntity course = new CourseEntity();
    
    @Column(name = "chapter_no")
    private Integer chapterNo;
    
    @OneToMany(
            mappedBy = "chapter",
            cascade = CascadeType.ALL
    )
    private List<LessonEntity> lessons = new ArrayList<>();
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        ChapterEntity that = (ChapterEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(course, that.course) &&
                Objects.equals(chapterNo, that.chapterNo) &&
                Objects.equals(lessons, that.lessons);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, chapterNo);
    }
    
}
