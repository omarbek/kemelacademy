package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-16
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "tests")
public class TestEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LessonEntity lesson = new LessonEntity();
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileEntity file = new FileEntity();
    
    private String description;
    
    @OneToMany(mappedBy = "test")
    private Set<UserTestEntity> userTests;
    
}
