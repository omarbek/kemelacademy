package kz.academy.kemelacademy.controllers;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.IChapterService;
import kz.academy.kemelacademy.services.ILessonService;
import kz.academy.kemelacademy.services.ILessonTypeService;
import kz.academy.kemelacademy.ui.dto.ChapterDto;
import kz.academy.kemelacademy.ui.dto.LessonDto;
import kz.academy.kemelacademy.ui.dto.LessonTypeDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.model.request.LessonRequestModel;
import kz.academy.kemelacademy.ui.model.response.LessonRest;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-14
 * @project kemelacademy
 */
@Slf4j
@RestController
@RequestMapping("lessons")
public class LessonController {
    
    @Autowired
    private ILessonTypeService lessonTypeService;
    
    @Autowired
    private IChapterService chapterService;
    
    @Autowired
    private ILessonService lessonService;
    
    @PostMapping
    @Transactional
    public LessonRest createLesson(@RequestBody LessonRequestModel lessonRequestModel) {
        LessonRest returnValue;
        
        Object[] fields = {
                lessonRequestModel.getLessonTypeId(),
                lessonRequestModel.getChapterId(),
                lessonRequestModel.getLessonNo()
        };
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        LessonDto lessonDto = convertModelToDto(lessonRequestModel);
        
        LessonDto createdLesson;
        try {
            createdLesson = lessonService.createLesson(lessonDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        returnValue = convertDtoToModel(createdLesson);
        
        return returnValue;
    }
    
    private LessonRest convertDtoToModel(LessonDto createdLesson) {
        LessonRest ret = new LessonRest();
        
        ret.setLessonType(createdLesson.getLessonTypeDto().toString());
        ret.setChapter(createdLesson.getChapterDto().toString());
        BeanUtils.copyProperties(createdLesson, ret);
        
        return ret;
    }
    
    private LessonDto convertModelToDto(LessonRequestModel lessonRequestModel) {
        LessonDto ret = new LessonDto();
        
        if (lessonRequestModel.getLessonTypeId() != null) {
            LessonTypeDto lessonTypeDto;
            try {
                lessonTypeDto = lessonTypeService.getLessonTypeById(lessonRequestModel.getLessonTypeId());
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            ret.setLessonTypeDto(lessonTypeDto);
        }
        if (lessonRequestModel.getChapterId() != null) {
            ChapterDto chapterDto;
            try {
                chapterDto = chapterService.getChapterById(lessonRequestModel.getChapterId());
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            ret.setChapterDto(chapterDto);
        }
        
        BeanUtils.copyProperties(lessonRequestModel, ret);
        
        return ret;
    }
    
}
