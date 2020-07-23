package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.shared.dto.UserDto;
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
    
}
