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
 * on 2020-11-15
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "progress_statuses")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProgressStatusEntity extends AbstractNameEntity {
    
    public static final long DRAFT = 1;
    public static final long REVIEWING = 2;
    public static final long PUBLISHED = 3;
    public static final long DECLINED = 4;
    
    @OneToMany(
            mappedBy = "progressStatus",
            cascade = CascadeType.ALL
    )
    private Set<CourseEntity> courses = new HashSet<>();
    
}
