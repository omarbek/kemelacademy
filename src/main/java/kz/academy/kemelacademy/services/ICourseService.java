package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.entity.CourseEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
public interface ICourseService {
    
    CourseDto createCourse(CourseDto courseDto) throws Exception;
    
    List<CourseDto> getAll(int page, int limit, Long categoryId, String name, Long progressStatusId) throws Exception;
    
    CourseDto getCourseById(long id) throws Exception;
    
    CourseDto updateCourse(long id, CourseDto courseDto) throws Exception;
    
    void deleteCourse(long id) throws Exception;
    
    public void delete(CourseEntity courseEntity) throws Exception;
    
    CourseDto uploadFile(Long courseId, MultipartFile file) throws Exception;
    
    void finishCourse(Long userId, Long courseId) throws Exception;
    
    void setRating(Long courseId, Double rating);
    
    List<CourseDto> getMyCourses(int page, int limit);
    
    List<CourseDto> myCoursesAsTeacher(int page, int limit) throws Exception;
    
    CourseDto uploadFile(MultipartFile image) throws Exception;
    
    void acceptCourse(long courseId) throws Exception;
    
    Resource loadFileAsResource(String fileName) throws Exception;
    
}
