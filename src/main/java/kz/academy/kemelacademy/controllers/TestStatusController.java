package kz.academy.kemelacademy.controllers;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.ITestStatusService;
import kz.academy.kemelacademy.ui.dto.TestStatusDto;
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
    private ITestStatusService testStatusService;

    @GetMapping
    public List<TestStatusRest> getStatusDtos(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<TestStatusRest> returnVal = new ArrayList<>();

        List<TestStatusDto> testStatusDtos;
        try {
            testStatusDtos = testStatusService.getStatusDtos(page, limit);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        for (TestStatusDto testStatusDto: testStatusDtos) {
            TestStatusRest testStatusRest = getTestStatusRest(testStatusDto);
            returnVal.add(testStatusRest);
        }

        return returnVal;
    }

    private TestStatusRest getTestStatusRest(TestStatusDto testStatusDto) {
        TestStatusRest testStatusRest = new TestStatusRest();
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = testStatusDto.getNameKz();
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = testStatusDto.getNameRu();
        } else {
            name = testStatusDto.getNameEn();
        }
        testStatusRest.setName(name);
        testStatusRest.setId(testStatusDto.getId());
        return testStatusRest;
    }

    @PostMapping
    public TestStatusRest createLessonType(@RequestBody TestStatusRequestModel testStatusRequestModel) {
        TestStatusRest returnValue = new TestStatusRest();

        String[] fields = {testStatusRequestModel.getNameKz(), testStatusRequestModel.getNameRu(),
                testStatusRequestModel.getNameEn()};
        ThrowUtils.throwMissingRequiredFieldException(fields);

        TestStatusDto testStatusDto = new TestStatusDto();
        BeanUtils.copyProperties(testStatusRequestModel, testStatusDto);

        TestStatusDto createdTestStatus;
        try {
            createdTestStatus = testStatusService.createStatusDto(testStatusDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        BeanUtils.copyProperties(createdTestStatus, returnValue);

        return returnValue;
    }

    @GetMapping(path = "/{id}")
    public TestStatusRest getStatusType(@PathVariable("id") long id) {
        TestStatusDto testStatusDto;
        try {
            testStatusDto = testStatusService.getStatusById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }

        return getTestStatusRest(testStatusDto);
    }

}
