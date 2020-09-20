package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Data
public class UserDetailsRequestModel {
    
    private String email;
    private String password;
    private String fullName;
    
}
