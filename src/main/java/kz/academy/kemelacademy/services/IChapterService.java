package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.ChapterDto;
import kz.academy.kemelacademy.ui.entity.ChapterEntity;
import kz.academy.kemelacademy.ui.model.response.ChapterRest;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
public interface IChapterService {
    
    ChapterDto createChapter(ChapterDto chapterDto) throws Exception;
    
    List<ChapterDto> getAll(int page, int limit, Long courseId) throws Exception;
    
    ChapterDto getChapterById(long id) throws Exception;
    
    ChapterDto updateChapter(long id, ChapterDto chapterDto) throws Exception;
    
    void delete(long id) throws Exception;
    
    void delete(ChapterEntity entity) throws Exception;
    
    ChapterRest convertDtoToRest(ChapterDto createdChapter);
    
}
