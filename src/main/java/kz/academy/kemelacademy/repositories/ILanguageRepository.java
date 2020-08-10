package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.LanguageEntity;
import kz.academy.kemelacademy.ui.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILanguageRepository extends JpaRepository<LanguageEntity, Long> {
}
