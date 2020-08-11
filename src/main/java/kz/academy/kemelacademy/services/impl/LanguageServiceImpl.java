package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.ILanguageRepository;
import kz.academy.kemelacademy.services.ILanguageService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.LanguageDto;
import kz.academy.kemelacademy.ui.entity.CourseEntity;
import kz.academy.kemelacademy.ui.entity.LanguageEntity;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements ILanguageService {
    
    @Autowired
    private ILanguageRepository languageRepository;
    
    @Override
    public List<LanguageDto> getLanguages(int page, int limit) throws Exception {
        List<LanguageDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<LanguageEntity> languagePage = languageRepository.findAll(pageable);
        List<LanguageEntity> languages = languagePage.getContent();
        
        for (LanguageEntity languageEntity: languages) {
            LanguageDto languageDto = convertEntityToDto(languageEntity);
            returnValue.add(languageDto);
        }
        
        return returnValue;
    }
    
    private LanguageDto convertEntityToDto(LanguageEntity languageEntity) {
        LanguageDto languageDto = new LanguageDto();
        for (CourseEntity courseEntity: languageEntity.getCourses()) {
            if (!courseEntity.getDeleted()) {
                CourseDto courseDto = new CourseDto();
                BeanUtils.copyProperties(courseEntity, courseDto);
                languageDto.getCourses().add(courseDto);
            }
        }
        BeanUtils.copyProperties(languageEntity, languageDto);
        return languageDto;
    }
    
    @Override
    public LanguageDto createLanguage(LanguageDto languageDto) throws Exception {
        LanguageEntity languageEntity = new LanguageEntity();
        BeanUtils.copyProperties(languageDto, languageEntity);
        
        LanguageEntity storedLanguage = languageRepository.save(languageEntity);
        
        LanguageDto returnVal = convertEntityToDto(storedLanguage);
        
        return returnVal;
    }
    
    @Override
    public LanguageDto getLanguageById(Long id) throws Exception {
        Optional<LanguageEntity> optional = languageRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException((ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }
        LanguageEntity languageEntity = optional.get();
        LanguageDto returnVal = convertEntityToDto(languageEntity);
        
        return returnVal;
    }
    
}
