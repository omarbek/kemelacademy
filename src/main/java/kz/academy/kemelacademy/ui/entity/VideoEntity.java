package kz.academy.kemelacademy.ui.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-14
 * @project kemelacademy
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "video")
public class VideoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private LessonEntity lesson = new LessonEntity();
    
    private String url;
    
    @Column(name = "always_open")
    private boolean alwaysOpen = false;
    
    private Double duration;
    private String videoId;
    private boolean finished;
    private int progress;
    private Date createdAt;
    private Date createdDate;
    
    @Override
    public String toString() {
        return url;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        VideoEntity that = (VideoEntity) o;
        return alwaysOpen == that.alwaysOpen &&
                Objects.equals(id, that.id) &&
                Objects.equals(lesson, that.lesson) &&
                Objects.equals(url, that.url) &&
                Objects.equals(videoId, that.videoId) &&
                Objects.equals(finished, that.finished) &&
                Objects.equals(progress, that.progress) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(createdDate, that.createdDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, url, alwaysOpen, duration, videoId, finished, progress, createdAt, createdDate);
    }
    
}
