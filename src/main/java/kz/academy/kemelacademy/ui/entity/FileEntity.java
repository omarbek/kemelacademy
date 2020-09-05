package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-16
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "files")
public class FileEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private LessonEntity lesson = new LessonEntity();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_type_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private FileTypeEntity fileType = new FileTypeEntity();
    
    private String name;
    
    @OneToOne(mappedBy = "file")
    private TestEntity test;
    
    @ManyToMany(mappedBy = "files")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserTestEntity> userTests = new HashSet<>();
    
}
