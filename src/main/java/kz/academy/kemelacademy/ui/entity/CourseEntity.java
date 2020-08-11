package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-06
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "courses")
@EqualsAndHashCode(callSuper = true)
@ToString
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
    
    //todo add certificate_id
    
}