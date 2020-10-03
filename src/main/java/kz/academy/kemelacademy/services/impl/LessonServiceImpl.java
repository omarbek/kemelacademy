package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.*;
import kz.academy.kemelacademy.services.IFileTypeService;
import kz.academy.kemelacademy.services.ILessonService;
import kz.academy.kemelacademy.ui.dto.FileDto;
import kz.academy.kemelacademy.ui.dto.FileTypeDto;
import kz.academy.kemelacademy.ui.dto.LessonDto;
import kz.academy.kemelacademy.ui.dto.UserHomeWorkDto;
import kz.academy.kemelacademy.ui.entity.*;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import kz.academy.kemelacademy.ui.model.request.VideoRequestModel;
import kz.academy.kemelacademy.ui.model.response.LessonRest;
import kz.academy.kemelacademy.utils.SystemParameterUtils;
import kz.academy.kemelacademy.utils.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
@Service
public class LessonServiceImpl implements ILessonService {
    
    @Autowired
    private ILessonRepository lessonRepository;
    
    @Autowired
    private IFileTypeService fileTypeService;
    
    @Autowired
    private IVideoRepository videoRepository;
    
    @Autowired
    private IFileRepository fileRepository;
    
    @Autowired
    private IHomeWorkRepository homeWorkRepository;
    
    @Autowired
    private IChapterRepository chapterRepository;
    
    @Autowired
    private IHomeWorkStatusRepository testStatusRepository;
    
    @Autowired
    private IUserHomeWorkRepository userHomeWorkRepository;
    
    @Autowired
    private IFileTypeRepository fileTypeRepository;
    
    @Autowired
    private UserUtils userUtils;
    
    @Autowired
    private SystemParameterUtils systemParameterUtils;
    
    @Override
    public LessonDto create(LessonDto lessonDto) throws Exception {
        LessonEntity lessonEntity = new LessonEntity();
        
        convertDtoToEntity(lessonDto, lessonEntity, false);
        
        LessonEntity savedLesson = lessonRepository.save(lessonEntity);
        
        return convertEntityToDto(savedLesson);
    }
    
    @Override
    public LessonDto getLessonById(Long lessonId) {
        LessonDto returnValue;
        
        LessonEntity lessonEntity = getLessonEntityById(lessonId);
        
        returnValue = convertEntityToDto(lessonEntity);
        
        return returnValue;
    }
    
    private LessonEntity getLessonEntityById(Long lessonId) {
        Optional<LessonEntity> optional = lessonRepository.findById(lessonId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return optional.get();
    }
    
    @Override
    public LessonDto uploadFile(Long lessonId, MultipartFile file) throws Exception {
        String pathFolder = systemParameterUtils.getPathFolder() + userUtils.getCurrentUserEntity().getUserId() + "/"
                + "lesssons/" + lessonId + "/";
        String filename = file.getOriginalFilename();
        String url = pathFolder + new Date().getTime() + "_" + filename;
        
        byte[] bytes = file.getBytes();
        Path path = Paths.get(url);
        Path parentDir = path.getParent();
        if (!Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
        try {
            Files.write(path, bytes);
        } catch (Exception e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
        }
        
        FileEntity fileEntity;
        
        LessonEntity lessonEntity = getLessonEntityById(lessonId);
        if (lessonEntity.getFile() != null) {
            fileEntity = lessonEntity.getFile();
        } else {
            fileEntity = new FileEntity();
            fileEntity.setLesson(lessonEntity);
            FileTypeDto fileTypeById;
            try {
                fileTypeById = fileTypeService.getFileTypeById(FileTypeEntity.FOR_DOWNLOAD);
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
            }
            BeanUtils.copyProperties(fileTypeById, fileEntity.getFileType());
        }
        
        fileEntity.setUrl(url);
        fileEntity.setName(filename);
        fileRepository.save(fileEntity);
        
        lessonEntity.setFile(fileEntity);
        LessonEntity uploadedFileLessonEntity = lessonRepository.save(lessonEntity);
        
        return convertEntityToDto(uploadedFileLessonEntity);
    }
    
    @Override
    public LessonDto uploadVideo(Long lessonId, MultipartFile file) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new FileSystemResource(convert(file)));
        
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        String ip = "http://195.210.47.99:80/api/uploadVideo";//todo edit
        ResponseEntity<String> response = restTemplate.postForEntity(ip, request, String.class);
        String url = response.getBody().substring(response.getBody().indexOf("Your video link is: ") + 20,
                response.getBody().indexOf("success") - 3);
        url = url.replace("\\", "");
        
        VideoEntity videoEntity;
        
        LessonEntity lessonEntity = getLessonEntityById(lessonId);
        if (lessonEntity.getVideo() != null) {
            videoEntity = lessonEntity.getVideo();
        } else {
            videoEntity = new VideoEntity();
            videoEntity.setLesson(lessonEntity);
        }
        
        videoEntity.setUrl(url);
        videoEntity.setDuration(0);
        videoRepository.save(videoEntity);
        
        lessonEntity.setVideo(videoEntity);
        LessonEntity uploadedFileLessonEntity = lessonRepository.save(lessonEntity);
        
        return convertEntityToDto(uploadedFileLessonEntity);
    }
    
    private static File convert(MultipartFile file) {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new ServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
        }
        
        return convFile;
    }
    
    @Override
    public List<LessonDto> getAll(int page, int limit, Long chapterId, Long courseId) throws Exception {
        List<LessonDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<LessonEntity> lessonEntityPage;
        if (chapterId != null) {
            lessonEntityPage = lessonRepository.findAllByOrderByLessonNoAsc(pageable, courseId, chapterId);
        } else {
            lessonEntityPage = lessonRepository.findAllByOrderByLessonNoAsc(pageable, courseId);
        }
        List<LessonEntity> lessonEntities = lessonEntityPage.getContent();
        
        for (LessonEntity lessonEntity: lessonEntities) {
            LessonDto lessonDto = convertEntityToDto(lessonEntity);
            returnValue.add(lessonDto);
        }
        
        return returnValue;
    }
    
    @Override
    public LessonDto update(long lessonId, LessonDto dto) throws Exception {
        LessonDto returnValue;
        
        Optional<LessonEntity> optional = lessonRepository.findById(lessonId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        LessonEntity entity = optional.get();
        convertDtoToEntity(dto, entity, true);
        
        LessonEntity updatedEntity = lessonRepository.save(entity);
        returnValue = convertEntityToDto(updatedEntity);
        
        return returnValue;
    }
    
    @Override
    public void delete(long id) throws Exception {
        Optional<LessonEntity> optional = lessonRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        LessonEntity entity = optional.get();
        delete(entity);
    }
    
    @Override
    public void delete(LessonEntity lessonEntity) {
        lessonRepository.delete(lessonEntity);
    }
    
    @Override
    public UserHomeWorkDto createHomeWork(Long lessonId) throws Exception {
        Optional<LessonEntity> optional = lessonRepository.findById(lessonId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        LessonEntity lessonEntity = optional.get();
        
        UserHomeWorkEntity userHomeWork = new UserHomeWorkEntity();
        userHomeWork.setUser(userUtils.getCurrentUserEntity());
        userHomeWork.setHomeWork(lessonEntity.getHomeWork());
        userHomeWork.setFile(null);
        
        Optional<HomeWorkStatusEntity> statusOptional = testStatusRepository.findById(HomeWorkStatusEntity.NOT_OPEN);
        if (!statusOptional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        HomeWorkStatusEntity homeWorkStatusEntity = statusOptional.get();
        userHomeWork.setHomeWorkStatus(homeWorkStatusEntity);
        
        userHomeWorkRepository.save(userHomeWork);
        
        UserHomeWorkDto ret = new UserHomeWorkDto();
        BeanUtils.copyProperties(userHomeWork.getUser(), ret.getUser());
        BeanUtils.copyProperties(userHomeWork.getHomeWorkStatus(), ret.getHomeWorkStatus());
        BeanUtils.copyProperties(userHomeWork.getHomeWork().getLesson(), ret.getHomeWork());
        BeanUtils.copyProperties(userHomeWork, ret);
        
        return ret;
    }
    
    @Override
    public UserHomeWorkDto uploadHomeWork(Long userHomeWorkId, MultipartFile file) throws Exception {
        String pathFolder = systemParameterUtils.getPathFolder() + userUtils.getCurrentUserEntity().getUserId() + "/"
                + "homework/" + userHomeWorkId + "/";
        File directory = new File(pathFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filename = pathFolder + new Date().getTime() + "_" + file.getOriginalFilename();
        
        byte[] bytes = file.getBytes();
        Path path = Paths.get(filename);
        Files.write(path, bytes);
        
        Optional<UserHomeWorkEntity> optional = userHomeWorkRepository.findById(userHomeWorkId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserHomeWorkEntity entity = optional.get();
        
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(filename);
        fileEntity.setLesson(null);
        
        Optional<FileTypeEntity> entityOptional = fileTypeRepository.findById(FileTypeEntity.FOR_SEND_HOMEWORK);
        if (!entityOptional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        FileTypeEntity fileTypeEntity = entityOptional.get();
        fileEntity.setFileType(fileTypeEntity);
        
        fileRepository.save(fileEntity);
        
        entity.setFile(fileEntity);
        userHomeWorkRepository.save(entity);
        
        UserHomeWorkDto ret = new UserHomeWorkDto();
        BeanUtils.copyProperties(entity.getUser(), ret.getUser());
        BeanUtils.copyProperties(entity.getHomeWorkStatus(), ret.getHomeWorkStatus());
        BeanUtils.copyProperties(entity.getHomeWork().getLesson(), ret.getHomeWork());
        
        FileDto fileDto = new FileDto();
        fileDto.setName(entity.getFile().getName());
        ret.setFile(fileDto);
        
        BeanUtils.copyProperties(entity, ret);
        
        return ret;
    }
    
    @Override
    public void changeStatus(Long userHomeWorkId, Long statusId) throws Exception {
        Optional<UserHomeWorkEntity> entityOptional = userHomeWorkRepository.findById(userHomeWorkId);
        if (!entityOptional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserHomeWorkEntity userHomeWorkEntity = entityOptional.get();
        
        Optional<HomeWorkStatusEntity> optional = testStatusRepository.findById(statusId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        HomeWorkStatusEntity homeWorkStatusEntity = optional.get();
        userHomeWorkEntity.setHomeWorkStatus(homeWorkStatusEntity);
        
        userHomeWorkRepository.save(userHomeWorkEntity);
    }
    
    @Override
    public void setGrade(Long userTestId, Integer grade, String comment) throws Exception {
        Optional<UserHomeWorkEntity> entityOptional = userHomeWorkRepository.findById(userTestId);
        if (!entityOptional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserHomeWorkEntity userHomeWorkEntity = entityOptional.get();
        userHomeWorkEntity.setGrade(grade);
        userHomeWorkEntity.setComment(comment);
        userHomeWorkRepository.save(userHomeWorkEntity);
    }
    
    @Override
    public LessonDto createVideo(VideoRequestModel videoRequestModel) throws Exception {
        VideoEntity videoEntity;
        
        LessonEntity lessonEntity = getLessonEntityById(videoRequestModel.getLessonId());
        if (lessonEntity.getVideo() != null) {
            videoEntity = lessonEntity.getVideo();
        } else {
            videoEntity = new VideoEntity();
            videoEntity.setLesson(lessonEntity);
        }
        
        videoEntity.setAlwaysOpen(videoRequestModel.isAlwaysOpen());
        videoEntity.setUrl(videoRequestModel.getUrl());
        videoEntity.setDuration(videoRequestModel.getDuration());
        
        videoRepository.save(videoEntity);
        
        lessonEntity.setVideo(videoEntity);
        
        LessonEntity savedLessonEntity = lessonRepository.save(lessonEntity);
        
        return convertEntityToDto(savedLessonEntity);
    }
    
    @Override
    public LessonDto createHomeWorkLesson(Long lessonId, String description) throws Exception {
        LessonEntity lessonEntity = getLessonEntityById(lessonId);
        HomeWorkEntity homeWorkEntity;
        if (lessonEntity.getHomeWork() != null) {
            homeWorkEntity = lessonEntity.getHomeWork();
        } else {
            homeWorkEntity = new HomeWorkEntity();
            homeWorkEntity.setLesson(lessonEntity);
        }
        homeWorkEntity.setDescription(description);
        homeWorkRepository.save(homeWorkEntity);
        lessonEntity.setHomeWork(homeWorkEntity);
        
        LessonEntity savedLessonEntity = lessonRepository.save(lessonEntity);
        
        return convertEntityToDto(savedLessonEntity);
    }
    
    @Override
    public LessonRest convertDtoToRest(LessonDto dto) {
        LessonRest ret = new LessonRest();
        
        ret.setChapter(dto.getChapterDto().toString());
        BeanUtils.copyProperties(dto, ret);
        
        return ret;
    }
    
    private LessonDto convertEntityToDto(LessonEntity savedLesson) {
        LessonDto ret = new LessonDto();
        
        BeanUtils.copyProperties(savedLesson.getChapter(), ret.getChapterDto());
        BeanUtils.copyProperties(savedLesson, ret);
        if (savedLesson.getVideo() != null) {
            ret.setAlwaysOpen(savedLesson.getVideo().isAlwaysOpen());
            ret.setUrl(savedLesson.getVideo().getUrl());
            ret.setDuration(savedLesson.getVideo().getDuration());
        }
        if (savedLesson.getFile() != null) {
            ret.setFileName(savedLesson.getFile().getName());
            ret.setFileUrl(savedLesson.getFile().getUrl());
        }
        if (savedLesson.getHomeWork() != null) {
            ret.setDescription(savedLesson.getHomeWork().getDescription());
        }
        
        return ret;
    }
    
    private void convertDtoToEntity(LessonDto lessonDto, LessonEntity lessonEntity, boolean update) {
        if (!update) {//create
            BeanUtils.copyProperties(lessonDto.getChapterDto(), lessonEntity.getChapter());
            BeanUtils.copyProperties(lessonDto, lessonEntity);
        } else {//update
            Long chapterId = lessonDto.getChapterDto().getId();
            if (chapterId != null) {
                if (!chapterId.equals(lessonEntity.getChapter().getId())) {
                    Optional<ChapterEntity> optional = chapterRepository.findById(chapterId);
                    ChapterEntity chapterEntity = optional.get();
                    lessonEntity.setChapter(chapterEntity);
                }
            }
            if (lessonDto.getLessonNo() != null) {
                lessonEntity.setLessonNo(lessonDto.getLessonNo());
            }
            if (lessonDto.getName() != null) {
                lessonEntity.setName(lessonDto.getName());
            }
        }
    }
    
}
