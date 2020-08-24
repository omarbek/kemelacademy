package kz.academy.kemelacademy.controllers;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.IChapterService;
import kz.academy.kemelacademy.services.ILessonService;
import kz.academy.kemelacademy.services.ILessonTypeService;
import kz.academy.kemelacademy.ui.dto.*;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.RequestOperationName;
import kz.academy.kemelacademy.ui.enums.RequestOperationStatus;
import kz.academy.kemelacademy.ui.model.request.LessonRequestModel;
import kz.academy.kemelacademy.ui.model.response.LessonRest;
import kz.academy.kemelacademy.ui.model.response.OperationStatusModel;
import kz.academy.kemelacademy.ui.model.response.UserTestRest;
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
    
    @PostMapping
    @Transactional
    public LessonRest create(@RequestBody LessonRequestModel requestModel) {
        LessonRest returnValue;
        
        Object[] fields = {
                requestModel.getLessonTypeId(),
                requestModel.getChapterId(),
                requestModel.getLessonNo()
        };
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        LessonDto dto = convertModelToDto(requestModel, false);
        
        LessonDto createdDto;
        try {
            createdDto = lessonService.create(dto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        returnValue = convertDtoToModel(createdDto);
        
        return returnValue;
    }
    
    @Transactional
    @PostMapping(path = "createFile/{id}")
    public LessonRest createFile(@RequestParam("file") MultipartFile file,
                                 @PathVariable("id") Long lessonId) {
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
        
        returnValue = convertDtoToModel(uploadedFileDto);
        
        return returnValue;
    }
    
    @GetMapping
    @Transactional
    public List<LessonRest> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit,
                                   @RequestParam(value = "chapterId", required = false) Long chapterId) {
        List<LessonRest> returnVal = new ArrayList<>();
        
        List<LessonDto> dtoList;
        try {
            dtoList = lessonService.getAll(page, limit, chapterId);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (LessonDto dto: dtoList) {
            LessonRest rest = convertDtoToModel(dto);
            returnVal.add(rest);
        }
        
        return returnVal;
    }
    
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
        
        return convertDtoToModel(dto);
    }
    
    private LessonRest convertDtoToModel(LessonDto createdDto) {
        LessonRest ret = new LessonRest();
        
        ret.setLessonType(createdDto.getLessonTypeDto().toString());
        ret.setChapter(createdDto.getChapterDto().toString());
        BeanUtils.copyProperties(createdDto, ret);
        
        return ret;
    }
    
    private LessonDto convertModelToDto(LessonRequestModel requestModel, boolean update) {
        LessonDto ret = new LessonDto();
        
        if (!update) {
            if (requestModel.getLessonTypeId() != null) {
                LessonTypeDto lessonTypeDto;
                try {
                    lessonTypeDto = lessonTypeService.getLessonTypeById(requestModel.getLessonTypeId());
                } catch (ServiceException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
                }
                ret.setLessonTypeDto(lessonTypeDto);
            }
        }
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
    
    @Transactional
    @PutMapping(path = "/{id}")
    public LessonRest update(@PathVariable("id") long id,
                             @RequestBody LessonRequestModel requestModel) {
        LessonRest returnValue;
        
        LessonDto dto = convertModelToDto(requestModel, true);
        
        LessonDto updatedDto;
        try {
            updatedDto = lessonService.update(id, dto);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        returnValue = convertDtoToModel(updatedDto);
        
        return returnValue;
    }
    
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
    
    @Transactional
    @PostMapping(path = "sendTestWork/{testId}")
    public UserTestRest sendTestWork(@PathVariable("testId") Long testId) {
        UserTestDto dto;
        try {
            dto = lessonService.uploadTest(testId);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        UserTestRest ret = new UserTestRest();
        ret.setTest(dto.getTest().toString());
        ret.setStatus(dto.getTestStatus().toString());
        BeanUtils.copyProperties(dto, ret);
        
        return ret;
    }
    
    @Transactional
    @PostMapping(path = "uploadHomeWork/{id}")
    public UserTestRest uploadHomeWork(@RequestParam("file") MultipartFile file,
                                       @PathVariable("id") Long userTestId) {
        if (file == null || file.isEmpty()) {
            throw new ServiceException(ErrorMessages.PLEASE_SELECT_FILE.getErrorMessage());
        }
        
        UserTestDto uploadedFileDto;
        try {
            uploadedFileDto = lessonService.uploadHomeWork(userTestId, file);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        UserTestRest ret = new UserTestRest();
        ret.setTest(uploadedFileDto.getTest().toString());
        ret.setStatus(uploadedFileDto.getTestStatus().toString());
        for (FileDto fileDto: uploadedFileDto.getFiles()) {
            ret.getFileNames().add(fileDto.getName());
        }
        BeanUtils.copyProperties(uploadedFileDto, ret);
        
        return ret;
    }
    
    @Transactional
    @PostMapping(path = "changeStatus/{userTestId}/{statusId}")
    public OperationStatusModel changeStatus(@PathVariable("userTestId") Long userTestId,
                                             @PathVariable("statusId") Long statusId) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.CHANGE_STATUS.name());
        
        try {
            lessonService.changeStatus(userTestId, statusId);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
    }
    
    @Transactional
    @PostMapping(path = "setGrade/{userTestId}/{grade}/{comment}")
    public OperationStatusModel setGrade(@PathVariable("userTestId") Long userTestId,
                                         @PathVariable("grade") Integer grade,
                                         @PathVariable(value = "comment", required = false) String comment) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.SET_GRADE.name());
        
        try {
            lessonService.setGrade(userTestId, grade, comment);
            lessonService.changeStatus(userTestId, 3L);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
    }
    
}
