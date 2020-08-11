package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-04
 * @project kemelacademy
 */
@Data
public class LevelRest {
    
    private long id;
    private String name;
    private Set<String> courses = new HashSet<>();
    
}
