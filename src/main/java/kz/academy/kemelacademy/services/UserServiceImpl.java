package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.exceptions.UserServiceException;
import kz.academy.kemelacademy.repositories.IUserRepository;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.UserEntity;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
    private GeneratorUtils generatorUtils;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userByEmail = userRepository.findByEmail(userDto.getEmail());
        if (userByEmail != null) {
            throw new RuntimeException("Record already exists");
        }
        
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        String userId = generatorUtils.generateUserId(30);
        userEntity.setUserId(userId);
        userEntity.setEmailVerificationToken(generatorUtils.generateEmailVerificationToken(userId));
        userEntity.setEmailVerificationStatus(false);
        
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
    public void sendEmail(String email, String emailVerificationToken) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        
        msg.setSubject("Email verification");
        msg.setText("Token: " + emailVerificationToken);
        
        javaMailSender.send(msg);
    }
    
    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;
        
        UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);
        
        if (userEntity != null) {
            boolean hasTokenExpired = GeneratorUtils.hasTokenExpired(token);
            if (!hasTokenExpired) {
                userEntity.setEmailVerificationToken(null);
                userEntity.setEmailVerificationStatus(true);
                userRepository.save(userEntity);
                returnValue = true;
            }
        }
        
        return returnValue;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                userEntity.getEmailVerificationStatus(), true, true, true, new ArrayList<>());
    }
    
}
