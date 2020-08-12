package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.ChapterDto;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
public interface IChapterService {
    
    ChapterDto createChapter(ChapterDto chapterDto) throws Exception;
    
    List<ChapterDto> getAll(int page, int limit, Long courseId);
    
    ChapterDto getChapterById(long id);
    
}
