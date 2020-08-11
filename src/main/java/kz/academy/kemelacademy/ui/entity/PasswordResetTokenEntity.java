package kz.academy.kemelacademy.ui.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
@Data
@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {
    
    @Id
    @GeneratedValue
    private long id;
    
    private String token;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userDetails;
    
}
