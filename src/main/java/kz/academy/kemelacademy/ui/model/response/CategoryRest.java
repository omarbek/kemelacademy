package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
@Data
public class CategoryRest {
    
    private long id;
    private String name;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    
}
