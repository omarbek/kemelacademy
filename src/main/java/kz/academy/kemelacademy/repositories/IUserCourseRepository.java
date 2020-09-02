package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.UserCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserCourseRepository extends JpaRepository<UserCourseEntity, Long> {
    
    @Override
    @Query("select u from UserCourseEntity u where u.deleted = false and id = :id")
    Optional<UserCourseEntity> findById(Long id);
    
}
