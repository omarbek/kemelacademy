package kz.academy.kemelacademy.ui.model.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Data
public class CourseRest {
    
    private long id;
    private String author;
    private String category;
    private String level;
    private String language;
    private Integer price;
    private Double rating;
    private Double duration;
    private String name;
    private String description;
    private String requirements;
    private String learns;
    private Integer chapterCount;
    private Integer lessonCount;
    private String courseStatus;
    private Set<Long> pupils = new HashSet<>();
    private String certificateName;
    private List<ChapterRest> chapters = new ArrayList<>();
    private String imageUrl;
    private String progressStatus;
    private String declineReason;
    
}
