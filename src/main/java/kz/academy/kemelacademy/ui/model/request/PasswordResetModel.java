package kz.academy.kemelacademy.ui.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
@Getter
@Setter
public class PasswordResetModel {
    
    private String token;
    private String password;
    
}
