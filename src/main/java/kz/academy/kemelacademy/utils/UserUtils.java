package kz.academy.kemelacademy.utils;

import kz.academy.kemelacademy.repositories.IUserRepository;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-23
 * @project kemelacademy
 */
@Component
public class UserUtils {
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IUserRepository userRepository;
    
    public String getCurrentUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public UserDto getCurrentUserDto() {
        String email = getCurrentUserEmail();
        return userService.getUser(email);
    }
    
    public UserEntity getCurrentUserEntity() {
        String email = getCurrentUserEmail();
        return userRepository.findByEmail(email);
    }
    
    public boolean userIdBelongsToCurUser(String userId) {
        return userId.equals(getCurrentUserEntity().getUserId());
    }
    
}
