package kz.academy.kemelacademy.ui.dto;

import kz.academy.kemelacademy.ui.entity.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
@Data
public class RoleDto implements Serializable {
    
    private long id;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private String name;
    private Set<UserEntity> users = new HashSet<>();
    
}
