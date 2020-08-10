package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.FileTypeDto;

import java.util.List;


public interface IFileTypeService {

    FileTypeDto createFileType(FileTypeDto fileTypeDto) throws Exception;

    FileTypeDto getFileTypeById(Long id) throws Exception;

    List<FileTypeDto> getFileTypeDtos(int page, int limit) throws Exception;

}
