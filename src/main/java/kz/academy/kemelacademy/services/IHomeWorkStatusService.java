package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.HomeWorkStatusDto;

import java.util.List;

public interface IHomeWorkStatusService {
    
    HomeWorkStatusDto createStatusDto(HomeWorkStatusDto statusDto) throws Exception;
    
    HomeWorkStatusDto getStatusById(long id) throws Exception;
    
    List<HomeWorkStatusDto> getStatusDtos(int page, int limit) throws Exception;
    
}
