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
@Table(name = "file_types")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FileTypeEntity extends AbstractNameEntity {
    
    public static final Long FOR_DOWNLOAD = 1L;
    public static final Long FOR_SEND_TESTS = 2L;
    public static final Long FOR_CERTIFICATE = 3L;
    public static final Long FOR_UPLOAD_TESTS = 4L;
    
    @OneToMany(
            mappedBy = "fileType",
            cascade = CascadeType.ALL
    )
    private Set<FileEntity> files = new HashSet<>();
    
}
