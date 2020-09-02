package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.ICourseStatusService;
import kz.academy.kemelacademy.ui.dto.CourseStatusDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.ui.model.response.CourseStatusRest;
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
 * on 2020-09-03
 * @project kemelacademy
 */
@RestController
@RequestMapping("course_statuses")
public class CourseStatusController {
    
    @Autowired
    private ICourseStatusService courseStatusService;
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @GetMapping
    @Transactional
    public List<CourseStatusRest> getAll() {
        List<CourseStatusRest> returnVal = new ArrayList<>();
        
        List<CourseStatusDto> dtos;
        try {
            dtos = courseStatusService.getAll();
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (CourseStatusDto courseStatusDto: dtos) {
            CourseStatusRest courseStatusRest = getCourseStatusRest(courseStatusDto);
            
            returnVal.add(courseStatusRest);
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
    public CourseStatusRest getCourseStatus(@PathVariable("id") long id) {
        CourseStatusDto courseStatusDto;
        try {
            courseStatusDto = courseStatusService.getEntityById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return getCourseStatusRest(courseStatusDto);
    }
    
    private CourseStatusRest getCourseStatusRest(CourseStatusDto courseStatusDto) {
        CourseStatusRest courseStatusRest = new CourseStatusRest();
        courseStatusRest.setId(courseStatusDto.getId());
        
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = courseStatusDto.getNameKz();
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = courseStatusDto.getNameRu();
        } else {
            name = courseStatusDto.getNameEn();
        }
        courseStatusRest.setName(name);
        return courseStatusRest;
    }
    
}
