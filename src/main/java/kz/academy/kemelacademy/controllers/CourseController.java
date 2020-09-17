package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.*;
import kz.academy.kemelacademy.ui.dto.*;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.RequestOperationName;
import kz.academy.kemelacademy.ui.enums.RequestOperationStatus;
import kz.academy.kemelacademy.ui.model.request.CourseRequestModel;
import kz.academy.kemelacademy.ui.model.response.CourseRest;
import kz.academy.kemelacademy.ui.model.response.OperationStatusModel;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    
    @Autowired
    private ICourseStatusService courseStatusService;
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header"),
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @PostMapping
    @Transactional
    public CourseRest createCourse(@RequestBody CourseRequestModel courseRequestModel) {
        CourseRest returnValue;
        
        Object[] fields = {
                courseRequestModel.getCategoryId(),
                courseRequestModel.getLevelId(),
                courseRequestModel.getLanguageId(),
                courseRequestModel.getPrice(),
                courseRequestModel.getName(),
                courseRequestModel.getDescription(),
                courseRequestModel.getRequirements(),
                courseRequestModel.getLearns(),
                courseRequestModel.getCourseStatusId()
        };
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        CourseDto courseDto = convertModelToDto(courseRequestModel, false);
        
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
        courseRest.setCourseStatus(createdCourse.getCourseStatus().toString());
        for (UserDto userDto: createdCourse.getPupils()) {
            courseRest.getPupils().add(userDto.toString());
        }
        Integer duration = 0;
        Integer lessonCount = 0;
        for (ChapterDto chapterDto: createdCourse.getChapters()) {
            for (LessonDto lessonDto: chapterDto.getLessons()) {
                duration += lessonDto.getDuration();
                lessonCount++;
            }
        }
        courseRest.setDuration(duration);
        courseRest.setChapterCount(createdCourse.getChapters().size());
        courseRest.setLessonCount(lessonCount);
        BeanUtils.copyProperties(createdCourse, courseRest);
        
        return courseRest;
    }
    
    private CourseDto convertModelToDto(CourseRequestModel courseRequestModel, boolean update) {
        CourseDto courseDto = new CourseDto();
        
        if (!update) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDto userDto = userService.getUser(email);
            courseDto.setAuthor(userDto);
        }
        if (courseRequestModel.getCategoryId() != null) {
            CategoryDto categoryDto;
            try {
                categoryDto = categoryService.getCategoryById(courseRequestModel.getCategoryId());
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            courseDto.setCategory(categoryDto);
        }
        if (courseRequestModel.getLevelId() != null) {
            LevelDto levelDto;
            try {
                levelDto = levelService.getLevelById(courseRequestModel.getLevelId());
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            courseDto.setLevel(levelDto);
        }
        if (courseRequestModel.getLanguageId() != null) {
            LanguageDto languageDto;
            try {
                languageDto = languageService.getLanguageById(courseRequestModel.getLanguageId());
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            courseDto.setLanguage(languageDto);
        }
        if (courseRequestModel.getCourseStatusId() != null) {
            CourseStatusDto courseStatusDto;
            try {
                courseStatusDto = courseStatusService.getEntityById(courseRequestModel.getCourseStatusId());
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            courseDto.setCourseStatus(courseStatusDto);
        }
        for (Long pupilId: courseRequestModel.getPupils()) {
            UserDto userDto;
            try {
                userDto = userService.getUserById(pupilId);
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            courseDto.getPupils().add(userDto);
        }
        
        BeanUtils.copyProperties(courseRequestModel, courseDto);
        
        return courseDto;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @GetMapping
    @Transactional
    public List<CourseRest> getCourses(@RequestParam(value = "page", defaultValue = "0") int page,
                                       @RequestParam(value = "limit", defaultValue = "25") int limit,
                                       @RequestParam(value = "categoryId", required = false) Long categoryId) {
        List<CourseRest> returnVal = new ArrayList<>();
        
        List<CourseDto> courses;
        try {
            courses = courseService.getAll(page, limit, categoryId);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (CourseDto courseDto: courses) {
            CourseRest courseRest = convertDtoToModel(courseDto);
            returnVal.add(courseRest);
        }
        
        return returnVal;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @Transactional
    @GetMapping(path = "/{courseId}")
    public CourseRest getCourse(@PathVariable("courseId") long courseId) {
        CourseDto courseDto;
        try {
            courseDto = courseService.getCourseById(courseId);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return convertDtoToModel(courseDto);
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header"),
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @Transactional
    @PutMapping(path = "/{courseId}")
    public CourseRest updateCourse(@PathVariable("courseId") long courseId,
                                   @RequestBody CourseRequestModel courseRequestModel) {
        CourseRest returnValue;
        
        CourseDto courseDto = convertModelToDto(courseRequestModel, true);
        
        CourseDto updatedCourse;
        try {
            updatedCourse = courseService.updateCourse(courseId, courseDto);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        returnValue = convertDtoToModel(updatedCourse);
        
        return returnValue;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header"),
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @Transactional
    @DeleteMapping(path = "/{courseId}")
    public OperationStatusModel deleteCourse(@PathVariable("courseId") long courseId) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
        
        try {
            courseService.deleteCourse(courseId);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header")
    })
    @Transactional
    @PostMapping(path = "uploadFile/{courseId}")
    public CourseRest uploadFile(@RequestParam("file") MultipartFile file,
                                 @PathVariable("courseId") Long courseId) {
        CourseRest returnValue;
        
        if (file == null || file.isEmpty()) {
            throw new ServiceException(ErrorMessages.PLEASE_SELECT_FILE.getErrorMessage());
        }
        
        CourseDto uploadedFileDto;
        try {
            uploadedFileDto = courseService.uploadFile(courseId, file);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        returnValue = convertDtoToModel(uploadedFileDto);
        
        return returnValue;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header")
    })
    @Transactional
    @PostMapping(path = "finishCourse/{userId}/{courseId}")
    public OperationStatusModel finishCourse(@PathVariable("userId") Long userId,
                                             @PathVariable("courseId") Long courseId) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.FINISH_COURSE.name());
        
        try {
            courseService.finishCourse(userId, courseId);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header")
    })
    @Transactional
    @PostMapping(path = "setRating/{courseId}/{rating}")
    public OperationStatusModel setRating(@PathVariable("courseId") Long courseId,
                                          @PathVariable("rating") Double rating) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.SET_RATING.name());
        
        try {
            courseService.setRating(courseId, rating);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header"),
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @Transactional
    @GetMapping(path = "myCourses")
    public List<CourseRest> getMyCourses() {
        List<CourseRest> returnVal = new ArrayList<>();
        
        List<CourseDto> courses;
        try {
            courses = courseService.getMyCourses();
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (CourseDto courseDto: courses) {
            CourseRest courseRest = convertDtoToModel(courseDto);
            returnVal.add(courseRest);
        }
        
        return returnVal;
    }
    
}
