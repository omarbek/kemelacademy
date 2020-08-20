package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
public interface ILessonRepository extends JpaRepository<LessonEntity, Long> {
    
    @Override
    @Query("select l from LessonEntity l where l.deleted = false and id = :id")
    Optional<LessonEntity> findById(Long id);
    
}
