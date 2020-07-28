package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
public interface IUserService extends UserDetailsService {
    
    UserDto createUser(UserDto userDto);
    
    UserDto getUser(String email);
    
    UserDto getUserByUserId(String userId);
    
    UserDto updateUser(String userId, UserDto userDto);
    
    void deleteUser(String userId);
    
    List<UserDto> getUsers(int page, int limit);
    
    boolean sendEmail(String email, String emailVerificationToken);
    
    boolean verifyEmailToken(String token);
    
    boolean requestPasswordReset(String email);
    
    boolean resetPassword(String token, String password);
    
}
