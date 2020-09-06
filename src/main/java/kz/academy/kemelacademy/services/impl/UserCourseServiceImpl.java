package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.repositories.IUserCourseRepository;
import kz.academy.kemelacademy.services.IUserCourseService;
import kz.academy.kemelacademy.ui.dto.UserCourseDto;
import kz.academy.kemelacademy.ui.entity.UserCourseEntity;
import kz.academy.kemelacademy.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCourseServiceImpl implements IUserCourseService {
    
    @Autowired
    private IUserCourseRepository userCourseRepository;
    
    @Autowired
    private UserUtils userUtils;
    
    @Override
    public UserCourseDto createUsersCourse(UserCourseDto usercourseDto) throws Exception {
        UserCourseEntity userCourseEntity = new UserCourseEntity();
        convertDtoToEntity(usercourseDto, userCourseEntity);
        UserCourseEntity storedUserCourse = userCourseRepository.save(userCourseEntity);
        return convertEntityToDto(storedUserCourse);
    }
    
    private void convertDtoToEntity(UserCourseDto userCourseDto, UserCourseEntity userCourseEntity) {
        userCourseEntity.setUser(userUtils.getCurrentUserEntity());
        BeanUtils.copyProperties(userCourseDto.getCourse(), userCourseEntity.getCourse());
        BeanUtils.copyProperties(userCourseDto, userCourseEntity);
    }
    
    private UserCourseDto convertEntityToDto(UserCourseEntity storedUserCourse) {
        UserCourseDto returnVal = new UserCourseDto();
        BeanUtils.copyProperties(storedUserCourse.getCourse(), returnVal.getCourse());
        BeanUtils.copyProperties(storedUserCourse, returnVal);
        return returnVal;
    }
    
}
