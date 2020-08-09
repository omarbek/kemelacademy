package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
public interface ICourseRepository extends JpaRepository<CourseEntity, Long> {
    
    @Override
    @Query("select c from CourseEntity c where c.deleted = false and id = :id")
    Optional<CourseEntity> findById(Long id);
    
}
