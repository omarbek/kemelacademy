package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileTypeDto implements Serializable {

    private long id;
    private String nameKz;
    private String nameRu;
    private String nameEn;

}
