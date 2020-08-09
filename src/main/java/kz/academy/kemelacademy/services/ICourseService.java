package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.CourseDto;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
public interface ICourseService {
    
    CourseDto createCourse(CourseDto courseDto) throws Exception;
    
    List<CourseDto> getAll(int page, int limit) throws Exception;
    
    CourseDto getCourseById(long id) throws Exception;
    
    CourseDto updateCourse(long id, CourseDto courseDto) throws Exception;
    
    void deleteCourse(long id) throws Exception;
    
}
