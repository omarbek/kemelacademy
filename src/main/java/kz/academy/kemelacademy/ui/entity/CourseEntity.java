package kz.academy.kemelacademy.ui.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-06
 * @project kemelacademy
 */
@Entity
@Setter
@Getter
@Table(name = "courses")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CourseEntity extends AbstractNameEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity author = new UserEntity();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CategoryEntity category = new CategoryEntity();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LevelEntity level = new LevelEntity();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LanguageEntity language = new LanguageEntity();
    
    private Integer price;
    
    @Column(name = "description_kz")
    private String descriptionKz;
    
    @Column(name = "description_ru")
    private String descriptionRu;
    
    @Column(name = "description_en")
    private String descriptionEn;
    
    @Column(name = "about_course_kz")
    private String aboutCourseKz;
    
    @Column(name = "about_course_ru")
    private String aboutCourseRu;
    
    @Column(name = "about_course_en")
    private String aboutCourseEn;
    
    private Boolean deleted = false;
    
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private Set<ChapterEntity> chapters = new HashSet<>();
    
    //todo add certificate_id
    
    @Override
    public String toString() {
        return super.toString();
    }
    
}