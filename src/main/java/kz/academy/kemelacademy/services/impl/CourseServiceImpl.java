package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.ICourseRepository;
import kz.academy.kemelacademy.services.ICourseService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.entity.CourseEntity;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        convertDtoToEntity(courseDto, courseEntity);
        
        CourseEntity storedCourse = courseRepository.save(courseEntity);
        
        return convertEntityToDto(storedCourse);
    }
    
    private void convertDtoToEntity(CourseDto courseDto, CourseEntity courseEntity) {
        BeanUtils.copyProperties(courseDto.getAuthor(), courseEntity.getAuthor());
        BeanUtils.copyProperties(courseDto.getCategory(), courseEntity.getCategory());
        BeanUtils.copyProperties(courseDto.getLevel(), courseEntity.getLevel());
        BeanUtils.copyProperties(courseDto.getLanguage(), courseEntity.getLanguage());
        BeanUtils.copyProperties(courseDto, courseEntity);
    }
    
    private CourseDto convertEntityToDto(CourseEntity storedCourse) {
        CourseDto returnVal = new CourseDto();
        BeanUtils.copyProperties(storedCourse.getAuthor(), returnVal.getAuthor());
        BeanUtils.copyProperties(storedCourse.getCategory(), returnVal.getCategory());
        BeanUtils.copyProperties(storedCourse.getLevel(), returnVal.getLevel());
        BeanUtils.copyProperties(storedCourse.getLanguage(), returnVal.getLanguage());
        BeanUtils.copyProperties(storedCourse, returnVal);
        return returnVal;
    }
    
    @Override
    public List<CourseDto> getAll(int page, int limit) throws Exception {
        List<CourseDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<CourseEntity> coursePage = courseRepository.findAll(pageable);
        List<CourseEntity> courses = new ArrayList<>();
        for (CourseEntity courseEntity: coursePage.getContent()) {
            if (!courseEntity.getDeleted()) {
                courses.add(courseEntity);
            }
        }
        
        for (CourseEntity courseEntity: courses) {
            CourseDto courseDto = convertEntityToDto(courseEntity);
            returnValue.add(courseDto);
        }
        
        return returnValue;
    }
    
    @Override
    public CourseDto getCourseById(long id) throws Exception {
        CourseDto returnValue;
        
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        CourseEntity courseEntity = optional.get();
        
        returnValue = convertEntityToDto(courseEntity);
        
        return returnValue;
    }
    
    @Override
    public CourseDto updateCourse(long id, CourseDto courseDto) throws Exception {
        CourseDto returnValue;
        
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        CourseEntity courseEntity = optional.get();
        convertDtoToEntity(courseDto, courseEntity);
        
        CourseEntity updatedCourse = courseRepository.save(courseEntity);
        returnValue = convertEntityToDto(updatedCourse);
        
        return returnValue;
    }
    
    @Override
    public void deleteCourse(long id) throws Exception {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        CourseEntity courseEntity = optional.get();
        courseEntity.setDeleted(true);
        
        courseRepository.save(courseEntity);
    }
    
}
