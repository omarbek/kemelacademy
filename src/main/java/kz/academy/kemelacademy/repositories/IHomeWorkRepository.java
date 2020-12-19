package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.HomeWorkEntity;
import kz.academy.kemelacademy.ui.entity.UserHomeWorkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-20
 * @project kemelacademy
 */
public interface IHomeWorkRepository extends JpaRepository<HomeWorkEntity, Long> {
    
    @Query("select u from UserHomeWorkEntity u where u.homeWork.lesson.chapter.course.id = :courseId")
    Page<UserHomeWorkEntity> findAllByCourseId(Pageable pageable, Long courseId);
}
