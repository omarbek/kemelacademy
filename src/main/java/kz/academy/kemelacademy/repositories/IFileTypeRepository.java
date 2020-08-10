package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.FileTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileTypeRepository extends JpaRepository<FileTypeEntity, Long>{
}
