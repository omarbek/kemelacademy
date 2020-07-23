package kz.academy.kemelacademy.ui.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private Boolean emailVerificationStatus=false;
    
}
