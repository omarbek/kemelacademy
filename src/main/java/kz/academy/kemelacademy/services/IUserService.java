package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
public interface IUserService extends UserDetailsService {
    
    UserDto createUser(UserDto userDto) throws Exception;
    
    UserDto getUser(String email);
    
    UserDto getUserByUserId(String userId);
    
    UserDto updateUser(UserEntity userEntity, UserDto userDto) throws Exception;
    
    void deleteUser(String userId) throws Exception;
    
    List<UserDto> getUsers(int page, int limit, String name) throws Exception;
    
    boolean sendEmail(String email, String emailVerificationToken);
    
    boolean verifyEmailToken(String token) throws Exception;
    
    boolean requestPasswordReset(String email) throws Exception;
    
    boolean resetPassword(String token, String password) throws Exception;
    
    UserDto getUserById(Long pupilId) throws Exception;
    
}
