package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
public interface ICourseRepository extends JpaRepository<CourseEntity, Long> {
    
    @Query("SELECT c FROM CourseEntity c" +
            " WHERE c.name LIKE CONCAT('%',:name,'%')" +
            " order by c.id")
    List<CourseEntity> findByNameOrderByIdAsc(String name);
    
    Page<CourseEntity> findAllByOrderByIdAsc(Pageable pageable);
    
}
