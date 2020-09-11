package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.IChapterRepository;
import kz.academy.kemelacademy.repositories.ICourseRepository;
import kz.academy.kemelacademy.services.IChapterService;
import kz.academy.kemelacademy.services.ILessonService;
import kz.academy.kemelacademy.ui.dto.ChapterDto;
import kz.academy.kemelacademy.ui.dto.LessonDto;
import kz.academy.kemelacademy.ui.entity.ChapterEntity;
import kz.academy.kemelacademy.ui.entity.CourseEntity;
import kz.academy.kemelacademy.ui.entity.LessonEntity;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
@Service
public class ChapterServiceImpl implements IChapterService {
    
    @Autowired
    private IChapterRepository chapterRepository;
    
    @Autowired
    private ICourseRepository courseRepository;
    
    @Autowired
    private ILessonService lessonService;
    
    @Override
    public ChapterDto createChapter(ChapterDto chapterDto) throws Exception {
        ChapterEntity chapterEntity = new ChapterEntity();
        
        convertDtoToEntity(chapterDto, chapterEntity, false);
        
        ChapterEntity savedChapter = chapterRepository.save(chapterEntity);
        
        return convertEntityToDto(savedChapter);
    }
    
    @Override
    public List<ChapterDto> getAll(int page, int limit, Long courseId) {
        List<ChapterDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<ChapterEntity> chapterEntityPage = chapterRepository.findAll(pageable);
        List<ChapterEntity> chapters = chapterEntityPage.getContent();
        
        for (ChapterEntity chapterEntity: chapters) {
            if (!chapterEntity.isDeleted() && courseId != null && chapterEntity.getCourse().getId().equals(courseId)) {
                ChapterDto chapterDto = convertEntityToDto(chapterEntity);
                returnValue.add(chapterDto);
            }
        }
        
        return returnValue;
    }
    
    @Override
    public ChapterDto getChapterById(long id) {
        ChapterDto returnValue;
        
        Optional<ChapterEntity> optional = chapterRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        ChapterEntity chapterEntity = optional.get();
        
        returnValue = convertEntityToDto(chapterEntity);
        
        return returnValue;
    }
    
    @Override
    public ChapterDto updateChapter(long id, ChapterDto chapterDto) throws Exception {
        ChapterDto returnValue;
        
        Optional<ChapterEntity> optional = chapterRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        ChapterEntity chapterEntity = optional.get();
        convertDtoToEntity(chapterDto, chapterEntity, true);
        
        ChapterEntity updatedChapter = chapterRepository.save(chapterEntity);
        returnValue = convertEntityToDto(updatedChapter);
        
        return returnValue;
    }
    
    @Override
    public void delete(long id) throws Exception {
        Optional<ChapterEntity> optional = chapterRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        ChapterEntity entity = optional.get();
        delete(entity);
    }
    
    @Override
    public void delete(ChapterEntity chapterEntity) throws Exception {
        chapterEntity.setDeleted(true);
        
        Iterator<LessonEntity> lessons = chapterEntity.getLessons().iterator();
        while (lessons.hasNext()) {
            LessonEntity lessonEntity = lessons.next();
            lessonService.delete(lessonEntity);
        }
        chapterEntity.setLessons(new ArrayList<>());
        
        chapterRepository.save(chapterEntity);
    }
    
    private ChapterDto convertEntityToDto(ChapterEntity savedChapter) {
        ChapterDto ret = new ChapterDto();
        
        BeanUtils.copyProperties(savedChapter.getCourse(), ret.getCourseDto());
        for (LessonEntity lessonEntity: savedChapter.getLessons()) {
            LessonDto lessonDto = new LessonDto();
            BeanUtils.copyProperties(lessonEntity, lessonDto);
            ret.getLessons().add(lessonDto);
        }
        BeanUtils.copyProperties(savedChapter, ret);
        
        return ret;
    }
    
    private void convertDtoToEntity(ChapterDto chapterDto, ChapterEntity chapterEntity, boolean update) {
        Long courseId = chapterDto.getCourseDto().getId();
        if (courseId != null) {
            if (update) {
                if (!courseId.equals(chapterEntity.getCourse().getId())) {
                    Optional<CourseEntity> optional = courseRepository.findById(courseId);
                    CourseEntity courseEntity = optional.get();
                    chapterEntity.setCourse(courseEntity);
                }
            } else {
                BeanUtils.copyProperties(chapterDto.getCourseDto(), chapterEntity.getCourse());
            }
        }
        if (!update) {
            BeanUtils.copyProperties(chapterDto, chapterEntity);
        } else {
            if (chapterDto.getChapterNo() != null) {
                chapterEntity.setChapterNo(chapterDto.getChapterNo());
            }
            if (chapterDto.getName() != null) {
                chapterEntity.setName(chapterDto.getName());
            }
        }
    }
    
}
