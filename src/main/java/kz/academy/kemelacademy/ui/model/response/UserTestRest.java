package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-22
 * @project kemelacademy
 */
@Data
public class UserTestRest {
    
    private Long id;
    private String test;
    private String status;
    private Integer grade;
    private String comment;
    private Set<String> fileNames = new HashSet<>();
    
}
