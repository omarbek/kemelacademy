package kz.academy.kemelacademy.controllers;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.services.ICategoryService;
import kz.academy.kemelacademy.ui.dto.CategoryDto;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.ui.enums.RequestOperationName;
import kz.academy.kemelacademy.ui.enums.RequestOperationStatus;
import kz.academy.kemelacademy.ui.model.request.CategoryRequestModel;
import kz.academy.kemelacademy.ui.model.response.CategoryRest;
import kz.academy.kemelacademy.ui.model.response.OperationStatusModel;
import kz.academy.kemelacademy.utils.LocaleUtils;
import kz.academy.kemelacademy.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
@Slf4j
@RestController
@RequestMapping("categories")
public class CategoryController {
    
    @Autowired
    private ICategoryService categoryService;
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @ApiOperation(value = "The Get Categories Web Service Endpoint",
            notes = "${categoryController.getCategories.apiOperation.notes}")
    @GetMapping
    @Transactional
    public List<CategoryRest> getCategories(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<CategoryRest> returnVal = new ArrayList<>();
        
        List<CategoryDto> categories;
        try {
            categories = categoryService.getCategories(page, limit);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        for (CategoryDto categoryDto: categories) {
            CategoryRest categoryRest = convertDtoToRest(categoryDto);
            returnVal.add(categoryRest);
        }
        
        return returnVal;
    }
    
    private CategoryRest convertDtoToRest(CategoryDto categoryDto) {
        CategoryRest categoryRest = new CategoryRest();
        categoryRest.setId(categoryDto.getId());
        
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = categoryDto.getNameKz();
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = categoryDto.getNameRu();
        } else {
            name = categoryDto.getNameEn();
        }
        categoryRest.setName(name);
        
        for (CourseDto courseDto: categoryDto.getCourses()) {
            categoryRest.getCourses().add(courseDto.toString());
        }
        
        return categoryRest;
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
    public CategoryRest createCategory(@RequestBody CategoryRequestModel categoryRequestModel) {
        CategoryRest returnValue = new CategoryRest();
        
        String[] fields = {categoryRequestModel.getNameKz(), categoryRequestModel.getNameRu(),
                categoryRequestModel.getNameEn()};
        ThrowUtils.throwMissingRequiredFieldException(fields);
        
        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(categoryRequestModel, categoryDto);
        
        CategoryDto createdCategory;
        try {
            createdCategory = categoryService.createCategory(categoryDto);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        BeanUtils.copyProperties(createdCategory, returnValue);
        
        return returnValue;
    }
    
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "accept-language",
                    value = "${accept.language}",
                    paramType = "header"
            )
    })
    @Transactional
    @GetMapping(path = "/{id}")
    public CategoryRest getCategory(@PathVariable("id") long id) {
        CategoryDto categoryDto;
        try {
            categoryDto = categoryService.getCategoryById(id);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        return convertDtoToRest(categoryDto);
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
    @Transactional
    @PutMapping(path = "/{id}")
    public CategoryRest updateCategory(@PathVariable("id") long id,
                                       @RequestBody CategoryRequestModel categoryRequestModel) {
        CategoryRest returnValue;
        
        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(categoryRequestModel, categoryDto);
        
        CategoryDto updatedCategory;
        try {
            updatedCategory = categoryService.updateCategory(id, categoryDto);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        returnValue = convertDtoToRest(updatedCategory);
        
        return returnValue;
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
    @Transactional
    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteCategory(@PathVariable("id") long id) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(RequestOperationName.DELETE.name());
        
        try {
            categoryService.deleteCategory(id);
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        
        return operationStatusModel;
    }
    
}
