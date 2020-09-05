package kz.academy.kemelacademy.utils;

import kz.academy.kemelacademy.repositories.IFeatureFlagRepository;
import kz.academy.kemelacademy.repositories.ISystemParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-05
 * @project kemelacademy
 */
@Component
public class SystemParameterUtils {
    
    @Autowired
    private ISystemParameterRepository systemParameterRepository;
    
    @Autowired
    private IFeatureFlagRepository featureFlagRepository;
    
    public String getPathFolder() {
        String pathFolder;
        if (featureFlagRepository.isSwitchOn(IFeatureFlagRepository.GET_UPLOADED_FOLDER_REAL)) {
            pathFolder = systemParameterRepository.getValueByCode(ISystemParameterRepository.UPLOADED_FOLDER_REAL);
        } else {
            pathFolder = systemParameterRepository.getValueByCode(ISystemParameterRepository.UPLOADED_FOLDER_LOCAL);
        }
        return pathFolder;
    }
    
}
