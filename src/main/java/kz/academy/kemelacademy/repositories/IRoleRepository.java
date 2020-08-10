package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
}
