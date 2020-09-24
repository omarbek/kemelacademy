package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.*;
import kz.academy.kemelacademy.services.IChapterService;
import kz.academy.kemelacademy.services.ICourseService;
import kz.academy.kemelacademy.services.IUserService;
import kz.academy.kemelacademy.ui.dto.ChapterDto;
import kz.academy.kemelacademy.ui.dto.CourseDto;
import kz.academy.kemelacademy.ui.dto.LessonDto;
import kz.academy.kemelacademy.ui.dto.UserDto;
import kz.academy.kemelacademy.ui.entity.*;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.utils.SystemParameterUtils;
import kz.academy.kemelacademy.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-07
 * @project kemelacademy
 */
@Service
public class CourseServiceImpl implements ICourseService {
    
    @Autowired
    private ICourseRepository courseRepository;
    
    @Autowired
    private ICourseStatusRepository courseStatusRepository;
    
    @Autowired
    private ICategoryRepository categoryRepository;
    
    @Autowired
    private ILevelRepository levelRepository;
    
    @Autowired
    private ILanguageRepository languageRepository;
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IUserCourseRepository userCourseRepository;
    
    @Autowired
    private UserUtils userUtils;
    
    @Autowired
    private SystemParameterUtils systemParameterUtils;
    
    @Autowired
    private IFileTypeRepository fileTypeRepository;
    
    @Autowired
    private IChapterService chapterService;
    
    @Override
    public CourseDto createCourse(CourseDto courseDto) throws Exception {
        CourseEntity courseEntity = new CourseEntity();
        convertDtoToEntity(courseDto, courseEntity, false);
        
        CourseEntity storedCourse = courseRepository.save(courseEntity);
        for (UserCourseEntity userCourseEntity: storedCourse.getUsers()) {
            UserCourseId userCourseId = new UserCourseId();
            userCourseId.setUserId(userCourseEntity.getUser().getId());
            userCourseId.setCourseId(userCourseEntity.getCourse().getId());
            userCourseEntity.setUserCourseId(userCourseId);
            userCourseRepository.save(userCourseEntity);
        }
        
        return convertEntityToDto(storedCourse);
    }
    
    private void convertDtoToEntity(CourseDto courseDto, CourseEntity courseEntity, boolean update) {
        if (!update) {
            BeanUtils.copyProperties(courseDto.getAuthor(), courseEntity.getAuthor());
        }
        if (courseDto.getCategory().getId() != null) {
            if (!courseDto.getCategory().getId().equals(courseEntity.getCategory().getId())) {
                CategoryEntity categoryEntity = categoryRepository
                        .findById(courseDto.getCategory().getId()).get();
                courseEntity.setCategory(categoryEntity);
            }
        }
        if (courseDto.getLevel().getId() != null) {
            if (!courseDto.getLevel().getId().equals(courseEntity.getLevel().getId())) {
                LevelEntity levelEntity = levelRepository
                        .findById(courseDto.getLevel().getId()).get();
                courseEntity.setLevel(levelEntity);
            }
        }
        if (courseDto.getLanguage().getId() != null) {
            if (!courseDto.getLanguage().getId().equals(courseEntity.getLanguage().getId())) {
                LanguageEntity languageEntity = languageRepository
                        .findById(courseDto.getLanguage().getId()).get();
                courseEntity.setLanguage(languageEntity);
            }
        }
        if (courseDto.getCourseStatus().getId() != null) {
            if (!courseDto.getCourseStatus().getId().equals(courseEntity.getCourseStatus().getId())) {
                CourseStatusEntity courseStatusEntity = courseStatusRepository
                        .findById(courseDto.getCourseStatus().getId()).get();
                courseEntity.setCourseStatus(courseStatusEntity);
            }
        }
        Set<UserCourseEntity> userCourses = new HashSet<>();
        for (UserDto userDto: courseDto.getPupils()) {
            UserEntity userEntity = userRepository.findByUserId(userDto.getUserId());
            
            UserCourseEntity userCourseEntity = new UserCourseEntity();
            userCourseEntity.setUser(userEntity);
            userCourseEntity.setCourse(courseEntity);
            userCourseEntity.setFinished(false);
            
            userCourses.add(userCourseEntity);
        }
        courseEntity.setUsers(userCourses);
        
        if (update) {
            if (courseDto.getPrice() != null) {
                courseEntity.setPrice(courseDto.getPrice());
            }
            if (courseDto.getName() != null) {
                courseEntity.setName(courseDto.getName());
            }
            if (courseDto.getDescription() != null) {
                courseEntity.setDescription(courseDto.getDescription());
            }
            if (courseDto.getRequirements() != null) {
                courseEntity.setRequirements(courseDto.getRequirements());
            }
            if (courseDto.getLearns() != null) {
                courseEntity.setLearns(courseDto.getLearns());
            }
        } else {
            BeanUtils.copyProperties(courseDto, courseEntity);
        }
    }
    
    private CourseDto convertEntityToDto(CourseEntity storedCourse) {
        CourseDto returnVal = new CourseDto();
        BeanUtils.copyProperties(storedCourse.getAuthor(), returnVal.getAuthor());
        BeanUtils.copyProperties(storedCourse.getCategory(), returnVal.getCategory());
        BeanUtils.copyProperties(storedCourse.getLevel(), returnVal.getLevel());
        BeanUtils.copyProperties(storedCourse.getLanguage(), returnVal.getLanguage());
        BeanUtils.copyProperties(storedCourse.getCourseStatus(), returnVal.getCourseStatus());
        
        Set<UserCourseEntity> users = new HashSet<>();
        try {
            users = storedCourse.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (UserCourseEntity userCourseEntity: users) {
            UserDto userDto = userService.getUserByUserId(userCourseEntity.getUser().getUserId());
            returnVal.getPupils().add(userDto);
        }
        for (ChapterEntity chapterEntity: storedCourse.getChapters()) {
            ChapterDto chapterDto = new ChapterDto();
            for (LessonEntity lessonEntity: chapterEntity.getLessons()) {
                LessonDto lessonDto = new LessonDto();
                BeanUtils.copyProperties(lessonEntity, lessonDto);
                chapterDto.getLessons().add(lessonDto);
            }
            BeanUtils.copyProperties(chapterEntity.getCourse(), chapterDto.getCourseDto());
            BeanUtils.copyProperties(chapterEntity, chapterDto);
            returnVal.getChapters().add(chapterDto);
        }
        if (storedCourse.getCertificate() != null) {
            returnVal.setCertificateName(storedCourse.getCertificate().getName());
        }
        Double ratingSum = 0.0;
        int numberOfUsers = 0;
        for (UserCourseEntity userCourseEntity: storedCourse.getUsers()) {
            if (userCourseEntity.getRating() != null) {
                ratingSum += userCourseEntity.getRating();
                numberOfUsers++;
            }
        }
        returnVal.setRating(ratingSum / numberOfUsers);
        BeanUtils.copyProperties(storedCourse, returnVal);
        return returnVal;
    }
    
    @Override
    public List<CourseDto> getAll(int page, int limit, Long categoryId) throws Exception {
        List<CourseDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<CourseEntity> coursePage = courseRepository.findAll(pageable);
        List<CourseEntity> courses = new ArrayList<>();
        for (CourseEntity courseEntity: coursePage.getContent()) {
            if (categoryId != null) {
                if (categoryId.equals(courseEntity.getCategory().getId())) {
                    courses.add(courseEntity);
                }
            } else {
                courses.add(courseEntity);
            }
        }
        
        for (CourseEntity courseEntity: courses) {
            CourseDto courseDto = convertEntityToDto(courseEntity);
            returnValue.add(courseDto);
        }
        
        return returnValue;
    }
    
    @Override
    public CourseDto getCourseById(long id) throws Exception {
        CourseDto returnValue;
        
        CourseEntity courseEntity = getCourseEntity(id);
        
        returnValue = convertEntityToDto(courseEntity);
        
        return returnValue;
    }
    
    private CourseEntity getCourseEntity(long id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return optional.get();
    }
    
    @Override
    public CourseDto updateCourse(long id, CourseDto courseDto) throws Exception {
        CourseDto returnValue;
        
        CourseEntity courseEntity = getCourseEntity(id);
        convertDtoToEntity(courseDto, courseEntity, true);
        
        CourseEntity updatedCourse = courseRepository.save(courseEntity);
        returnValue = convertEntityToDto(updatedCourse);
        
        return returnValue;
    }
    
    @Override
    public void deleteCourse(long id) throws Exception {
        CourseEntity courseEntity = getCourseEntity(id);
        delete(courseEntity);
    }
    
    @Override
    public void delete(CourseEntity courseEntity) throws Exception {
        courseRepository.delete(courseEntity);
    }
    
    @Override
    public CourseDto uploadFile(Long courseId, MultipartFile file) throws Exception {
        String pathFolder = systemParameterUtils.getPathFolder() + "courses/" + courseId + "/";
        File directory = new File(pathFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filename = pathFolder + new Date().getTime() + "_" + file.getOriginalFilename();
        
        byte[] bytes = file.getBytes();
        Path path = Paths.get(filename);
        Files.write(path, bytes);
        
        CourseEntity courseEntity = getCourseEntity(courseId);
        
        FileEntity fileEntity = new FileEntity();
        Optional<FileTypeEntity> optional = fileTypeRepository.findById(FileTypeEntity.FOR_CERTIFICATE);
        if (!optional.isPresent()) {
            throw new ServiceException((ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }
        FileTypeEntity fileTypeEntity = optional.get();
        fileEntity.setFileType(fileTypeEntity);
        fileEntity.setLesson(null);
        fileEntity.setName(filename);
        
        courseEntity.setCertificate(fileEntity);
        CourseEntity uploadedFileCourseEntity = courseRepository.save(courseEntity);
        
        return convertEntityToDto(uploadedFileCourseEntity);
    }
    
    @Override
    public void finishCourse(Long userId, Long courseId) throws Exception {
        UserCourseId userCourseId = new UserCourseId();
        userCourseId.setUserId(userId);
        userCourseId.setCourseId(courseId);
        Optional<UserCourseEntity> optional = userCourseRepository.findById(userCourseId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserCourseEntity userCourseEntity = optional.get();
        userCourseEntity.setFinished(true);
        userCourseRepository.save(userCourseEntity);
    }
    
    @Override
    public void setRating(Long courseId, Double rating) {
        UserCourseId userCourseId = new UserCourseId();
        userCourseId.setUserId(userUtils.getCurrentUserEntity().getId());
        userCourseId.setCourseId(courseId);
        Optional<UserCourseEntity> optional = userCourseRepository.findById(userCourseId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserCourseEntity userCourseEntity = optional.get();
        userCourseEntity.setRating(rating);
        userCourseRepository.save(userCourseEntity);
    }
    
    @Override
    public List<CourseDto> getMyCourses() {
        List<CourseDto> returnValue = new ArrayList<>();
        
        List<CourseEntity> courses = new ArrayList<>();
        Set<UserCourseEntity> pupils = userUtils.getCurrentUserEntity().getPupils();
        for (UserCourseEntity userCourseEntity: pupils) {
            courses.add(userCourseEntity.getCourse());
        }
        
        for (CourseEntity courseEntity: courses) {
            CourseDto courseDto = convertEntityToDto(courseEntity);
            returnValue.add(courseDto);
        }
        
        return returnValue;
    }
    
    
}
