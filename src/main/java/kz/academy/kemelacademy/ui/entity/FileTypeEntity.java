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
@Table(name = "file_type")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FileTypeEntity extends AbstractNameEntity {
    
    @OneToMany(
            mappedBy = "fileType",
            cascade = CascadeType.ALL
    )
    private Set<FileEntity> files = new HashSet<>();
    
}
