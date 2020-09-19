package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "lesson_types")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LessonTypeEntity extends AbstractNameEntity {
    
    public static final Long VIDEO = 1L;
    public static final Long FILE = 2L;
    public static final Long HOME_WORK = 3L;
    
}
