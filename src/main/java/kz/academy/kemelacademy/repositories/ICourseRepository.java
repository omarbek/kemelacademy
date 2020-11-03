package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
public interface ICourseRepository extends JpaRepository<CourseEntity, Long> {
    
    @Query("SELECT c FROM CourseEntity c" +
            " WHERE c.name LIKE CONCAT('%',:name,'%')" +
            " and c.accepted = true" +
            " order by c.id")
    List<CourseEntity> findByNameOrderByIdAsc(String name);
    
    @Query("select course from CourseEntity course" +
            " where course.accepted = true" +
            " order by course.id")
    Page<CourseEntity> findAllByOrderByIdAsc(Pageable pageable);
    
    @Query("select course from CourseEntity course" +
            " left join course.users course_users" +
            " where course_users.user.id = :userId" +
            " and course.accepted = true" +
            " order by course.id")
    Page<CourseEntity> myCoursesAsPupilOrderByIdAsc(Pageable pageable, Long userId);
    
    @Query("select course from CourseEntity course" +
            " where course.author.id = :userId" +
            " and course.accepted = true" +
            " order by course.id")
    Page<CourseEntity> myCoursesAsTeacherOrderByIdAsc(Pageable pageable, Long userId);
    
    @Override
    @Query("select c from CourseEntity c where c.id = :id and c.accepted = true")
    Optional<CourseEntity> findById(Long id);
    
}
