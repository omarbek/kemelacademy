package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.ChapterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
public interface IChapterRepository extends JpaRepository<ChapterEntity, Long> {
    
    @Query("select ch from ChapterEntity ch" +
            " where ch.course.id = :courseId" +
            " order by ch.chapterNo")
    Page<ChapterEntity> findAllByOrderByChapterNoAsc(Pageable pageable, Long courseId);
    
}
