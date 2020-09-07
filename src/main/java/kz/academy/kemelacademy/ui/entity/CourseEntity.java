package kz.academy.kemelacademy.ui.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-06
 * @project kemelacademy
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "courses")
public class CourseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private UserEntity author = new UserEntity();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category = new CategoryEntity();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private LevelEntity level = new LevelEntity();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
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
    private CourseStatusEntity courseStatus = new CourseStatusEntity();
    
    @OneToMany(
            mappedBy = "course",
            cascade = CascadeType.ALL
    )
    private Set<ChapterEntity> chapters = new HashSet<>();
    
    @OneToMany(mappedBy = "course")
    private Set<UserCourseEntity> users = new HashSet<>();
    
    //todo add certificate_id
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        CourseEntity that = (CourseEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(author, that.author) &&
                Objects.equals(category, that.category) &&
                Objects.equals(level, that.level) &&
                Objects.equals(language, that.language) &&
                Objects.equals(price, that.price) &&
                Objects.equals(description, that.description) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(requirements, that.requirements) &&
                Objects.equals(learns, that.learns) &&
                Objects.equals(courseStatus, that.courseStatus) &&
                Objects.equals(chapters, that.chapters) &&
                Objects.equals(users, that.users);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, category, level, language, price, description, deleted, requirements,
                learns, courseStatus);
    }
}