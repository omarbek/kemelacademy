package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.CourseStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-03
 * @project kemelacademy
 */
public interface ICourseStatusRepository extends JpaRepository<CourseStatusEntity, Long> {
}
