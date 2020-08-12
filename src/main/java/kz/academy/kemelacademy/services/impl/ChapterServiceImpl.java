package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.repositories.IChapterRepository;
import kz.academy.kemelacademy.services.IChapterService;
import kz.academy.kemelacademy.ui.dto.ChapterDto;
import kz.academy.kemelacademy.ui.entity.ChapterEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
@Service
public class ChapterServiceImpl implements IChapterService {
    
    @Autowired
    private IChapterRepository chapterRepository;
    
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
            if (courseId != null && chapterEntity.getCourse().getId().equals(courseId)) {
                ChapterDto chapterDto = convertEntityToDto(chapterEntity);
                returnValue.add(chapterDto);
            }
        }
        
        return returnValue;
    }
    
    private ChapterDto convertEntityToDto(ChapterEntity savedChapter) {
        ChapterDto ret = new ChapterDto();
        
        BeanUtils.copyProperties(savedChapter.getCourse(), ret.getCourseDto());
        BeanUtils.copyProperties(savedChapter, ret);
        
        return ret;
    }
    
    private void convertDtoToEntity(ChapterDto chapterDto, ChapterEntity chapterEntity, boolean update) {
        if (chapterDto.getCourseDto().getId() != null) {
            BeanUtils.copyProperties(chapterDto.getCourseDto(), chapterEntity.getCourse());
        }
        if (!update) {
            BeanUtils.copyProperties(chapterDto, chapterEntity);
        } else {
            if (chapterDto.getChapterNo() != null) {
                chapterEntity.setChapterNo(chapterDto.getChapterNo());
            }
            if (chapterDto.getNameKz() != null) {
                chapterEntity.setNameKz(chapterDto.getNameKz());
            }
            if (chapterDto.getNameRu() != null) {
                chapterEntity.setNameRu(chapterDto.getNameRu());
            }
            if (chapterDto.getNameEn() != null) {
                chapterEntity.setNameEn(chapterDto.getNameEn());
            }
        }
    }
    
}
