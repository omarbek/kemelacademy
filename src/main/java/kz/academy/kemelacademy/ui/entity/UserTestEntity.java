package kz.academy.kemelacademy.ui.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-21
 * @project kemelacademy
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tests")
public class UserTestEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    @Override
    public String toString() {
        return grade + ", " + comment;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        UserTestEntity that = (UserTestEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(test, that.test) &&
                Objects.equals(testStatus, that.testStatus) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(files, that.files);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, grade, comment);
    }
    
}
