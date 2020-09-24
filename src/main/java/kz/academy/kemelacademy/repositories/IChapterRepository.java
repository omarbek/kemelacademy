package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.ChapterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
public interface IChapterRepository extends JpaRepository<ChapterEntity, Long> {
    
    Page<ChapterEntity> findAllByOrderByChapterNoAsc(Pageable pageable);
    
    
}
