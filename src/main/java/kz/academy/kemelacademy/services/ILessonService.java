package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.LessonDto;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
public interface ILessonService {
    
    LessonDto createLesson(LessonDto lessonDto);
    
}
