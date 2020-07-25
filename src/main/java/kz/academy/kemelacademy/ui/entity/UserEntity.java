package kz.academy.kemelacademy.ui.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
    
    @Id
    @GeneratedValue
    private long id;
    
    @Column(nullable = false, length = 120)
    private String email;
    
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;
    
    private String emailVerificationToken;
    
    @Column(nullable = false)
    @NotNull(message = "Password must not be null")
    private String encryptedPassword;
    
    @Column(nullable = false, length = 120)
    @NotNull(message = "First name must not be null")
    private String firstName;
    
    @Column(length = 120)
    @NotNull(message = "Last name not be null")
    private String lastName;
    
    @Column(length = 120)
    private String patronymic;
    
    @Column(nullable = false)
    private String userId;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    Set<RoleEntity> roles = new HashSet<>();
    
    @Override
    public String toString() {
        StringBuilder fullNameSB = new StringBuilder();
        fullNameSB.append(lastName);
        fullNameSB.append(" ");
        fullNameSB.append(firstName);
        if (patronymic != null) {
            fullNameSB.append(" ");
            fullNameSB.append(patronymic);
        }
        
        return fullNameSB.toString();
    }
    
}
