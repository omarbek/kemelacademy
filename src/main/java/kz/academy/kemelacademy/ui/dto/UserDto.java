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
    private String fullName;
    private String userId;
    private Set<RoleDto> roles = new HashSet<>();
    private Set<CourseDto> coursesAsAuthor = new HashSet<>();
    private Set<CourseDto> coursesAsPupil = new HashSet<>();
    
    @Override
    public String toString() {
        return fullName;
    }
    
}
