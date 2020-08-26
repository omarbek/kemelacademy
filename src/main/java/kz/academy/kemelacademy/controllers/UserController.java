package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.RoleDto;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.RequestOperationName;
import kz.academy.kemelacademy.ui.enums.RequestOperationStatus;
import kz.academy.kemelacademy.ui.model.request.PasswordResetModel;
import kz.academy.kemelacademy.ui.model.request.PasswordResetRequestModel;
import kz.academy.kemelacademy.ui.model.request.UserDetailsRequestModel;
import kz.academy.kemelacademy.ui.model.response.OperationStatusModel;
import kz.academy.kemelacademy.ui.model.response.UserRest;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Slf4j
@RestController
@RequestMapping("users")
public class UserController {
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private MessageSource messageSource;
    
    @PostMapping
    @Transactional
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        UserRest returnValue = new UserRest();
        
        String[] fields = {userDetailsRequestModel.getFirstName(), userDetailsRequestModel.getLastName(),
                userDetailsRequestModel.getEmail(), userDetailsRequestModel.getPassword()};
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);
        
        UserDto createdUser;
        try {
            createdUser = userService.createUser(userDto);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e); //todo
        }
        
        convertDtoToRest(createdUser, returnValue);
        
        boolean sendEmail = userService.sendEmail(createdUser.getEmail(), createdUser.getEmailVerificationToken());
        if (!sendEmail) {
            throw new ServiceException(ErrorMessages.DID_NOT_SEND_EMAIL.getErrorMessage());
        }
        
        return returnValue;
    }
    
    private void convertDtoToRest(UserDto createdUser, UserRest returnValue) {
        BeanUtils.copyProperties(createdUser, returnValue);
        for (RoleDto roleDto: createdUser.getRoles()) {
            returnValue.getRoles().add(roleDto.toString());
        }
        for (CourseDto courseDto: createdUser.getCourses()) {
            returnValue.getCourses().add(courseDto.toString());
        }
    }
    
    @ApiOperation(value = "The Get User Details Web Service Endpoint",
            notes = "${userController.getUser.apiOperation.notes}")
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable("id") String userId) {
        UserRest returnValue = new UserRest();
        
        UserDto userDto = userService.getUserByUserId(userId);
        convertDtoToRest(userDto, returnValue);
        
        return returnValue;
    }
    
    @PutMapping(path = "/{id}")
    public UserRest updateUser(@PathVariable("id") String userId,
                               @RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        UserRest returnValue = new UserRest();
        
        String[] fields = {userDetailsRequestModel.getFirstName(), userDetailsRequestModel.getLastName(),
                userDetailsRequestModel.getPassword()};
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);
        
        UserDto updatedUser;
        try {
            updatedUser = userService.updateUser(userId, userDto);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        convertDtoToRest(updatedUser, returnValue);
        
        return returnValue;
    }
    
    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable("id") String userId) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
        
        try {
            userService.deleteUser(userId);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping
    @Transactional
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnVal = new ArrayList<>();
        
        List<UserDto> users;
        try {
            users = userService.getUsers(page, limit);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.name(), e);
        }
        for (UserDto userDto: users) {
            UserRest userModel = new UserRest();
            convertDtoToRest(userDto, userModel);
            returnVal.add(userModel);
        }
        
        return returnVal;
    }
    
    @GetMapping(path = "email-verification")
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
        
        boolean isVerified = false;
        try {
            isVerified = userService.verifyEmailToken(token);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        returnValue.setOperationResult(isVerified ? RequestOperationStatus.SUCCESS.name() :
                RequestOperationStatus.ERROR.name());
        
        return returnValue;
    }
    
    @GetMapping(path = "/hello-inter")
    //    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")//todo
    public String helloWorldInter() {
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
    
    @PostMapping(path = "/password-reset-request")
    public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
        OperationStatusModel returnValue = new OperationStatusModel();
        
        boolean operationResult = false;
        try {
            operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        
        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        
        if (operationResult) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }
        
        return returnValue;
    }
    
    @PostMapping(path = "/password-reset")
    public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
        OperationStatusModel returnVal = new OperationStatusModel();
        
        boolean operationResult = false;
        try {
            operationResult = userService.resetPassword(passwordResetModel.getToken(),
                    passwordResetModel.getPassword());
        } catch (Exception e) {//todo
            log.error(e.getLocalizedMessage(), e);
        }
        
        returnVal.setOperationName(RequestOperationName.PASSWORD_RESET.name());
        returnVal.setOperationResult(RequestOperationStatus.ERROR.name());
        
        if (operationResult) {
            returnVal.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }
        
        return returnVal;
    }
    
}
