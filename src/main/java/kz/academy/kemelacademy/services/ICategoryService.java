package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.CategoryDto;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
public interface ICategoryService {
    
    List<CategoryDto> getCategories(int page, int limit) throws Exception;
    
    CategoryDto createCategory(CategoryDto categoryDto);
    
    CategoryDto getCategoryById(long id);
    
    CategoryDto updateCategory(long id, CategoryDto categoryDto);
    
    void deleteCategory(long id);
    
}
