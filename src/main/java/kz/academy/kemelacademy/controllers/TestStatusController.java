package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.IHomeWorkStatusService;
import kz.academy.kemelacademy.ui.dto.HomeWorkStatusDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.ui.model.request.TestStatusRequestModel;
import kz.academy.kemelacademy.ui.model.response.TestStatusRest;
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
@RequestMapping("test_statuses")
public class TestStatusController {
    
    @Autowired
    private IHomeWorkStatusService testStatusService;
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @GetMapping
    public List<TestStatusRest> getStatusDtos(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<TestStatusRest> returnVal = new ArrayList<>();
        
        List<HomeWorkStatusDto> homeWorkStatusDtos;
        try {
            homeWorkStatusDtos = testStatusService.getStatusDtos(page, limit);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        for (HomeWorkStatusDto homeWorkStatusDto: homeWorkStatusDtos) {
            TestStatusRest testStatusRest = getTestStatusRest(homeWorkStatusDto);
            returnVal.add(testStatusRest);
        }
        
        return returnVal;
    }
    
    private TestStatusRest getTestStatusRest(HomeWorkStatusDto homeWorkStatusDto) {
        TestStatusRest testStatusRest = new TestStatusRest();
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = homeWorkStatusDto.getNameKz();
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = homeWorkStatusDto.getNameRu();
        } else {
            name = homeWorkStatusDto.getNameEn();
        }
        testStatusRest.setName(name);
        testStatusRest.setId(homeWorkStatusDto.getId());
        return testStatusRest;
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
    public TestStatusRest createLessonType(@RequestBody TestStatusRequestModel testStatusRequestModel) {
        TestStatusRest returnValue = new TestStatusRest();
        
        String[] fields = {testStatusRequestModel.getNameKz(), testStatusRequestModel.getNameRu(),
                testStatusRequestModel.getNameEn()};
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        HomeWorkStatusDto homeWorkStatusDto = new HomeWorkStatusDto();
        BeanUtils.copyProperties(testStatusRequestModel, homeWorkStatusDto);
        
        HomeWorkStatusDto createdTestStatus;
        try {
            createdTestStatus = testStatusService.createStatusDto(homeWorkStatusDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        BeanUtils.copyProperties(createdTestStatus, returnValue);
        
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
    public TestStatusRest getStatusType(@PathVariable("id") long id) {
        HomeWorkStatusDto homeWorkStatusDto;
        try {
            homeWorkStatusDto = testStatusService.getStatusById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        
        return getTestStatusRest(homeWorkStatusDto);
    }
    
}
