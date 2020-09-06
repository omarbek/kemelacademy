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
public class CourseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
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
    
    @Column(nullable = false)
    private String description;
    
    private Boolean deleted = false;
    
    @Column(nullable = false)
    private String requirements;
    
    @Column(nullable = false)
    private String learns;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_status_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CourseStatusEntity courseStatus = new CourseStatusEntity();
    
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private Set<ChapterEntity> chapters = new HashSet<>();
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "course_users",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<UserEntity> pupils = new HashSet<>();
    
    @OneToMany(mappedBy = "course")
    private Set<UserCourseEntity> userCourses;
    
    //todo add certificate_id
    
    @Override
    public String toString() {
        return name;
    }
    
}