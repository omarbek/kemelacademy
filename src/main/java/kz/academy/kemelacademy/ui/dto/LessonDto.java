package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
@Data
public class LessonDto implements Serializable {
    
    private Long id;
    private ChapterDto chapterDto = new ChapterDto();
    private Integer lessonNo;
    private String name;
    
    private String url;
    private boolean alwaysOpen;
    private Double duration = 0.0;
    private String videoId;
    private boolean finished;
    private int progress;
    private Date createdAt;
    private Date createdDate;
    
    private String fileName;
    private String fileUrl;
    
    private String description;
    
    @Override
    public String toString() {
        return name;
    }
    
}
