package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.HomeWorkStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHomeWorkStatusRepository extends JpaRepository<HomeWorkStatusEntity, Long> {
}
