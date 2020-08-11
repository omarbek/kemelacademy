package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class LanguageRest {
    
    private long id;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    private String name;
    private Set<String> courses = new HashSet<>();
    
}
