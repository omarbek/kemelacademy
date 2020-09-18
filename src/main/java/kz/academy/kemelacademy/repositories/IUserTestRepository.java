package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.UserHomeWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-23
 * @project kemelacademy
 */
public interface IUserTestRepository extends JpaRepository<UserHomeWorkEntity, Long> {
}
