package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Data
public class UserRest {
    
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Set<String> roles = new HashSet<>();
    private Set<String> courses = new HashSet<>();
    
}
