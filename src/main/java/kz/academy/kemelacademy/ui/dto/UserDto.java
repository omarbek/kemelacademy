package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Data
public class UserDto implements Serializable {
    
    private long id;
    private String email;
    private Boolean emailVerificationStatus = false;
    private String emailVerificationToken;
    private String encryptedPassword;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String userId;
    private Set<RoleDto> roles = new HashSet<>();
    private Set<CourseDto> courses = new HashSet<>();
    
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
