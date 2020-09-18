package kz.academy.kemelacademy.ui.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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
@Table(name = "user_home_works")
public class UserHomeWorkEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "home_work_id")
    private HomeWorkEntity homeWork;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HomeWorkStatusEntity homeWorkStatus = new HomeWorkStatusEntity();
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileEntity file = new FileEntity();
    
    private Integer grade;
    
    private String comment;
    
    @Override
    public String toString() {
        return grade + ", " + comment;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        UserHomeWorkEntity that = (UserHomeWorkEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(homeWork, that.homeWork) &&
                Objects.equals(homeWorkStatus, that.homeWorkStatus) &&
                Objects.equals(grade, that.grade) &&
                Objects.equals(comment, that.comment);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, grade, comment);
    }
    
}
