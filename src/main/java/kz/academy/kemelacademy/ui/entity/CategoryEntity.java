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
 * on 2020-07-28
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "categories")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryEntity extends AbstractNameEntity {
    
    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL
    )
    private Set<CourseEntity> courses = new HashSet<>();
    
}
