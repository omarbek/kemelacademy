package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.*;
import kz.academy.kemelacademy.services.ICourseService;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.*;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Service
public class CourseServiceImpl implements ICourseService {
    
    @Autowired
    private ICourseRepository courseRepository;
    
    @Autowired
    private ICourseStatusRepository courseStatusRepository;
    
    @Autowired
    private ICategoryRepository categoryRepository;
    
    @Autowired
    private ILevelRepository levelRepository;
    
    @Autowired
    private ILanguageRepository languageRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private IUserService userService;
    
    @Override
    public CourseDto createCourse(CourseDto courseDto) throws Exception {
        CourseEntity courseEntity = new CourseEntity();
        convertDtoToEntity(courseDto, courseEntity, false);
        
        CourseEntity storedCourse = courseRepository.save(courseEntity);
        
        return convertEntityToDto(storedCourse);
    }
    
    private void convertDtoToEntity(CourseDto courseDto, CourseEntity courseEntity, boolean update) {
        if (!update) {
            BeanUtils.copyProperties(courseDto.getAuthor(), courseEntity.getAuthor());
        }
        if (courseDto.getCategory().getId() != null) {
            if (!courseDto.getCategory().getId().equals(courseEntity.getCategory().getId())) {
                CategoryEntity categoryEntity = categoryRepository
                        .findById(courseDto.getCategory().getId()).get();
                courseEntity.setCategory(categoryEntity);
            }
        }
        if (courseDto.getLevel().getId() != null) {
            if (!courseDto.getLevel().getId().equals(courseEntity.getLevel().getId())) {
                LevelEntity levelEntity = levelRepository
                        .findById(courseDto.getLevel().getId()).get();
                courseEntity.setLevel(levelEntity);
            }
        }
        if (courseDto.getLanguage().getId() != null) {
            if (!courseDto.getLanguage().getId().equals(courseEntity.getLanguage().getId())) {
                LanguageEntity languageEntity = languageRepository
                        .findById(courseDto.getLanguage().getId()).get();
                courseEntity.setLanguage(languageEntity);
            }
        }
        if (courseDto.getCourseStatus().getId() != null) {
            if (!courseDto.getCourseStatus().getId().equals(courseEntity.getCourseStatus().getId())) {
                CourseStatusEntity courseStatusEntity = courseStatusRepository
                        .findById(courseDto.getCourseStatus().getId()).get();
                courseEntity.setCourseStatus(courseStatusEntity);
            }
        }
        for (UserDto userDto: courseDto.getPupils()) {
            Set<UserEntity> pupils = new HashSet<>();
            UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());
            pupils.add(userEntity);
            courseEntity.setPupils(pupils);
        }
        if (update) {
            if (courseDto.getPrice() != null) {
                courseEntity.setPrice(courseDto.getPrice());
            }
            if (courseDto.getName() != null) {
                courseEntity.setName(courseDto.getName());
            }
            if (courseDto.getDescription() != null) {
                courseEntity.setDescription(courseDto.getDescription());
            }
            if (courseDto.getRequirements() != null) {
                courseEntity.setRequirements(courseDto.getRequirements());
            }
            if (courseDto.getLearns() != null) {
                courseEntity.setLearns(courseDto.getLearns());
            }
        } else {
            BeanUtils.copyProperties(courseDto, courseEntity);
        }
    }
    
    private CourseDto convertEntityToDto(CourseEntity storedCourse) {
        CourseDto returnVal = new CourseDto();
        BeanUtils.copyProperties(storedCourse.getAuthor(), returnVal.getAuthor());
        BeanUtils.copyProperties(storedCourse.getCategory(), returnVal.getCategory());
        BeanUtils.copyProperties(storedCourse.getLevel(), returnVal.getLevel());
        BeanUtils.copyProperties(storedCourse.getLanguage(), returnVal.getLanguage());
        BeanUtils.copyProperties(storedCourse.getCourseStatus(), returnVal.getCourseStatus());
        for (UserEntity userEntity: storedCourse.getPupils()) {
            UserDto userDto = userService.getUserByUserId(userEntity.getUserId());
            returnVal.getPupils().add(userDto);
        }
        BeanUtils.copyProperties(storedCourse, returnVal);
        return returnVal;
    }
    
    @Override
    public List<CourseDto> getAll(int page, int limit, Long categoryId) throws Exception {
        List<CourseDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<CourseEntity> coursePage = courseRepository.findAll(pageable);
        List<CourseEntity> courses = new ArrayList<>();
        for (CourseEntity courseEntity: coursePage.getContent()) {
            if (!courseEntity.getDeleted()) {
                if (categoryId != null) {
                    if (categoryId.equals(courseEntity.getCategory().getId())) {
                        courses.add(courseEntity);
                    }
                } else {
                    courses.add(courseEntity);
                }
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
        convertDtoToEntity(courseDto, courseEntity, true);
        
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
        courseEntity.setPupils(new HashSet<>());
        
        courseRepository.save(courseEntity);
    }
    
}
