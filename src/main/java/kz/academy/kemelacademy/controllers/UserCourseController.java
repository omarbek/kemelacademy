package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.ICourseService;
import kz.academy.kemelacademy.services.IUserCourseService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.UserCourseDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.model.request.UserCourseRequestModel;
import kz.academy.kemelacademy.ui.model.response.UserCourseRest;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user_courses")
public class UserCourseController {
    
    @Autowired
    private IUserCourseService userCourseService;
    
    @Autowired
    private ICourseService courseService;
    
    
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
    public UserCourseRest createUserCourse(@RequestBody UserCourseRequestModel userCourseRequestModel) {
        UserCourseRest returnValue = new UserCourseRest();
        
        Object[] fields = {
                userCourseRequestModel.getCourseId(),
                userCourseRequestModel.isFinished()
        };
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        UserCourseDto userCourseDto = new UserCourseDto();
        CourseDto courseDto;
        try {
            courseDto = courseService.getCourseById(userCourseRequestModel.getCourseId());
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        userCourseDto.setCourse(courseDto);
        BeanUtils.copyProperties(userCourseRequestModel, userCourseDto);
        
        UserCourseDto createdUserCourse;
        try {
            createdUserCourse = userCourseService.createUsersCourse(userCourseDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        BeanUtils.copyProperties(createdUserCourse.getCourse(), returnValue.getCourseRest());
        BeanUtils.copyProperties(createdUserCourse, returnValue);
        
        return returnValue;
    }
    
}
