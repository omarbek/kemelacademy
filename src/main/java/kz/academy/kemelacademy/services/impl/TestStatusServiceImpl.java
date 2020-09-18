package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.ITestStatusRepository;
import kz.academy.kemelacademy.services.ITestStatusService;
import kz.academy.kemelacademy.ui.dto.TestStatusDto;
import kz.academy.kemelacademy.ui.entity.HomeWorkStatusEntity;
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
        
        HomeWorkStatusEntity homeWorkStatusEntity = new HomeWorkStatusEntity();
        BeanUtils.copyProperties(statusDto, homeWorkStatusEntity);
        
        HomeWorkStatusEntity storedTestStatus = testStatusRepository.save(homeWorkStatusEntity);
        
        TestStatusDto returnVal = new TestStatusDto();
        BeanUtils.copyProperties(storedTestStatus, returnVal);
        
        return returnVal;
    }
    
    @Override
    public TestStatusDto getStatusById(long id) throws Exception {
        
        Optional<HomeWorkStatusEntity> optional = testStatusRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException((ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }
        HomeWorkStatusEntity homeWorkStatusEntity = optional.get();
        TestStatusDto returnVal = new TestStatusDto();
        BeanUtils.copyProperties(homeWorkStatusEntity, returnVal);
        
        return returnVal;
    }
    
    @Override
    public List<TestStatusDto> getStatusDtos(int page, int limit) throws Exception {
        List<TestStatusDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<HomeWorkStatusEntity> testStatusEntities = testStatusRepository.findAll(pageable);
        List<HomeWorkStatusEntity> testStatuses = testStatusEntities.getContent();
        
        for (HomeWorkStatusEntity homeWorkStatusEntity: testStatuses) {
            TestStatusDto testStatusDto = new TestStatusDto();
            BeanUtils.copyProperties(homeWorkStatusEntity, testStatusDto);
            returnValue.add(testStatusDto);
        }
        
        return returnValue;
    }
}
