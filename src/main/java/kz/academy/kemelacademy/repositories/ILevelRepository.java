package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.LevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-04
 * @project kemelacademy
 */
public interface ILevelRepository extends JpaRepository<LevelEntity, Long> {
}
