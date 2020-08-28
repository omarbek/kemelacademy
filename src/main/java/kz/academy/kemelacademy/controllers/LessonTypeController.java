package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.ILessonTypeService;
import kz.academy.kemelacademy.ui.dto.LessonTypeDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.ui.model.request.LessonTypeRequestModel;
import kz.academy.kemelacademy.ui.model.response.LessonTypeRest;
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
@RequestMapping("lesson_types")
public class LessonTypeController {//

    @Autowired
    private ILessonTypeService lessonTypeService;
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @GetMapping
    public List<LessonTypeRest> getLessonTypes(@RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<LessonTypeRest> returnVal = new ArrayList<>();

        List<LessonTypeDto> lessonTypeDtos;
        try {
            lessonTypeDtos = lessonTypeService.getLessonTypeDtos(page, limit);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        for (LessonTypeDto lessonTypeDto: lessonTypeDtos) {
            LessonTypeRest lessonTypeRest = getLessonTypeRest(lessonTypeDto);
            returnVal.add(lessonTypeRest);
        }

        return returnVal;
    }

    private LessonTypeRest getLessonTypeRest(LessonTypeDto lessonTypeDto) {
        LessonTypeRest lessonTypeRest = new LessonTypeRest();
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = lessonTypeDto.getNameKz();
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = lessonTypeDto.getNameRu();
        } else {
            name = lessonTypeDto.getNameEn();
        }
        lessonTypeRest.setName(name);
        lessonTypeRest.setId(lessonTypeDto.getId());
        return lessonTypeRest;
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
    public LessonTypeRest createLessonType(@RequestBody LessonTypeRequestModel lessonTypeRequestModel) {
        LessonTypeRest returnValue = new LessonTypeRest();

        String[] fields = {lessonTypeRequestModel.getNameKz(), lessonTypeRequestModel.getNameRu(),
                lessonTypeRequestModel.getNameEn()};
        ThrowUtils.throwMissingRequiredFieldException(fields);

        LessonTypeDto lessonTypeDto = new LessonTypeDto();
        BeanUtils.copyProperties(lessonTypeRequestModel, lessonTypeDto);

        LessonTypeDto createdLessonType;
        try {
            createdLessonType = lessonTypeService.createLessonType(lessonTypeDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        BeanUtils.copyProperties(createdLessonType, returnValue);

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
    public LessonTypeRest getLessonType(@PathVariable("id") long id) {
        LessonTypeDto lessonTypeDto;
        try {
            lessonTypeDto = lessonTypeService.getLessonTypeById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }

        return getLessonTypeRest(lessonTypeDto);
    }

}
