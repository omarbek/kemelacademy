package kz.academy.kemelacademy.ui.entity;

import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.utils.LocaleUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoleEntity extends AbstractNameEntity {
    
    public static final Long MODERATOR = 1L;
    public static final Long INSTRUCTOR = 2L;
    public static final Long STUDENT = 3L;
    
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String ROLE_INSTRUCTOR = "ROLE_INSTRUCTOR";
    public static final String ROLE_STUDENT = "ROLE_STUDENT";
    
    @Column(nullable = false, length = 40)
    private String name;
    
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserEntity> users = new HashSet<>();
    
}
