package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-03
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "course_statuses")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CourseStatusEntity extends AbstractNameEntity {
    
    @OneToMany(
            mappedBy = "courseStatus",
            cascade = CascadeType.ALL
    )
    private Set<CourseEntity> courses = new HashSet<>();
    
}
