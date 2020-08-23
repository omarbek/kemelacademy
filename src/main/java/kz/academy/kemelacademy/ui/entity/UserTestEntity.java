package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-21
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "user_tests")
public class UserTestEntity implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private TestStatusEntity testStatus = new TestStatusEntity();
    
    private Integer grade;
    
    private String comment;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "test_files",
            joinColumns = {@JoinColumn(name = "user_test_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")}
    )
    private Set<FileEntity> files = new HashSet<>();
    
}
