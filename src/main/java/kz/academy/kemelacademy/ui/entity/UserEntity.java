package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Entity(name = "users")
@Data
public class UserEntity implements Serializable {
    
    @Id
    @GeneratedValue
    private long id;
    
    @Column(nullable = false)
    private String userId;
    
    @Column(nullable = false, length = 50)
    private String firstName;
    
    @Column(nullable = false, length = 50)
    private String lastName;
    
    @Column(nullable = false, length = 120)
    private String email;
    
    @Column(nullable = false)
    private String encryptedPassword;
    
    private String emailVerificationToken;
    
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    //    @NotNull(message = GeneratorUtils.NotNullUtils.ROLE_MUST_NOT_BE_NULL_VALUE)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private RoleEntity role;
    
}
