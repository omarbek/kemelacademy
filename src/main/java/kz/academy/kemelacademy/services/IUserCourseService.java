package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.UserCourseDto;

import java.util.List;

public interface IUserCourseService {
    
    UserCourseDto createUsersCourse(UserCourseDto userCourseDto) throws Exception;
    
    UserCourseDto getUserCourseById(Long userCourseId);
    
    UserCourseDto updateUserCourse(Long id, UserCourseDto userCourseDto) throws Exception;
    
    void deleteUsersCourse(Long id) throws Exception;
    
    List<UserCourseDto> getUserCourses(int page, int limit) throws Exception;
    
}
