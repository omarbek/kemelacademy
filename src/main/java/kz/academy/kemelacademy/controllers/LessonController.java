package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.IChapterService;
import kz.academy.kemelacademy.services.ILessonService;
import kz.academy.kemelacademy.services.ILessonTypeService;
import kz.academy.kemelacademy.ui.dto.ChapterDto;
import kz.academy.kemelacademy.ui.dto.LessonDto;
import kz.academy.kemelacademy.ui.dto.UserHomeWorkDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.RequestOperationName;
import kz.academy.kemelacademy.ui.enums.RequestOperationStatus;
import kz.academy.kemelacademy.ui.model.request.LessonRequestModel;
import kz.academy.kemelacademy.ui.model.request.VideoRequestModel;
import kz.academy.kemelacademy.ui.model.response.LessonRest;
import kz.academy.kemelacademy.ui.model.response.OperationStatusModel;
import kz.academy.kemelacademy.ui.model.response.UserHomeWorkRest;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    public LessonRest create(@RequestBody LessonRequestModel requestModel) {
        LessonRest returnValue;
        
        Object[] fields = {
                requestModel.getChapterId(),
                requestModel.getLessonNo(),
                requestModel.getName()
        };
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        LessonDto dto = convertModelToDto(requestModel);
        
        LessonDto createdDto;
        try {
            createdDto = lessonService.create(dto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        returnValue = lessonService.convertDtoToRest(createdDto);
        
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
    @PostMapping(path = "createVideo")
    @Transactional
    public LessonRest createVideo(@RequestBody VideoRequestModel videoRequestModel) {
        Object[] fields = {
                videoRequestModel.getLessonId(),
                videoRequestModel.getDuration(),
                videoRequestModel.getUrl(),
                videoRequestModel.isAlwaysOpen()
        };
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        LessonDto createdDto;
        try {
            createdDto = lessonService.createVideo(videoRequestModel);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return lessonService.convertDtoToRest(createdDto);
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
    @PostMapping(path = "uploadFile/{lessonId}")
    public LessonRest uploadFile(@RequestParam("file") MultipartFile file,
                                 @PathVariable("lessonId") Long lessonId) {
        LessonRest returnValue;
        
        if (file == null || file.isEmpty()) {
            throw new ServiceException(ErrorMessages.PLEASE_SELECT_FILE.getErrorMessage());
        }
        
        LessonDto uploadedFileDto;
        try {
            uploadedFileDto = lessonService.uploadFile(lessonId, file);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        returnValue = lessonService.convertDtoToRest(uploadedFileDto);
        
        return returnValue;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header")
    })
    @Transactional
    @PostMapping(path = "uploadVideo/{lessonId}")
    public LessonRest uploadVideo(@RequestParam("file") MultipartFile file,
                                  @PathVariable("lessonId") Long lessonId) {
        LessonRest returnValue;
        
        if (file == null || file.isEmpty()) {
            throw new ServiceException(ErrorMessages.PLEASE_SELECT_FILE.getErrorMessage());
        }
        
        LessonDto uploadedVideoDto;
        try {
            uploadedVideoDto = lessonService.uploadVideo(lessonId, file);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        returnValue = lessonService.convertDtoToRest(uploadedVideoDto);
        
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
    @PostMapping(path = "createHomeWorkLesson/{lessonId}")
    public LessonRest createHomeWorkLesson(@RequestParam("description") String description,
                                           @PathVariable("lessonId") Long lessonId) {
        LessonDto createdDto;
        try {
            createdDto = lessonService.createHomeWorkLesson(lessonId, description);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return lessonService.convertDtoToRest(createdDto);
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
    @GetMapping
    @Transactional
    public List<LessonRest> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit,
                                   @RequestParam(value = "chapterId", required = false) Long chapterId,
                                   @RequestParam(value = "courseId") Long courseId) {
        Object[] fields = {courseId};
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        List<LessonRest> returnVal = new ArrayList<>();
        
        List<LessonDto> dtoList;
        try {
            dtoList = lessonService.getAll(page, limit, chapterId, courseId);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (LessonDto dto: dtoList) {
            LessonRest rest = lessonService.convertDtoToRest(dto);
            returnVal.add(rest);
        }
        
        return returnVal;
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
    @GetMapping(path = "/{id}")
    public LessonRest getById(@PathVariable("id") long id) {
        LessonDto dto;
        try {
            dto = lessonService.getLessonById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return lessonService.convertDtoToRest(dto);
    }
    
    private LessonDto convertModelToDto(LessonRequestModel requestModel) {
        LessonDto ret = new LessonDto();
        
        if (requestModel.getChapterId() != null) {
            ChapterDto chapterDto;
            try {
                chapterDto = chapterService.getChapterById(requestModel.getChapterId());
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            ret.setChapterDto(chapterDto);
        }
        
        BeanUtils.copyProperties(requestModel, ret);
        
        return ret;
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
    public LessonRest update(@PathVariable("id") long id,
                             @RequestBody LessonRequestModel requestModel) {
        LessonRest returnValue;
        
        LessonDto dto = convertModelToDto(requestModel);
        
        LessonDto updatedDto;
        try {
            updatedDto = lessonService.update(id, dto);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        returnValue = lessonService.convertDtoToRest(updatedDto);
        
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
            lessonService.delete(id);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
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
    @PostMapping(path = "createHomeWork/{lessonId}")
    public UserHomeWorkRest createHomeWork(@PathVariable("lessonId") Long lessonId) {
        UserHomeWorkDto dto;
        try {
            dto = lessonService.createHomeWork(lessonId);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        UserHomeWorkRest ret = new UserHomeWorkRest();
        ret.setHomeWorkName(dto.getHomeWork().toString());
        ret.setStatus(dto.getHomeWorkStatus().toString());
        BeanUtils.copyProperties(dto, ret);
        
        return ret;
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
    @PostMapping(path = "uploadHomeWork/{userHomeWorkId}")
    public UserHomeWorkRest uploadHomeWork(@RequestParam("file") MultipartFile file,
                                           @PathVariable("userHomeWorkId") Long userHomeWorkId) {
        if (file == null || file.isEmpty()) {
            throw new ServiceException(ErrorMessages.PLEASE_SELECT_FILE.getErrorMessage());
        }
        
        UserHomeWorkDto uploadedFileDto;
        try {
            uploadedFileDto = lessonService.uploadHomeWork(userHomeWorkId, file);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        UserHomeWorkRest ret = new UserHomeWorkRest();
        ret.setHomeWorkName(uploadedFileDto.getHomeWork().toString());
        ret.setStatus(uploadedFileDto.getHomeWorkStatus().toString());
        ret.setFileName(uploadedFileDto.getFile().getName());
        BeanUtils.copyProperties(uploadedFileDto, ret);
        
        return ret;
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
    @PostMapping(path = "changeStatus/{userHomeWorkId}/{statusId}")
    public OperationStatusModel changeStatus(@PathVariable("userHomeWorkId") Long userHomeWorkId,
                                             @PathVariable("statusId") Long statusId) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.CHANGE_STATUS.name());
        
        try {
            lessonService.changeStatus(userHomeWorkId, statusId);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
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
    @PostMapping(path = "setGrade/{userHomeWorkId}/{grade}/{comment}")
    public OperationStatusModel setGrade(@PathVariable("userHomeWorkId") Long userHomeWorkId,
                                         @PathVariable("grade") Integer grade,
                                         @PathVariable(value = "comment", required = false) String comment) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.SET_GRADE.name());
        
        try {
            lessonService.setGrade(userHomeWorkId, grade, comment);
            lessonService.changeStatus(userHomeWorkId, 3L);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
    }
    
}
