package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.ILessonRepository;
import kz.academy.kemelacademy.repositories.IVideoRepository;
import kz.academy.kemelacademy.services.IFileTypeService;
import kz.academy.kemelacademy.services.ILessonService;
import kz.academy.kemelacademy.ui.dto.FileTypeDto;
import kz.academy.kemelacademy.ui.dto.LessonDto;
import kz.academy.kemelacademy.ui.entity.*;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
@Service
public class LessonServiceImpl implements ILessonService {
    
    @Autowired
    private ILessonRepository lessonRepository;
    
    @Autowired
    private IFileTypeService fileTypeService;
    
    @Autowired
    private IVideoRepository videoRepository;
    
    @Override
    public LessonDto createLesson(LessonDto lessonDto) {
        LessonEntity lessonEntity = new LessonEntity();
        
        convertDtoToEntity(lessonDto, lessonEntity, false);
        
        LessonEntity savedLesson = lessonRepository.save(lessonEntity);
        
        return convertEntityToDto(savedLesson);
    }
    
    private LessonDto convertEntityToDto(LessonEntity savedLesson) {
        LessonDto ret = new LessonDto();
        
        BeanUtils.copyProperties(savedLesson.getLessonType(), ret.getLessonTypeDto());
        BeanUtils.copyProperties(savedLesson.getChapter(), ret.getChapterDto());
        BeanUtils.copyProperties(savedLesson, ret);
        
        ret.setUrl(savedLesson.getVideo().getUrl());
        ret.setAlwaysOpen(savedLesson.getVideo().isAlwaysOpen());
        
        ret.setFileName(savedLesson.getFile() != null ? savedLesson.getFile().getName() : null);
        
        if (savedLesson.getTest() != null) {
            ret.setTestFileName(savedLesson.getTest().getFile().getName());
            ret.setDescription(savedLesson.getTest().getDescription());
        }
        
        return ret;
    }
    
    private void convertDtoToEntity(LessonDto lessonDto, LessonEntity lessonEntity, boolean update) {
        if (!update) {
            BeanUtils.copyProperties(lessonDto.getLessonTypeDto(), lessonEntity.getLessonType());
            BeanUtils.copyProperties(lessonDto.getChapterDto(), lessonEntity.getChapter());
            BeanUtils.copyProperties(lessonDto, lessonEntity);
            
            if (LessonTypeEntity.VIDEO.equals(lessonEntity.getLessonType().getId())) {
                VideoEntity videoEntity = new VideoEntity();
                videoEntity.setLesson(lessonEntity);
                videoEntity.setUrl(lessonDto.getUrl());
                videoEntity.setAlwaysOpen(lessonDto.isAlwaysOpen());
                
                videoRepository.save(videoEntity);
                
                lessonEntity.setVideo(videoEntity);
            } else if (LessonTypeEntity.FILE.equals(lessonEntity.getLessonType().getId())) {
                FileEntity fileEntity = new FileEntity();
                fileEntity.setLesson(lessonEntity);
                FileTypeDto fileTypeById;
                try {
                    fileTypeById = fileTypeService.getFileTypeById(FileTypeEntity.FOR_DOWNLOAD);
                } catch (ServiceException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
                }
                BeanUtils.copyProperties(fileTypeById, fileEntity.getFileType());
                fileEntity.setName(lessonDto.getFileName());
                
                lessonEntity.setFile(fileEntity);
            } else {
                TestEntity testEntity = new TestEntity();
                testEntity.setLesson(lessonEntity);
                
                FileEntity fileEntity = new FileEntity();
                FileTypeDto fileTypeById;
                try {
                    fileTypeById = fileTypeService.getFileTypeById(FileTypeEntity.FOR_TESTS);
                } catch (ServiceException e) {
                    throw e;
                } catch (Exception e) {
                    throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
                }
                BeanUtils.copyProperties(fileTypeById, fileEntity.getFileType());
                fileEntity.setName(lessonDto.getTestFileName());
                
                testEntity.setFile(fileEntity);
                testEntity.setDescription(lessonDto.getDescription());
                
                lessonEntity.setTest(testEntity);
            }
        }
    }
    
}
