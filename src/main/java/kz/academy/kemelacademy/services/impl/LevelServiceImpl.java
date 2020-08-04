package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.repositories.ILevelRepository;
import kz.academy.kemelacademy.services.ILevelService;
import kz.academy.kemelacademy.ui.dto.LevelDto;
import kz.academy.kemelacademy.ui.entity.LevelEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-04
 * @project kemelacademy
 */
@Service
public class LevelServiceImpl implements ILevelService {
    
    @Autowired
    private ILevelRepository levelRepository;
    
    @Override
    public List<LevelDto> getAll() throws Exception {
        List<LevelDto> returnValue = new ArrayList<>();
        
        List<LevelEntity> levels = levelRepository.findAll();
        
        for (LevelEntity levelEntity: levels) {
            LevelDto levelDto = new LevelDto();
            BeanUtils.copyProperties(levelEntity, levelDto);
            returnValue.add(levelDto);
        }
        
        return returnValue;
    }
    
}
