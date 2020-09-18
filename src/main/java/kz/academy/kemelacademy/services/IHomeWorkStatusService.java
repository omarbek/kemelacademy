package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.TestStatusDto;

import java.util.List;

public interface IHomeWorkStatusService {
    
    TestStatusDto createStatusDto(TestStatusDto statusDto) throws Exception;
    
    TestStatusDto getStatusById(long id) throws Exception;
    
    List<TestStatusDto> getStatusDtos(int page, int limit) throws Exception;
    
}
