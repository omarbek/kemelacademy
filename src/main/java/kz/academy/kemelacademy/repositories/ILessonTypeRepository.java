package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.LessonTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILessonTypeRepository extends JpaRepository<LessonTypeEntity, Long> {
}
