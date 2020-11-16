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
    
    @Query("SELECT course FROM CourseEntity course" +
            " WHERE course.name LIKE CONCAT('%',:name,'%')" +
            " and course.progressStatus.id = :progressStatusId" +
            " order by course.id")
    List<CourseEntity> findByNameOrderByIdAsc(String name, Long progressStatusId);
    
    @Query("SELECT course FROM CourseEntity course" +
            " WHERE course.name LIKE CONCAT('%',:name,'%')" +
            " order by course.id")
    List<CourseEntity> findByNameOrderByIdAsc(String name);
    
    @Query("select course from CourseEntity course" +
            " where course.progressStatus.id = :progressStatusId" +
            " order by course.id")
    Page<CourseEntity> findAllByOrderByIdAsc(Pageable pageable, Long progressStatusId);
    
    @Query("select course from CourseEntity course" +
            " order by course.id")
    Page<CourseEntity> findAllByOrderByIdAsc(Pageable pageable);
    
    @Query("select course from CourseEntity course" +
            " left join course.users course_users" +
            " where course_users.user.id = :userId" +
            " and course.progressStatus.id = 3" +
            " order by course.id")
    Page<CourseEntity> myCoursesAsPupilOrderByIdAsc(Pageable pageable, Long userId);
    
    @Query("select course from CourseEntity course" +
            " where course.author.id = :userId" +
            " and course.progressStatus.id = 3" +
            " order by course.id")
    Page<CourseEntity> myCoursesAsTeacherOrderByIdAsc(Pageable pageable, Long userId);
    
    @Override
    @Query("select course from CourseEntity course where course.id = :id")
    Optional<CourseEntity> findById(Long id);
}
