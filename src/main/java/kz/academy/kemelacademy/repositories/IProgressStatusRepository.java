package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.ProgressStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-11-15
 * @project kemelacademy
 */
public interface IProgressStatusRepository extends JpaRepository<ProgressStatusEntity, Long> {
}