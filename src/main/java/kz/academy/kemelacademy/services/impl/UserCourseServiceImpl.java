package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.repositories.IUserCourseRepository;
import kz.academy.kemelacademy.services.IUserCourseService;
import kz.academy.kemelacademy.ui.dto.UserCourseDto;
import kz.academy.kemelacademy.ui.entity.UserCourseEntity;
import kz.academy.kemelacademy.ui.entity.UserCourseId;
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
        UserCourseId userCourseId = new UserCourseId();
        userCourseId.setUserId(userUtils.getCurrentUserDto().getId());
        userCourseId.setCourseId(usercourseDto.getCourse().getId());
        
        UserCourseEntity userCourseEntity = new UserCourseEntity();
        userCourseEntity.setUserCourseId(userCourseId);
        userCourseEntity.setUser(userUtils.getCurrentUserEntity());
        userCourseEntity.setFinished(false);
        BeanUtils.copyProperties(usercourseDto.getCourse(), userCourseEntity.getCourse());
        
        UserCourseEntity storedUserCourse = userCourseRepository.save(userCourseEntity);
        
        UserCourseDto userCourseDto = new UserCourseDto();
        BeanUtils.copyProperties(storedUserCourse.getCourse(), userCourseDto.getCourse());
        BeanUtils.copyProperties(storedUserCourse, userCourseDto);
        
        return userCourseDto;
    }
    
}
