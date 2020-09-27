package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.LessonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
public interface ILessonRepository extends JpaRepository<LessonEntity, Long> {
    
    @Query("select l from LessonEntity l" +
            " where l.chapter.id = :chapterId and l.chapter.course.id = :courseId" +
            " order by l.lessonNo")
    Page<LessonEntity> findAllByOrderByLessonNoAsc(Pageable pageable, Long courseId, Long chapterId);
    
    @Query("select l from LessonEntity l where l.chapter.course.id = :courseId order by l.lessonNo")
    Page<LessonEntity> findAllByOrderByLessonNoAsc(Pageable pageable, Long courseId);
    
}
