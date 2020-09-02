package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-11
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "chapters")
@EqualsAndHashCode()
public class ChapterEntity {
    
    @Id
    @GeneratedValue
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
    
    private boolean deleted = false;
    
    @OneToMany(
            mappedBy = "chapter",
            cascade = CascadeType.ALL
    )
    private Set<LessonEntity> lessons = new HashSet<>();
    
    @Override
    public String toString() {
        return name;
    }
    
}
