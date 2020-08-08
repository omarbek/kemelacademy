package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.CourseDto;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
public interface ICourseService {
    
    CourseDto createCourse(CourseDto courseDto) throws Exception;
    
}
