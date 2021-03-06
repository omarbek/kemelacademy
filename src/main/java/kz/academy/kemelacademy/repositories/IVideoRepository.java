package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
public interface IVideoRepository extends JpaRepository<VideoEntity, Long> {
}
