package kz.academy.kemelacademy.utils;

import kz.academy.kemelacademy.repositories.IUserRepository;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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
    
    @Autowired
    private HttpServletRequest request;
    
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
    
    public boolean isItAdmin() {
        // another method for checking role
        //        UserDetails details = userService.loadUserByUsername(userUtils.getCurrentUserEmail());
        //        if (details == null || details.getAuthorities().stream()
        //                .noneMatch(a -> a.getAuthority().equals("Moderator"))) {
        //            throw new ServiceException(ErrorMessages.YOUR_ROLE_HAS_NO_GRANTS_TO_EXECUTE_THIS_OPERATION
        //                    .getErrorMessage());
        //        }
        return request.isUserInRole("ROLE_MODERATOR");
    }
    
}
