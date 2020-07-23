package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Data
public class UserRest {
    
    private String userId;
    private String firstName;
    private String lastName;
    private String email;

}
