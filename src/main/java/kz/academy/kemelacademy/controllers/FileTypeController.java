package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.IFileTypeService;
import kz.academy.kemelacademy.ui.dto.FileTypeDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.ui.model.request.FileTypeRequestModel;
import kz.academy.kemelacademy.ui.model.response.FileTypeRest;
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
@RequestMapping("file_types")
public class FileTypeController {

    @Autowired
    private IFileTypeService fileTypeService;
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @GetMapping
    public List<FileTypeRest> getFileTypes(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<FileTypeRest> returnVal = new ArrayList<>();

        List<FileTypeDto> fileTypeDtos;
        try {
            fileTypeDtos = fileTypeService.getFileTypeDtos(page, limit);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        for (FileTypeDto fileTypeDto: fileTypeDtos) {
            FileTypeRest fileTypeRest = getFileTypeRest(fileTypeDto);
            returnVal.add(fileTypeRest);
        }

        return returnVal;
    }

    private FileTypeRest getFileTypeRest(FileTypeDto fileTypeDto) {
        FileTypeRest fileTypeRest = new FileTypeRest();
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = fileTypeDto.getNameKz();
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = fileTypeDto.getNameRu();
        } else {
            name = fileTypeDto.getNameEn();
        }
        fileTypeRest.setName(name);
        fileTypeRest.setId(fileTypeDto.getId());
        return fileTypeRest;
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
    public FileTypeRest createFileType(@RequestBody FileTypeRequestModel fileTypeRequestModel) {
        FileTypeRest returnValue = new FileTypeRest();

        String[] fields = {fileTypeRequestModel.getNameKz(), fileTypeRequestModel.getNameRu(),
                fileTypeRequestModel.getNameEn()};
        ThrowUtils.throwMissingRequiredFieldException(fields);

        FileTypeDto fileTypeDto = new FileTypeDto();
        BeanUtils.copyProperties(fileTypeRequestModel, fileTypeDto);

        FileTypeDto createdFileType;
        try {
            createdFileType = fileTypeService.createFileType(fileTypeDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        BeanUtils.copyProperties(createdFileType, returnValue);

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
    public FileTypeRest getFiletype(@PathVariable("id") long id) {
        FileTypeDto fileTypeDto;
        try {
            fileTypeDto = fileTypeService.getFileTypeById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }

        return getFileTypeRest(fileTypeDto);
    }

}
