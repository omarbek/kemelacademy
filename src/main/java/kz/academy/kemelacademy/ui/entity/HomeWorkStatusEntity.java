package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "home_work_statuses")
@EqualsAndHashCode(callSuper = true)
public class HomeWorkStatusEntity extends AbstractNameEntity {
    
    public static final Long NOT_OPEN = 1L;
    public static final Long DOWNLOADED = 2L;
    public static final Long COMPLETE = 3L;
    
    @OneToMany(
            mappedBy = "homeWorkStatus",
            cascade = CascadeType.ALL
    )
    private Set<UserHomeWorkEntity> userHomeWorks = new HashSet<>();
    
}
