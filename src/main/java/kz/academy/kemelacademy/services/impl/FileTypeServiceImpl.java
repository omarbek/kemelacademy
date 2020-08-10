package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.IFileTypeRepository;
import kz.academy.kemelacademy.services.IFileTypeService;
import kz.academy.kemelacademy.ui.dto.FileTypeDto;

import kz.academy.kemelacademy.ui.entity.FileTypeEntity;

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
public class FileTypeServiceImpl implements IFileTypeService {

    @Autowired
    private IFileTypeRepository fileTypeRepository;

    @Override
    public FileTypeDto createFileType(FileTypeDto fileTypeDto) throws Exception {

        FileTypeEntity fileTypeEntity = new FileTypeEntity();
        BeanUtils.copyProperties(fileTypeDto, fileTypeEntity);

        FileTypeEntity storedFileType = fileTypeRepository.save(fileTypeEntity);

        FileTypeDto returnVal = new FileTypeDto();
        BeanUtils.copyProperties(storedFileType, returnVal);

        return returnVal;
    }

    @Override
    public FileTypeDto getFileTypeById(Long id) throws Exception {
        Optional<FileTypeEntity> optional = fileTypeRepository.findById(id);
        if(!optional.isPresent()){
            throw new ServiceException((ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }
        FileTypeEntity fileTypeEntity = optional.get();
        FileTypeDto returnVal = new FileTypeDto();
        BeanUtils.copyProperties(fileTypeEntity, returnVal);

        return returnVal;
    }

    @Override
    public List<FileTypeDto> getFileTypeDtos(int page, int limit) throws Exception {
        List<FileTypeDto> returnValue = new ArrayList<>();

        if (page > 0) {
            page -= 1;
        }

        Pageable pageable = PageRequest.of(page, limit);
        Page<FileTypeEntity> fileTypePage = fileTypeRepository.findAll(pageable);
        List<FileTypeEntity> fileTypes = fileTypePage.getContent();

        for (FileTypeEntity fileTypeEntity: fileTypes) {
            FileTypeDto fileTypeDto = new FileTypeDto();
            BeanUtils.copyProperties(fileTypeEntity, fileTypeDto);
            returnValue.add(fileTypeDto);
        }

        return returnValue;
    }

}
