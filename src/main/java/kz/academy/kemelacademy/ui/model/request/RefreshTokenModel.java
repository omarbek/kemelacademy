package kz.academy.kemelacademy.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-16
 * @project kemelacademy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenModel {
    
    @NotNull
    private String refreshToken;
    
}
