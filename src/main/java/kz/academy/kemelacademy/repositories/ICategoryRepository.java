package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    
    Page<CategoryEntity> findAllByOrderByIdAsc(Pageable pageable);
    
}
