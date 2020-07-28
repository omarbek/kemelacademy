package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
@Data
public class CategoryDto implements Serializable {
    
    private long id;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    
}
