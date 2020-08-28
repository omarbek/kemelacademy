package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.ILanguageService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.LanguageDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.ui.model.request.LanguageRequestModel;
import kz.academy.kemelacademy.ui.model.response.LanguageRest;
import kz.academy.kemelacademy.utils.LocaleUtils;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("languages")
public class LanguageController {
    
    @Autowired
    private ILanguageService languageService;
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @GetMapping
    public List<LanguageRest> getLanguages(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<LanguageRest> returnVal = new ArrayList<>();
        
        List<LanguageDto> languages;
        try {
            languages = languageService.getLanguages(page, limit);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (LanguageDto languageDto: languages) {
            LanguageRest languageRest = getLanguageRest(languageDto);
            returnVal.add(languageRest);
        }
        
        return returnVal;
    }
    
    private LanguageRest getLanguageRest(LanguageDto languageDto) {
        LanguageRest languageRest = new LanguageRest();
        languageRest.setId(languageDto.getId());
        
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = languageDto.getNameKz();
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = languageDto.getNameRu();
        } else {
            name = languageDto.getNameEn();
        }
        languageRest.setName(name);
        
        for (CourseDto courseDto: languageDto.getCourses()) {
            languageRest.getCourses().add(courseDto.toString());
        }
        
        return languageRest;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "${authorizationHeader.description}",
                    paramType = "header"),
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @PostMapping
    public LanguageRest createLanguage(@RequestBody LanguageRequestModel languageRequestModel) {
        LanguageRest returnValue = new LanguageRest();
        
        String[] fields = {languageRequestModel.getNameKz(), languageRequestModel.getNameRu(),
                languageRequestModel.getNameEn()};
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        LanguageDto languageDto = new LanguageDto();
        BeanUtils.copyProperties(languageRequestModel, languageDto);
        
        LanguageDto createdLanguage;
        try {
            createdLanguage = languageService.createLanguage(languageDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        BeanUtils.copyProperties(createdLanguage, returnValue);
        
        return returnValue;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @GetMapping(path = "/{id}")
    public LanguageRest getLanguage(@PathVariable("id") long id) {
        LanguageDto languageDto;
        try {
            languageDto = languageService.getLanguageById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return getLanguageRest(languageDto);
    }
    
}
