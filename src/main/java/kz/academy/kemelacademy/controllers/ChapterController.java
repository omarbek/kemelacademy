package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.IChapterService;
import kz.academy.kemelacademy.services.ICourseService;
import kz.academy.kemelacademy.ui.dto.ChapterDto;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.RequestOperationName;
import kz.academy.kemelacademy.ui.enums.RequestOperationStatus;
import kz.academy.kemelacademy.ui.model.request.ChapterRequestModel;
import kz.academy.kemelacademy.ui.model.response.ChapterRest;
import kz.academy.kemelacademy.ui.model.response.OperationStatusModel;
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
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header"),
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @PostMapping
    @Transactional
    public ChapterRest createChapter(@RequestBody ChapterRequestModel chapterRequestModel) {
        ChapterRest returnValue;
        
        Object[] fields = {
                chapterRequestModel.getChapterNo(),
                chapterRequestModel.getName()
        };
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        ChapterDto chapterDto = convertModelToDto(chapterRequestModel);
        
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
    
    private ChapterDto convertModelToDto(ChapterRequestModel chapterRequestModel) {
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
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
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
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @Transactional
    @GetMapping(path = "/{id}")
    public ChapterRest getChapter(@PathVariable("id") long id) {
        ChapterDto chapterDto;
        try {
            chapterDto = chapterService.getChapterById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return convertDtoToModel(chapterDto);
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header"),
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @Transactional
    @PutMapping(path = "/{id}")
    public ChapterRest updateChapter(@PathVariable("id") long id,
                                     @RequestBody ChapterRequestModel chapterRequestModel) {
        ChapterRest returnValue;
        
        ChapterDto chapterDto = convertModelToDto(chapterRequestModel);
        
        ChapterDto updatedChapter;
        try {
            updatedChapter = chapterService.updateChapter(id, chapterDto);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        returnValue = convertDtoToModel(updatedChapter);
        
        return returnValue;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header"),
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @Transactional
    @DeleteMapping(path = "/{id}")
    public OperationStatusModel delete(@PathVariable("id") long id) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
        
        try {
            chapterService.delete(id);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
    }
    
}
