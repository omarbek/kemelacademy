package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Data
public class UserRest {//todo get all roles
    
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    
}
