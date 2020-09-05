package kz.academy.kemelacademy.ui.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user_courses")
public class UserCourseEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @Column(nullable = false)
    private Boolean finished = false;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_courses",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")}
    )
    private Set<CourseEntity> courses = new HashSet<>();
    
    private Boolean deleted = false;
    
}
