package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.SystemParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-05
 * @project kemelacademy
 */
public interface ISystemParameterRepository extends JpaRepository<SystemParameterEntity, Long> {
    
    String UPLOADED_FOLDER_LOCAL = "UPLOADED_FOLDER_LOCAL";
    String UPLOADED_FOLDER_REAL = "UPLOADED_FOLDER_REAL";
    String EXPIRATION_TIME = "EXPIRATION_TIME";
    
    @Query("select spe.value from SystemParameterEntity spe where spe.code = :code")
    String getValueByCode(String code);
    
}
