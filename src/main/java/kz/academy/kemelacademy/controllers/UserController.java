package kz.academy.kemelacademy.controllers;

import kz.academy.kemelacademy.exceptions.UserServiceException;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.shared.dto.UserDto;
import kz.academy.kemelacademy.ui.enums.RequestOperationName;
import kz.academy.kemelacademy.ui.model.request.UserDetailsRequestModel;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.model.response.OperationStatusModel;
import kz.academy.kemelacademy.ui.enums.RequestOperationStatus;
import kz.academy.kemelacademy.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@RestController
@RequestMapping("users")
public class UserController {
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) throws Exception {
        UserRest returnValue = new UserRest();
        
        if (userDetailsRequestModel.getFirstName().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);
        
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);
        
        return returnValue;
    }
    
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable("id") String userId) {
        UserRest returnValue = new UserRest();
        
        UserDto userDto = userService.getUserByUserId(userId);
        BeanUtils.copyProperties(userDto, returnValue);
        
        return returnValue;
    }
    
    @PutMapping(path = "/{id}")
    public UserRest updateUser(@PathVariable("id") String userId,
                               @RequestBody UserDetailsRequestModel userDetailsRequestModel) {
        UserRest returnValue = new UserRest();
        
        if (userDetailsRequestModel.getFirstName().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);
        
        UserDto updatedUser = userService.updateUser(userId, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);
        
        return returnValue;
    }
    
    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable("id") String userId) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
        
        userService.deleteUser(userId);
        
        operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return operationStatusModel;
    }
    
    @GetMapping
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnVal = new ArrayList<>();
        
        List<UserDto> users = userService.getUsers(page, limit);
        for (UserDto userDto: users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnVal.add(userModel);
        }
        
        return returnVal;
    }
    
    @PostMapping(path = "ss")
    public String ss(){
        return "ss";
    }
    
}
