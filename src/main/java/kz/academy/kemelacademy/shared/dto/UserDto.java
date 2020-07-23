package kz.academy.kemelacademy.shared.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Data
public class UserDto implements Serializable {
    
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    
}
