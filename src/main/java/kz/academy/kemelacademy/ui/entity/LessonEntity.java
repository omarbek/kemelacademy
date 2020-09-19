package kz.academy.kemelacademy.ui.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-14
 * @project kemelacademy
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lessons")
public class LessonEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ChapterEntity chapter = new ChapterEntity();
    
    @Column(name = "lesson_no")
    private Integer lessonNo;
    
    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL)
    private VideoEntity video;
    
    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL)
    private FileEntity file;
    
    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL)
    private HomeWorkEntity homeWork;
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        LessonEntity that = (LessonEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(chapter, that.chapter) &&
                Objects.equals(lessonNo, that.lessonNo) &&
                Objects.equals(video, that.video) &&
                Objects.equals(file, that.file) &&
                Objects.equals(homeWork, that.homeWork);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, lessonNo);
    }
    
}
