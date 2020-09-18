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

@Data
@Entity
@Table(name = "lesson_types")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LessonTypeEntity extends AbstractNameEntity {
    
    public static final Long VIDEO = 1L;
    public static final Long FILE = 2L;
    public static final Long HOME_WORK = 3L;
    
    @OneToMany(
            mappedBy = "lessonType",
            cascade = CascadeType.ALL
    )
    private Set<LessonEntity> lessons = new HashSet<>();
    
}
