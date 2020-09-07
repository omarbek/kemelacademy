package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.UserCourseEntity;
import kz.academy.kemelacademy.ui.entity.UserCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserCourseRepository extends JpaRepository<UserCourseEntity, UserCourseId> {
}
