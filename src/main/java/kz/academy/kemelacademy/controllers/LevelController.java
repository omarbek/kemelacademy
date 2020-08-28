package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.ILevelService;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.LevelDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.ui.model.response.LevelRest;
import kz.academy.kemelacademy.utils.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-04
 * @project kemelacademy
 */
@RestController
@RequestMapping("levels")
public class LevelController {
    
    @Autowired
    private ILevelService levelService;
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @GetMapping
    @Transactional
    public List<LevelRest> getRoles() {
        List<LevelRest> returnVal = new ArrayList<>();
        
        List<LevelDto> levels;
        try {
            levels = levelService.getAll();
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (LevelDto levelDto: levels) {
            LevelRest levelRest = getLevelRest(levelDto);
            returnVal.add(levelRest);
        }
        
        return returnVal;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @GetMapping(path = "/{id}")
    @Transactional
    public LevelRest getLevel(@PathVariable("id") long id) {
        LevelDto levelDto;
        try {
            levelDto = levelService.getLevelById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return getLevelRest(levelDto);
    }
    
    private LevelRest getLevelRest(LevelDto levelDto) {
        LevelRest levelRest = new LevelRest();
        levelRest.setId(levelDto.getId());
        
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = levelDto.getNameKz();
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = levelDto.getNameRu();
        } else {
            name = levelDto.getNameEn();
        }
        levelRest.setName(name);
        
        for (CourseDto courseDto: levelDto.getCourses()) {
            levelRest.getCourses().add(courseDto.toString());
        }
        
        return levelRest;
    }
    
}
