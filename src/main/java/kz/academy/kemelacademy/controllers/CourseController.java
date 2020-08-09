package kz.academy.kemelacademy.controllers;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.*;
import kz.academy.kemelacademy.ui.dto.*;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.model.request.CourseRequestModel;
import kz.academy.kemelacademy.ui.model.response.CourseRest;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Slf4j
@RestController
@RequestMapping("courses")
public class CourseController {
    
    @Autowired
    private ICourseService courseService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ICategoryService categoryService;
    
    @Autowired
    private ILevelService levelService;
    
    @Autowired
    private ILanguageService languageService;
    
    @PostMapping
    @Transactional
    public CourseRest createCategory(@RequestBody CourseRequestModel courseRequestModel) {
        CourseRest returnValue;
        
        Object[] fields = {
                courseRequestModel.getCategoryId(),
                courseRequestModel.getLevelId(),
                courseRequestModel.getLanguageId(),
                courseRequestModel.getPrice(),
                courseRequestModel.getNameKz(),
                courseRequestModel.getNameRu(),
                courseRequestModel.getNameEn(),
                courseRequestModel.getDescriptionKz(),
                courseRequestModel.getDescriptionRu(),
                courseRequestModel.getDescriptionEn(),
                courseRequestModel.getAboutCourseKz(),
                courseRequestModel.getAboutCourseRu(),
                courseRequestModel.getAboutCourseEn()
        };
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        CourseDto courseDto = convertModelToDto(courseRequestModel);
        
        CourseDto createdCourse;
        try {
            createdCourse = courseService.createCourse(courseDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        returnValue = convertDtoToModel(createdCourse);
        
        return returnValue;
    }
    
    private CourseRest convertDtoToModel(CourseDto createdCourse) {
        CourseRest courseRest = new CourseRest();
        
        courseRest.setAuthor(createdCourse.getAuthor().toString());
        courseRest.setCategory(createdCourse.getCategory().toString());
        courseRest.setLevel(createdCourse.getLevel().toString());
        courseRest.setLanguage(createdCourse.getLanguage().toString());
        //        courseRest.setRating();//todo
        //        courseRest.setDuration();//todo
        
        BeanUtils.copyProperties(createdCourse, courseRest);
        
        return courseRest;
    }
    
    private CourseDto convertModelToDto(CourseRequestModel courseRequestModel) {
        CourseDto courseDto = new CourseDto();
        
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDto = userService.getUser(email);
        courseDto.setAuthor(userDto);
        
        CategoryDto categoryDto;
        try {
            categoryDto = categoryService.getCategoryById(courseRequestModel.getCategoryId());
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        courseDto.setCategory(categoryDto);
        
        LevelDto levelDto;
        try {
            levelDto = levelService.getLevelById(courseRequestModel.getLevelId());
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        courseDto.setLevel(levelDto);
        
        LanguageDto languageDto;
        try {
            languageDto = languageService.getLanguageById(courseRequestModel.getLanguageId());
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        courseDto.setLanguage(languageDto);
        
        BeanUtils.copyProperties(courseRequestModel, courseDto);
        
        return courseDto;
    }
    
    @GetMapping
    @Transactional
    public List<CourseRest> getCourses(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<CourseRest> returnVal = new ArrayList<>();
        
        List<CourseDto> courses;
        try {
            courses = courseService.getAll(page, limit);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (CourseDto courseDto: courses) {
            CourseRest courseRest = convertDtoToModel(courseDto);
            returnVal.add(courseRest);
        }
        
        return returnVal;
    }
    
    @Transactional
    @GetMapping(path = "/{id}")
    public CourseRest getCourse(@PathVariable("id") long id) {
        CourseDto courseDto;
        try {
            courseDto = courseService.getCourseById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return convertDtoToModel(courseDto);
    }
    
}
