package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.repositories.IChapterRepository;
import kz.academy.kemelacademy.services.IChapterService;
import kz.academy.kemelacademy.ui.dto.ChapterDto;
import kz.academy.kemelacademy.ui.entity.ChapterEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
