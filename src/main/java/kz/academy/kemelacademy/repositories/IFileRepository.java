package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-20
 * @project kemelacademy
 */
public interface IFileRepository extends JpaRepository<FileEntity, Long> {
}
