package kz.academy.kemelacademy.ui.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Data
public class CourseDto implements Serializable {
    
    private Long id;
    private UserDto author = new UserDto();
    private CategoryDto category = new CategoryDto();
    private LevelDto level = new LevelDto();
    private LanguageDto language = new LanguageDto();
    private Integer price;
    private String name;
    private String description;
    private String requirements;
    private String learns;
    private CourseStatusDto courseStatus = new CourseStatusDto();
    private Set<UserDto> pupils = new HashSet<>();
    
    @Override
    public String toString() {
        return name;
    }
    
}
