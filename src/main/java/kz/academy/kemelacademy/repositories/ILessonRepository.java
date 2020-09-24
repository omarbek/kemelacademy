package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.LessonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
public interface ILessonRepository extends JpaRepository<LessonEntity, Long> {
    
    Page<LessonEntity> findAllByOrderByLessonNoAsc(Pageable pageable);
    
}
