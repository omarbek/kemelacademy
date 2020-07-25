package kz.academy.kemelacademy.ui.entity;

import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.utils.LocaleUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class RoleEntity {
    
    public static final Long MODERATOR = 1L;
    public static final Long INSTRUCTOR = 2L;
    public static final Long STUDENT = 3L;
    
    @Id
    @GeneratedValue
    private long id;
    
    @Column(length = 20)
    @NotNull(message = "Name kz must not be null")
    private String nameKz;
    
    @Column(nullable = false, length = 20)
    @NotNull(message = "Name ru must not be null")
    private String nameRu;
    
    @Column(nullable = false, length = 20)
    @NotNull(message = "Name en must not be null")
    private String nameEn;
    
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserEntity> users = new HashSet<>();
    
    @Override
    public String toString() {
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = nameKz;
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = nameRu;
        } else {
            name = nameEn;
        }
        return name;
    }
    
}
