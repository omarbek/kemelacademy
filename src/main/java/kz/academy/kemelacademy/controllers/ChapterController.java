package kz.academy.kemelacademy.controllers;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.IChapterService;
import kz.academy.kemelacademy.services.ICourseService;
import kz.academy.kemelacademy.ui.dto.ChapterDto;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.model.request.ChapterRequestModel;
import kz.academy.kemelacademy.ui.model.response.ChapterRest;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
@Slf4j
@RestController
@RequestMapping("chapters")
public class ChapterController {
    
    @Autowired
    private IChapterService chapterService;
    
    @Autowired
    private ICourseService courseService;
    
    @PostMapping
    @Transactional
    public ChapterRest createChapter(@RequestBody ChapterRequestModel chapterRequestModel) {
        ChapterRest returnValue;
        
        Object[] fields = {
                chapterRequestModel.getChapterNo(),
                chapterRequestModel.getNameKz(),
                chapterRequestModel.getNameRu(),
                chapterRequestModel.getNameEn()
        };
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        ChapterDto chapterDto = convertModelToDto(chapterRequestModel, false);
        
        ChapterDto createdChapter;
        try {
            createdChapter = chapterService.createChapter(chapterDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        returnValue = convertDtoToModel(createdChapter);
        
        return returnValue;
    }
    
    private ChapterRest convertDtoToModel(ChapterDto createdChapter) {
        ChapterRest ret = new ChapterRest();
        
        ret.setCourse(createdChapter.getCourseDto().toString());
        //        ret.setDuration();//todo
        //        ret.setLessonCount();//todo
        BeanUtils.copyProperties(createdChapter, ret);
        
        return ret;
    }
    
    private ChapterDto convertModelToDto(ChapterRequestModel chapterRequestModel, boolean update) {
        ChapterDto ret = new ChapterDto();
        
        if (chapterRequestModel.getCourseId() != null) {
            CourseDto courseDto;
            try {
                courseDto = courseService.getCourseById(chapterRequestModel.getCourseId());
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            ret.setCourseDto(courseDto);
            
        }
        
        BeanUtils.copyProperties(chapterRequestModel, ret);
        
        return ret;
    }
    
    @GetMapping
    @Transactional
    public List<ChapterRest> getChapters(@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "limit", defaultValue = "25") int limit,
                                         @RequestParam(value = "courseId") Long courseId) {
        List<ChapterRest> returnVal = new ArrayList<>();
        
        List<ChapterDto> chapters;
        try {
            chapters = chapterService.getAll(page, limit, courseId);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (ChapterDto chapterDto: chapters) {
            ChapterRest chapterRest = convertDtoToModel(chapterDto);
            returnVal.add(chapterRest);
        }
        
        return returnVal;
    }
    
}
