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
    
    private Long id;
    private String userId;
    private String email;
    private String fullName;
    private Set<String> roles = new HashSet<>();
    private Set<String> coursesAsAuthor = new HashSet<>();
    private Set<String> coursesAsPupil = new HashSet<>();
    private boolean agreedWithAgreement;
    
}
