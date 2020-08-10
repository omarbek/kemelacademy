package kz.academy.kemelacademy.services;


import kz.academy.kemelacademy.ui.dto.FileTypeDto;
import kz.academy.kemelacademy.ui.dto.LessonTypeDto;

import java.util.List;

public interface ILessonTypeService {

    LessonTypeDto createLessonType(LessonTypeDto lessonTypeDto) throws Exception;

    LessonTypeDto getLessonTypeById(long id) throws Exception;

    List<LessonTypeDto> getLessonTypeDtos(int page, int limit) throws Exception;

}
