package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.FeatureFlagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-05
 * @project kemelacademy
 */
public interface IFeatureFlagRepository extends JpaRepository<FeatureFlagEntity, Long> {

    String GET_UPLOADED_FOLDER_REAL = "GET_UPLOADED_FOLDER_REAL";
    
    @Query("select ff.switchOn from FeatureFlagEntity ff where ff.code = :code")
    Boolean isSwitchOn(String code);
    
}
