package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.LessonDto;
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
    
    List<LessonDto> getAll(int page, int limit, Long chapterId) throws Exception;
    
    LessonDto update(long id, LessonDto dto) throws Exception;
    
}
