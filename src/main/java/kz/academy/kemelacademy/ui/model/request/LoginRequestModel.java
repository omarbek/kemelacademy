package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-24
 * @project kemelacademy
 */
@Data
public class LoginRequestModel {
    
    private String email;
    private String password;
    
}
