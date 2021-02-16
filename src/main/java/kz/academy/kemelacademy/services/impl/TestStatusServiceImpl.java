package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.ITestStatusRepository;
import kz.academy.kemelacademy.services.ITestStatusService;
import kz.academy.kemelacademy.ui.dto.TestStatusDto;
import kz.academy.kemelacademy.ui.entity.TestStatusEntity;
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
public class TestStatusServiceImpl implements ITestStatusService {

    @Autowired
    ITestStatusRepository testStatusRepository;

    @Override
    public TestStatusDto createStatusDto(TestStatusDto statusDto) throws Exception {

        TestStatusEntity testStatusEntity = new TestStatusEntity();
        BeanUtils.copyProperties(statusDto, testStatusEntity);

        TestStatusEntity storedTestStatus = testStatusRepository.save(testStatusEntity);

        TestStatusDto returnVal = new TestStatusDto();
        BeanUtils.copyProperties(storedTestStatus, returnVal);

        return returnVal;
    }

    @Override
    public TestStatusDto getStatusById(long id) throws Exception {

        Optional<TestStatusEntity> optional = testStatusRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException((ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }
        TestStatusEntity testStatusEntity = optional.get();
        TestStatusDto returnVal = new TestStatusDto();
        BeanUtils.copyProperties(testStatusEntity, returnVal);

        return returnVal;
    }

    @Override
    public List<TestStatusDto> getStatusDtos(int page, int limit) throws Exception {
        List<TestStatusDto> returnValue = new ArrayList<>();

        if (page > 0) {
            page -= 1;
        }

        Pageable pageable = PageRequest.of(page, limit);
        Page<TestStatusEntity> testStatusEntities = testStatusRepository.findAll(pageable);
        List<TestStatusEntity> testStatuses = testStatusEntities.getContent();

        for (TestStatusEntity testStatusEntity : testStatuses) {
            TestStatusDto testStatusDto = new TestStatusDto();
            BeanUtils.copyProperties(testStatusEntity, testStatusDto);
            returnValue.add(testStatusDto);
        }

        return returnValue;
    }
}
