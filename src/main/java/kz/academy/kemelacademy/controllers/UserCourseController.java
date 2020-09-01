package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.ICourseService;
import kz.academy.kemelacademy.services.IUserCourseService;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.UserCourseDto;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.RequestOperationName;
import kz.academy.kemelacademy.ui.enums.RequestOperationStatus;
import kz.academy.kemelacademy.ui.model.request.LessonRequestModel;
import kz.academy.kemelacademy.ui.model.request.UserCourseRequestModel;
import kz.academy.kemelacademy.ui.model.response.OperationStatusModel;
import kz.academy.kemelacademy.ui.model.response.UserCourseRest;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("user_courses")
public class UserCourseController {

    @Autowired
    IUserCourseService userCourseService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private MessageSource messageSource;


    private UserCourseRest convertDtoToRest(UserCourseDto userCourseDto){
        UserCourseRest userCourseRest = new UserCourseRest();
        userCourseRest.setId(userCourseDto.getId());
        userCourseRest.setFinished(userCourseDto.getFinished());
        for (UserDto userDto : userCourseDto.getUser()){
            userCourseRest.getUsers().add(userDto.toString());
        }
        for (CourseDto courseDto: userCourseDto.getCourses()){
            userCourseRest.getCourses().add(courseDto.toString());
        }
        return userCourseRest;
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
    @PostMapping
    public UserCourseRest createUserCourse(@RequestBody UserCourseRequestModel userCourseRequestModel) {
        UserCourseRest returnValue = new UserCourseRest();

        UserCourseDto userCourseDto = new UserCourseDto();
        BeanUtils.copyProperties(userCourseRequestModel, userCourseDto);

        UserCourseDto createdUserCourse;
        try {
            createdUserCourse = userCourseService.createUsersCourse(userCourseDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        BeanUtils.copyProperties(createdUserCourse, returnValue);

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
    @PutMapping(path = "/{id}")
    public UserCourseRest updateUserCourse(@PathVariable("id") long id,
                                       @RequestBody UserCourseRequestModel userCourseRequestModel) {
        UserCourseRest returnValue;

        UserCourseDto userCourseDto = new UserCourseDto();
        BeanUtils.copyProperties(userCourseRequestModel, userCourseDto);

        UserCourseDto updatedUserCourse;
        try {
            updatedUserCourse = userCourseService.updateUserCourse(id, userCourseDto);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }

        returnValue = convertDtoToRest(updatedUserCourse);

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
    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteCategory(@PathVariable("id") long id) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());

        try {
            userCourseService.deleteUsersCourse(id);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return operationStatusModel;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @Transactional
    @GetMapping(path = "/{id}")
    public UserCourseRest getUserCourse(@PathVariable("id") long id) {
        UserCourseDto userCourseDto;
        try {
            userCourseDto = userCourseService.getUserCourseById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }

        return convertDtoToRest(userCourseDto);
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
    public List<UserCourseRest> getUserCourses(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserCourseRest> returnVal = new ArrayList<>();

        List<UserCourseDto> userCourses;
        try {
            userCourses = userCourseService.getUserCourses(page, limit);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (UserCourseDto userCourseDto: userCourses) {
            UserCourseRest userCourseRest = convertDtoToRest(userCourseDto);
            returnVal.add(userCourseRest);
        }

        return returnVal;
    }






}
