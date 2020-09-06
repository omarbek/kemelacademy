package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.UserCourseDto;

public interface IUserCourseService {
    
    UserCourseDto createUsersCourse(UserCourseDto userCourseDto) throws Exception;
    
}
