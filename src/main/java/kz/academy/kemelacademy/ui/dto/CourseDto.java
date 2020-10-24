package kz.academy.kemelacademy.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private List<UserDto> pupils = new ArrayList<>();
    private List<ChapterDto> chapters = new ArrayList<>();
    private String certificateName;
    private Double rating;
    private String imageUrl;
    private boolean accepted;
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        CourseDto courseDto = (CourseDto) o;
        return Objects.equals(id, courseDto.id) &&
                Objects.equals(author, courseDto.author) &&
                Objects.equals(category, courseDto.category) &&
                Objects.equals(level, courseDto.level) &&
                Objects.equals(language, courseDto.language) &&
                Objects.equals(price, courseDto.price) &&
                Objects.equals(name, courseDto.name) &&
                Objects.equals(description, courseDto.description) &&
                Objects.equals(requirements, courseDto.requirements) &&
                Objects.equals(learns, courseDto.learns) &&
                Objects.equals(courseStatus, courseDto.courseStatus) &&
                Objects.equals(pupils, courseDto.pupils) &&
                Objects.equals(chapters, courseDto.chapters) &&
                Objects.equals(certificateName, courseDto.certificateName) &&
                Objects.equals(rating, courseDto.rating) &&
                Objects.equals(imageUrl, courseDto.imageUrl) &&
                Objects.equals(accepted, courseDto.accepted);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, description, requirements, learns, certificateName, rating, imageUrl,
                accepted);
    }
    
}
