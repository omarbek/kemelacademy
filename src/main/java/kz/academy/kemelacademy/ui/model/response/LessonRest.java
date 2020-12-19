package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

import java.util.Date;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
@Data
public class LessonRest {
    
    private Long id;
    private String chapter;
    private Integer lessonNo;
    private String name;
    private String videoCallUrl;
    
    private String url;
    private boolean alwaysOpen;
    private Double duration;
    private String videoId;
    private boolean finished;
    private int progress;
    private Date createdAt;
    private Date createdDate;
    
    private String fileName;
    private String fileUrl;
    
    private String description;
    
}
