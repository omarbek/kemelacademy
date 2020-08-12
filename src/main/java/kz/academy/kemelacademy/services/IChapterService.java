package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.ChapterDto;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-12
 * @project kemelacademy
 */
public interface IChapterService {
    
    ChapterDto createChapter(ChapterDto chapterDto) throws Exception;
    
}
