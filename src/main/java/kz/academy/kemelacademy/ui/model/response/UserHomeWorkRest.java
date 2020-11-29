package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-22
 * @project kemelacademy
 */
@Data
public class UserHomeWorkRest {
    
    private Long id;
    private String homeWorkName;
    private String status;
    private Integer grade;
    private String comment;
    private String fileName;
    private String fullName;
    
}
