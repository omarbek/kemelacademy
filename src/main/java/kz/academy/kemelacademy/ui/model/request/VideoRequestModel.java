package kz.academy.kemelacademy.ui.model.request;

import lombok.Data;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-20
 * @project kemelacademy
 */
@Data
public class VideoRequestModel {
    
    private Long lessonId;
    private String url;
    private boolean alwaysOpen;
    private Integer duration;
    
}
