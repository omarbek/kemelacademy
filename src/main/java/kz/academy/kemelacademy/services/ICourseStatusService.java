package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.CourseStatusDto;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-03
 * @project kemelacademy
 */
public interface ICourseStatusService {
    
    CourseStatusDto getEntityById(long id) throws Exception;
    
    List<CourseStatusDto> getAll();
    
}
