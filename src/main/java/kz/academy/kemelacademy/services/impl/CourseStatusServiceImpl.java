package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.ICourseStatusRepository;
import kz.academy.kemelacademy.services.ICourseStatusService;
import kz.academy.kemelacademy.ui.dto.CourseStatusDto;
import kz.academy.kemelacademy.ui.entity.CourseStatusEntity;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-03
 * @project kemelacademy
 */
@Service
public class CourseStatusServiceImpl implements ICourseStatusService {
    
    @Autowired
    private ICourseStatusRepository courseStatusRepository;
    
    @Override
    public CourseStatusDto getEntityById(long id) throws Exception {
        Optional<CourseStatusEntity> optional = courseStatusRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        CourseStatusEntity courseStatusEntity = optional.get();
        
        CourseStatusDto returnVal = new CourseStatusDto();
        BeanUtils.copyProperties(courseStatusEntity, returnVal);
        
        return returnVal;
    }
    
    @Override
    public List<CourseStatusDto> getAll() {
        List<CourseStatusDto> returnValue = new ArrayList<>();
        
        List<CourseStatusEntity> entities = courseStatusRepository.findAll();
        
        for (CourseStatusEntity courseStatusEntity: entities) {
            CourseStatusDto dto = new CourseStatusDto();
            BeanUtils.copyProperties(courseStatusEntity, dto);
            returnValue.add(dto);
        }
        
        return returnValue;
    }
    
}
