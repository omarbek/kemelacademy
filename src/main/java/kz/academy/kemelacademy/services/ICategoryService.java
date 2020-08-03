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
    
    CategoryDto createCategory(CategoryDto categoryDto) throws Exception;
    
    CategoryDto getCategoryById(long id) throws Exception;
    
    CategoryDto updateCategory(long id, CategoryDto categoryDto) throws Exception;
    
    void deleteCategory(long id);
    
}
