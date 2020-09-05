package kz.academy.kemelacademy.ui.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-14
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "video")
public class VideoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private LessonEntity lesson = new LessonEntity();
    
    private String url;
    
    @Column(name = "always_open")
    private boolean alwaysOpen = false;
    
}
