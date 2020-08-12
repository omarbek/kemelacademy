package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
public interface IChapterRepository extends JpaRepository<ChapterEntity, Long> {
    
    @Override
    @Query("select c from ChapterEntity c where c.deleted = false and id = :id")
    Optional<ChapterEntity> findById(Long id);
    
}
