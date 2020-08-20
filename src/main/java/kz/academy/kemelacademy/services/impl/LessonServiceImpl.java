package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.*;
import kz.academy.kemelacademy.services.IFileTypeService;
import kz.academy.kemelacademy.services.ILessonService;
import kz.academy.kemelacademy.ui.dto.FileTypeDto;
import kz.academy.kemelacademy.ui.dto.LessonDto;
import kz.academy.kemelacademy.ui.entity.*;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    
    @Autowired
    private IFileRepository fileRepository;
    
    @Autowired
    private ITestRepository testRepository;
    
    @Autowired
    private IChapterRepository chapterRepository;
    
    private static String UPLOADED_FOLDER = "/Users/omar/Desktop/";
    
    @Override
    public LessonDto create(LessonDto lessonDto) throws Exception {
        LessonEntity lessonEntity = new LessonEntity();
        
        convertDtoToEntity(lessonDto, lessonEntity, false);
        
        LessonEntity savedLesson = lessonRepository.save(lessonEntity);
        
        return convertEntityToDto(savedLesson);
    }
    
    @Override
    public LessonDto getLessonById(Long lessonId) {
        LessonDto returnValue;
        
        LessonEntity lessonEntity = getLessonEntityById(lessonId);
        
        returnValue = convertEntityToDto(lessonEntity);
        
        return returnValue;
    }
    
    private LessonEntity getLessonEntityById(Long lessonId) {
        Optional<LessonEntity> optional = lessonRepository.findById(lessonId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return optional.get();
    }
    
    @Override
    public LessonDto uploadFile(Long lessonId, MultipartFile file) throws Exception {
        String filename = UPLOADED_FOLDER + file.getOriginalFilename();
        
        byte[] bytes = file.getBytes();
        Path path = Paths.get(filename);
        Files.write(path, bytes);
        
        LessonEntity lessonEntity = getLessonEntityById(lessonId);
        if (LessonTypeEntity.FILE.equals(lessonEntity.getLessonType().getId())) {
            lessonEntity.getFile().setName(filename);
        } else if (LessonTypeEntity.TEST.equals(lessonEntity.getLessonType().getId())) {
            lessonEntity.getTest().getFile().setName(filename);
        }
        LessonEntity uploadedFileLessonEntity = lessonRepository.save(lessonEntity);
        
        return convertEntityToDto(uploadedFileLessonEntity);
    }
    
    @Override
    public List<LessonDto> getAll(int page, int limit, Long chapterId) throws Exception {
        List<LessonDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<LessonEntity> lessonEntityPage = lessonRepository.findAll(pageable);
        List<LessonEntity> lessonEntities = lessonEntityPage.getContent();
        
        for (LessonEntity lessonEntity: lessonEntities) {
            if (!lessonEntity.isDeleted()) {
                if (chapterId != null) {
                    if (lessonEntity.getChapter().getId().equals(chapterId)) {
                        LessonDto lessonDto = convertEntityToDto(lessonEntity);
                        returnValue.add(lessonDto);
                    }
                } else {
                    LessonDto lessonDto = convertEntityToDto(lessonEntity);
                    returnValue.add(lessonDto);
                }
            }
        }
        
        return returnValue;
    }
    
    @Override
    public LessonDto update(long id, LessonDto dto) throws Exception {
        LessonDto returnValue;
        
        Optional<LessonEntity> optional = lessonRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        LessonEntity entity = optional.get();
        convertDtoToEntity(dto, entity, true);
        
        LessonEntity updatedEntity = lessonRepository.save(entity);
        returnValue = convertEntityToDto(updatedEntity);
        
        return returnValue;
    }
    
    @Override
    public void delete(long id) throws Exception {
        Optional<LessonEntity> optional = lessonRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        LessonEntity entity = optional.get();
        entity.setDeleted(true);
        lessonRepository.save(entity);
    }
    
    private LessonDto convertEntityToDto(LessonEntity savedLesson) {
        LessonDto ret = new LessonDto();
        
        BeanUtils.copyProperties(savedLesson.getLessonType(), ret.getLessonTypeDto());
        BeanUtils.copyProperties(savedLesson.getChapter(), ret.getChapterDto());
        BeanUtils.copyProperties(savedLesson, ret);
        
        if (savedLesson.getVideo() != null) {
            ret.setUrl(savedLesson.getVideo().getUrl());
            ret.setAlwaysOpen(savedLesson.getVideo().isAlwaysOpen());
        }
        
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
                fileEntity.setName(null);
                
                fileRepository.save(fileEntity);
                
                lessonEntity.setFile(fileEntity);
            } else {//test
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
                fileEntity.setLesson(null);
                
                testEntity.setFile(fileEntity);
                testEntity.setDescription(lessonDto.getDescription());
                
                testRepository.save(testEntity);
                
                lessonEntity.setTest(testEntity);
            }
        } else {
            Long chapterId = lessonDto.getChapterDto().getId();
            if (chapterId != null) {
                if (!chapterId.equals(lessonEntity.getChapter().getId())) {
                    Optional<ChapterEntity> optional = chapterRepository.findById(chapterId);
                    ChapterEntity chapterEntity = optional.get();
                    lessonEntity.setChapter(chapterEntity);
                }
            }
            if (lessonDto.getLessonNo() != null) {
                lessonEntity.setLessonNo(lessonDto.getLessonNo());
            }
            if (lessonDto.getDuration() != null) {
                lessonEntity.setDuration(lessonDto.getDuration());
            }
            if (lessonDto.getNameKz() != null) {
                lessonEntity.setNameKz(lessonDto.getNameKz());
            }
            if (lessonDto.getNameRu() != null) {
                lessonEntity.setNameRu(lessonDto.getNameRu());
            }
            if (lessonDto.getNameEn() != null) {
                lessonEntity.setNameEn(lessonDto.getNameEn());
            }
            
            if (LessonTypeEntity.VIDEO.equals(lessonEntity.getLessonType().getId())) {
                if (lessonDto.getUrl() != null) {
                    lessonEntity.getVideo().setUrl(lessonDto.getUrl());
                }
                lessonEntity.getVideo().setAlwaysOpen(lessonDto.isAlwaysOpen());
            } else if (LessonTypeEntity.FILE.equals(lessonEntity.getLessonType().getId())) {
                if (lessonDto.getFileName() != null) {
                    lessonEntity.getFile().setName(lessonDto.getFileName());
                }
            } else {//test
                if (lessonDto.getTestFileName() != null) {
                    lessonEntity.getTest().getFile().setName(lessonDto.getTestFileName());
                }
                if (lessonDto.getDescription() != null) {
                    lessonEntity.getTest().setDescription(lessonDto.getDescription());
                }
            }
        }
    }
    
}
