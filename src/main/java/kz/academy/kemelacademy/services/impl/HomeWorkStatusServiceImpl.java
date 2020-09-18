package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.IHomeWorkStatusRepository;
import kz.academy.kemelacademy.services.IHomeWorkStatusService;
import kz.academy.kemelacademy.ui.dto.HomeWorkStatusDto;
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
public class HomeWorkStatusServiceImpl implements IHomeWorkStatusService {
    
    @Autowired
    IHomeWorkStatusRepository testStatusRepository;
    
    @Override
    public HomeWorkStatusDto createStatusDto(HomeWorkStatusDto statusDto) throws Exception {
        
        HomeWorkStatusEntity homeWorkStatusEntity = new HomeWorkStatusEntity();
        BeanUtils.copyProperties(statusDto, homeWorkStatusEntity);
        
        HomeWorkStatusEntity storedTestStatus = testStatusRepository.save(homeWorkStatusEntity);
        
        HomeWorkStatusDto returnVal = new HomeWorkStatusDto();
        BeanUtils.copyProperties(storedTestStatus, returnVal);
        
        return returnVal;
    }
    
    @Override
    public HomeWorkStatusDto getStatusById(long id) throws Exception {
        
        Optional<HomeWorkStatusEntity> optional = testStatusRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException((ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }
        HomeWorkStatusEntity homeWorkStatusEntity = optional.get();
        HomeWorkStatusDto returnVal = new HomeWorkStatusDto();
        BeanUtils.copyProperties(homeWorkStatusEntity, returnVal);
        
        return returnVal;
    }
    
    @Override
    public List<HomeWorkStatusDto> getStatusDtos(int page, int limit) throws Exception {
        List<HomeWorkStatusDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<HomeWorkStatusEntity> testStatusEntities = testStatusRepository.findAll(pageable);
        List<HomeWorkStatusEntity> testStatuses = testStatusEntities.getContent();
        
        for (HomeWorkStatusEntity homeWorkStatusEntity: testStatuses) {
            HomeWorkStatusDto homeWorkStatusDto = new HomeWorkStatusDto();
            BeanUtils.copyProperties(homeWorkStatusEntity, homeWorkStatusDto);
            returnValue.add(homeWorkStatusDto);
        }
        
        return returnValue;
    }
}
