package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-04
 * @project kemelacademy
 */
@Data
public class LevelDto implements Serializable {
    
    private long id;
    private String nameKz;
    private String nameRu;
    private String nameEn;
    
}
