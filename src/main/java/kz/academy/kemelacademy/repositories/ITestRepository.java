package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-20
 * @project kemelacademy
 */
public interface ITestRepository extends JpaRepository<TestEntity, Long> {
}
