package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-21
 * @project kemelacademy
 */
@Data
public class UserLoginRequestModel {
    
    private String email;
    private String password;
    
}
