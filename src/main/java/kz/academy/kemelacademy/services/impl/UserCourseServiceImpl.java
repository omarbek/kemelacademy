package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.IUserCourseRepository;
import kz.academy.kemelacademy.services.IUserCourseService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.UserCourseDto;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.CourseEntity;
import kz.academy.kemelacademy.ui.entity.UserCourseEntity;
import kz.academy.kemelacademy.ui.entity.UserEntity;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserCourseServiceImpl implements IUserCourseService {

    @Autowired
    private IUserCourseRepository userCourseRepository;


    @Override
    public UserCourseDto createUsersCourse(UserCourseDto usercourseDto) throws Exception {

        UserCourseEntity userCourseEntity = new UserCourseEntity();
        convertDtoToEntity(usercourseDto, userCourseEntity, false);
        UserCourseEntity storedUserCourse = userCourseRepository.save(userCourseEntity);

        return convertEntityToDto(storedUserCourse);
    }

    private void convertDtoToEntity(UserCourseDto userCourseDto, UserCourseEntity userCourseEntity, boolean update) {
        if (!userCourseDto.getUser().isEmpty()) {
            BeanUtils.copyProperties(userCourseDto.getUser(), userCourseEntity.getUser());
        }
        if (userCourseDto.getFinished() != null) {
            BeanUtils.copyProperties(userCourseDto.getFinished(), userCourseEntity.getFinished());
        }
        if (!userCourseDto.getCourses().isEmpty()) {
            BeanUtils.copyProperties(userCourseDto.getCourses(), userCourseEntity.getCourses());
        }
        else {
            BeanUtils.copyProperties(userCourseDto, userCourseEntity);
        }
    }

    private UserCourseDto convertEntityToDto(UserCourseEntity storedUserCourse) {
        UserCourseDto returnVal = new UserCourseDto();
        BeanUtils.copyProperties(storedUserCourse.getFinished(), returnVal.getFinished());
        BeanUtils.copyProperties(storedUserCourse.getUser(), returnVal.getUser());
        BeanUtils.copyProperties(storedUserCourse.getCourses(), returnVal.getCourses());
        BeanUtils.copyProperties(storedUserCourse, returnVal);
        return returnVal;
    }


    @Override
    public UserCourseDto getUserCourseById(Long userCourseId) {

        UserCourseDto returnValue = new UserCourseDto();
        Optional<UserCourseEntity> optional = userCourseRepository.findById(userCourseId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserCourseEntity userCourseEntity = optional.get();
        returnValue = convertEntityToDto(userCourseEntity);

        return returnValue;
    }

    @Override
    public UserCourseDto updateUserCourse(Long id, UserCourseDto userCourseDto) throws Exception {

        UserCourseDto returnValue;
        Optional<UserCourseEntity> optional = userCourseRepository.findById(id);
        if(!optional.isPresent()){
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserCourseEntity userCourseEntity = optional.get();
        convertDtoToEntity(userCourseDto, userCourseEntity, true);

        UserCourseEntity updatedUserCourse = userCourseRepository.save(userCourseEntity);
        returnValue=convertEntityToDto(updatedUserCourse);

        return returnValue;
    }

    @Override
    public void deleteUsersCourse(Long id) throws Exception {

        Optional<UserCourseEntity> optional = userCourseRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserCourseEntity userCourseEntity = optional.get();
        userCourseEntity.setDeleted(true);

        userCourseRepository.save(userCourseEntity);
    }

    @Override
    public List<UserCourseDto> getUserCourses(int page, int limit) throws Exception {

        List<UserCourseDto> returnValue = new ArrayList<>();
        if (page > 0) {
            page -= 1;
        }

        Pageable pageable = PageRequest.of(page, limit);
        Page<UserCourseEntity> userCoursePage = userCourseRepository.findAll(pageable);
        List<UserCourseEntity> userCourses = userCoursePage.getContent();

        for (UserCourseEntity userCourseEntity: userCourses){
            returnValue.add(convertEntityToDto(userCourseEntity));
        }

        return returnValue;
    }
}
