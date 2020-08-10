package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.ICategoryRepository;
import kz.academy.kemelacademy.services.ICategoryService;
import kz.academy.kemelacademy.ui.dto.CategoryDto;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.entity.CategoryEntity;
import kz.academy.kemelacademy.ui.entity.CourseEntity;
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

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    
    @Autowired
    private ICategoryRepository categoryRepository;
    
    @Override
    public List<CategoryDto> getCategories(int page, int limit) throws Exception {
        List<CategoryDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(pageable);
        List<CategoryEntity> categories = categoryPage.getContent();
        
        for (CategoryEntity categoryEntity: categories) {
            CategoryDto categoryDto = convertEntityToDto(categoryEntity);
            returnValue.add(categoryDto);
        }
        
        return returnValue;
    }
    
    private CategoryDto convertEntityToDto(CategoryEntity categoryEntity) {
        CategoryDto categoryDto = new CategoryDto();
        for (CourseEntity courseEntity: categoryEntity.getCourses()) {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(courseEntity, courseDto);
            categoryDto.getCourses().add(courseDto);
        }
        BeanUtils.copyProperties(categoryEntity, categoryDto);
        return categoryDto;
    }
    
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) throws Exception {
        CategoryEntity categoryEntity = new CategoryEntity();
        BeanUtils.copyProperties(categoryDto, categoryEntity);
        
        CategoryEntity storedCategory = categoryRepository.save(categoryEntity);
        
        CategoryDto returnVal = new CategoryDto();
        BeanUtils.copyProperties(storedCategory, returnVal);
        
        return returnVal;
    }
    
    @Override
    public CategoryDto getCategoryById(long id) throws Exception {
        CategoryDto returnValue = new CategoryDto();
        
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        CategoryEntity categoryEntity = optional.get();
        
        BeanUtils.copyProperties(categoryEntity, returnValue);
        
        return returnValue;
    }
    
    @Override
    public CategoryDto updateCategory(long id, CategoryDto categoryDto) throws Exception {
        CategoryDto returnValue = new CategoryDto();
        
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        CategoryEntity categoryEntity = optional.get();
        categoryEntity.setNameKz(categoryDto.getNameKz());
        categoryEntity.setNameRu(categoryDto.getNameRu());
        categoryEntity.setNameEn(categoryDto.getNameEn());
        
        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
        BeanUtils.copyProperties(updatedCategory, returnValue);
        
        return returnValue;
    }
    
    @Override
    public void deleteCategory(long id) throws Exception {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        CategoryEntity categoryEntity = optional.get();
        
        categoryRepository.delete(categoryEntity);
    }
    
}
