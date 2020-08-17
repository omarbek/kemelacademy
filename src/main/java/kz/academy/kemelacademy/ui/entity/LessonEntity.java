package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-14
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "lessons")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LessonEntity extends AbstractNameEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_type_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LessonTypeEntity lessonType = new LessonTypeEntity();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ChapterEntity chapter = new ChapterEntity();
    
    @Column(name = "lesson_no")
    private Integer lessonNo;
    
    private Integer duration;
    
    private boolean deleted = false;
    
    @OneToOne(mappedBy = "lesson")
    private VideoEntity video;
    
    @OneToOne(mappedBy = "lesson")
    private FileEntity file;
    
    @OneToOne(mappedBy = "lesson")
    private TestEntity test;
    
}
