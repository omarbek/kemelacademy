package kz.academy.kemelacademy.services.impl;

import com.google.common.collect.Sets;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.ICourseRepository;
import kz.academy.kemelacademy.repositories.IPasswordResetTokenRepository;
import kz.academy.kemelacademy.repositories.IRoleRepository;
import kz.academy.kemelacademy.repositories.IUserRepository;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.RoleDto;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.*;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.utils.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Slf4j
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
    
    @Autowired
    private IRoleRepository roleRepository;
    
    @Autowired
    private IPasswordResetTokenRepository passwordResetTokenRepository;
    
    @Autowired
    private ICourseRepository courseRepository;
    
    @Override
    public UserDto createUser(UserDto userDto) throws Exception {
        UserEntity userByEmail = userRepository.findByEmail(userDto.getEmail());
        if (userByEmail != null) {
            throw new ServiceException(ErrorMessages.EMAIL_ALREADY_EXISTS.getErrorMessage());
        }
        
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        String userId = generatorUtils.generateUserId(30);
        userEntity.setUserId(userId);
        userEntity.setEmailVerificationToken(generatorUtils.generateEmailVerificationToken(userId));
        userEntity.setEmailVerificationStatus(false);
        
        RoleEntity instructor = roleRepository.findById(RoleEntity.INSTRUCTOR).get();
        RoleEntity student = roleRepository.findById(RoleEntity.STUDENT).get();
        Set<RoleEntity> roles = Sets.newHashSet(instructor, student);
        userEntity.setRoles(roles);
        
        UserEntity storedUserDetails = userRepository.save(userEntity);
        
        UserDto returnVal = new UserDto();
        convertDomainToDto(storedUserDetails, returnVal);
        
        return returnVal;
    }
    
    private void convertDomainToDto(UserEntity storedUserDetails, UserDto returnVal) {
        BeanUtils.copyProperties(storedUserDetails, returnVal);
        for (RoleEntity roleEntity: storedUserDetails.getRoles()) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(roleEntity, roleDto);
            returnVal.getRoles().add(roleDto);
        }
        for (CourseEntity courseEntity: storedUserDetails.getCourses()) {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(courseEntity, courseDto);
            returnVal.getCoursesAsAuthor().add(courseDto);
        }
        for (UserCourseEntity userCourseEntity: storedUserDetails.getPupils()) {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(userCourseEntity.getCourse(), courseDto);
            returnVal.getCoursesAsPupil().add(courseDto);
        }
    }
    
    @Override
    @Transactional
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        
        UserDto returnVal = new UserDto();
        convertDomainToDto(userEntity, returnVal);
        
        return returnVal;
    }
    
    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("user with id " + userId + " is not found");
        }
        
        convertDomainToDto(userEntity, returnValue);
        
        return returnValue;
    }
    
    @Override
    public UserDto updateUser(UserEntity userEntity, UserDto userDto) throws Exception {
        UserDto returnValue = new UserDto();
        
        if (userDto.getFullName() != null) {
            userEntity.setFullName(userDto.getFullName());
        }
        if (userDto.getPassword() != null) {
            String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
            userEntity.setEncryptedPassword(encodedPassword);
        }
        
        boolean agreedWithAgreement = userDto.isAgreedWithAgreement();
        if (agreedWithAgreement) {
            userEntity.setVideoCallUrl(generatorUtils.generateRandomString(25));
        } else {
            userEntity.setVideoCallUrl(null);
        }
        userEntity.setAgreedWithAgreement(agreedWithAgreement);
        UserEntity updatedUserDetails = userRepository.save(userEntity);
        
        convertDomainToDto(updatedUserDetails, returnValue);
        
        return returnValue;
    }
    
    @Override
    public void deleteUser(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        Iterator<RoleEntity> iterator = userEntity.getRoles().iterator();
        for (userEntity.getRoles().iterator(); iterator.hasNext(); ) {
            RoleEntity roleEntity = iterator.next();
            iterator.remove();
        }
        Iterator<CourseEntity> courses = userEntity.getCourses().iterator();
        for (userEntity.getCourses().iterator(); courses.hasNext(); ) {
            CourseEntity courseEntity = courses.next();
            courseRepository.delete(courseEntity);
        }
        userEntity.setCourses(new HashSet<>());
        
        userRepository.delete(userEntity);
    }
    
    @Override
    public List<UserDto> getUsers(int page, int limit, String name) throws Exception {
        List<UserDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        List<UserEntity> users;
        if (name != null) {
            users = userRepository.findByName(name);
        } else {
            users = usersPage.getContent();
        }
        
        for (UserEntity userEntity: users) {
            UserDto userDto = new UserDto();
            convertDomainToDto(userEntity, userDto);
            returnValue.add(userDto);
        }
        
        return returnValue;
    }
    
    @Override
    public boolean sendEmail(String email, String emailVerificationToken) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        
        msg.setSubject("Email verification");
        msg.setText("Token: " + emailVerificationToken);
        
        try {
            javaMailSender.send(msg);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean verifyEmailToken(String token) throws Exception {
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
    @Transactional
    public boolean requestPasswordReset(String email) throws Exception {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        
        String token = new GeneratorUtils().generatePasswordResetToken(userEntity.getUserId());
        
        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(userEntity);
        passwordResetTokenRepository.save(passwordResetTokenEntity);
        
        boolean sendEmail = sendEmail(email, token);
        if (!sendEmail) {
            throw new ServiceException(ErrorMessages.DID_NOT_SEND_EMAIL.getErrorMessage());
        }
        
        return true;
    }
    
    @Override
    @Transactional
    public boolean resetPassword(String token, String password) throws Exception {
        boolean returnVal = false;
        
        if (GeneratorUtils.hasTokenExpired(token)) {
            return returnVal;
        }
        
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);
        if (passwordResetTokenEntity == null) {
            return returnVal;
        }
        
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        
        UserEntity userEntity = passwordResetTokenEntity.getUserDetails();
        userEntity.setEncryptedPassword(encodedPassword);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        
        if (savedUserEntity.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) {
            returnVal = true;
        }
        
        passwordResetTokenRepository.delete(passwordResetTokenEntity);
        
        return returnVal;
    }
    
    @Override
    public UserDto getUserById(Long pupilId) throws Exception {
        UserDto returnValue = new UserDto();
        
        Optional<UserEntity> optional = userRepository.findById(pupilId);
        if (!optional.isPresent()) {
            throw new UsernameNotFoundException("user with id " + pupilId + " is not found");
        }
        
        UserEntity userEntity = optional.get();
        convertDomainToDto(userEntity, returnValue);
        
        return returnValue;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {//login-2
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                userEntity.getEmailVerificationStatus(), true, true, true, getAuthority(userEntity));
    }
    
    private Set<GrantedAuthority> getAuthority(UserEntity userEntity) {
        Set<GrantedAuthority> set = new HashSet<>();
        for (RoleEntity roleEntity: userEntity.getRoles()) {
            set.add((GrantedAuthority) roleEntity::getNameEn);
        }
        return set;
    }
    
}
