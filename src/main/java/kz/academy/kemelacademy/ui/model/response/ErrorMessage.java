package kz.academy.kemelacademy.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-22
 * @project kemelacademy
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
    
    private Date timestamp;
    private String message;
    
}
