package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.LessonDto;
import kz.academy.kemelacademy.ui.dto.UserHomeWorkDto;
import kz.academy.kemelacademy.ui.entity.LessonEntity;
import kz.academy.kemelacademy.ui.model.request.VideoRequestModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
public interface ILessonService {
    
    LessonDto create(LessonDto lessonDto) throws Exception;
    
    LessonDto getLessonById(Long lessonId);
    
    LessonDto uploadFile(Long lessonId, MultipartFile file) throws Exception;
    
    List<LessonDto> getAll(int page, int limit, Long chapterId, Long courseId) throws Exception;
    
    LessonDto update(long id, LessonDto dto) throws Exception;
    
    void delete(long id) throws Exception;
    
    void delete(LessonEntity entity) throws Exception;
    
    UserHomeWorkDto createHomeWork(Long testId) throws Exception;
    
    UserHomeWorkDto uploadHomeWork(Long userHomeWorkId, MultipartFile file) throws Exception;
    
    void changeStatus(Long userHomeWorkId, Long statusId) throws Exception;
    
    void setGrade(Long userHomeWorkId, Integer grade, String comment) throws Exception;
    
    LessonDto createVideo(VideoRequestModel videoRequestModel) throws Exception;
    
    LessonDto createHomeWorkLesson(Long lessonId, String description) throws Exception;
    
}
