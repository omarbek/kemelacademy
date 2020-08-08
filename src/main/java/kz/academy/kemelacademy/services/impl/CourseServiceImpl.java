package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.repositories.ICourseRepository;
import kz.academy.kemelacademy.services.ICourseService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.entity.CourseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Service
public class CourseServiceImpl implements ICourseService {
    
    @Autowired
    private ICourseRepository courseRepository;
    
    @Override
    public CourseDto createCourse(CourseDto courseDto) throws Exception {
        CourseEntity courseEntity = new CourseEntity();
        BeanUtils.copyProperties(courseDto.getAuthor(), courseEntity.getAuthor());
        BeanUtils.copyProperties(courseDto.getCategory(), courseEntity.getCategory());
        BeanUtils.copyProperties(courseDto.getLevel(), courseEntity.getLevel());
        BeanUtils.copyProperties(courseDto.getLanguage(), courseEntity.getLanguage());
        BeanUtils.copyProperties(courseDto, courseEntity);
        
        CourseEntity storedCategory = courseRepository.save(courseEntity);
        
        CourseDto returnVal = new CourseDto();
        BeanUtils.copyProperties(storedCategory.getAuthor(), returnVal.getAuthor());
        BeanUtils.copyProperties(storedCategory.getCategory(), returnVal.getCategory());
        BeanUtils.copyProperties(storedCategory.getLevel(), returnVal.getLevel());
        BeanUtils.copyProperties(storedCategory.getLanguage(), returnVal.getLanguage());
        BeanUtils.copyProperties(storedCategory, returnVal);
        
        return returnVal;
    }
    
}
