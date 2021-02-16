package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.TestStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITestStatusRepository extends JpaRepository<TestStatusEntity, Long> {
}
