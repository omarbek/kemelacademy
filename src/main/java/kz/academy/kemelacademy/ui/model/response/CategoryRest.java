package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
@Data
public class CategoryRest {
    
    private Long id;
    private String name;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private Set<String> courses = new HashSet<>();
    
}
