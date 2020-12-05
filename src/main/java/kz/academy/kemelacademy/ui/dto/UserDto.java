package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<RoleDto> roles = new ArrayList<>();
    private List<CourseDto> coursesAsAuthor = new ArrayList<>();
    private List<CourseDto> coursesAsPupil = new ArrayList<>();
    private Boolean agreedWithAgreement;
    private String videoCallUrl;
    
    @Override
    public String toString() {
        return fullName;
    }
    
}
