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
 * on 2020-08-04
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "levels")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LevelEntity extends AbstractNameEntity {
    
    @OneToMany(
            mappedBy = "level",
            cascade = CascadeType.ALL
    )
    private Set<CourseEntity> courses = new HashSet<>();
    
}
