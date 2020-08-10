package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.LanguageDto;

import java.util.List;

public interface ILanguageService {

    List<LanguageDto> getLanguages(int page, int limit) throws Exception;

    LanguageDto createLanguage(LanguageDto languageDto) throws Exception;

    LanguageDto getLanguageById(Long id) throws Exception;

}
