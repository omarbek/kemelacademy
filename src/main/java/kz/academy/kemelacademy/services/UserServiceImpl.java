package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.exceptions.UserServiceException;
import kz.academy.kemelacademy.repositories.IUserRepository;
import kz.academy.kemelacademy.shared.Utils;
import kz.academy.kemelacademy.shared.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.UserEntity;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Service
public class UserServiceImpl implements IUserService {
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private Utils utils;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userByEmail = userRepository.findByEmail(userDto.getEmail());
        if (userByEmail != null) {
            throw new RuntimeException("Record already exists");
        }
        
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setUserId(utils.generateUserId(30));
        
        UserEntity storedUserDetails = userRepository.save(userEntity);
        
        UserDto returnVal = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnVal);
        
        return returnVal;
    }
    
    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        
        UserDto returnVal = new UserDto();
        BeanUtils.copyProperties(userEntity, returnVal);
        
        return returnVal;
    }
    
    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("user with id " + userId + " not found");
        }
        
        BeanUtils.copyProperties(userEntity, returnValue);
        
        return returnValue;
    }
    
    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UserDto returnValue = new UserDto();
        
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        
        UserEntity updatedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, returnValue);
        
        return returnValue;
    }
    
    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        
        userRepository.delete(userEntity);
    }
    
    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        List<UserEntity> users = usersPage.getContent();
        
        for (UserEntity userEntity: users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }
        
        return returnValue;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
    
}
