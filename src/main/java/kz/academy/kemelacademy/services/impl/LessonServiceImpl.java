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
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-18
 * @project kemelacademy
 */
@Slf4j
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
    public LessonDto uploadVideo(Long lessonId, byte[] file) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer unmkhw1a3Dwi7QWaibuG9b");
        headers.add("X-Video-Title", "New video");
        headers.setAccept(Arrays.asList(MediaType.ALL));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        HttpEntity<byte[]> entity = new HttpEntity<>(file, headers);
        RestTemplate restTemplate = new RestTemplate();
        String ip = "https://uploader.kinescope.io/video";
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(ip, entity, String.class);
        } catch (Exception e) {
            System.out.println(response);
            throw new Exception();
        }
        //                String jsonString = "{\n" +
        //                        "\t\"data\": {\n" +
        //                        "\t\t\"id\": \"qnPkAnqg8aNGiPj1qfrjDi\",\n" +
        //                        "\t\t\"workspace_id\": \"7QMYPMq8GhfH9UbyE1W34V\",\n" +
        //                        "\t\t\"storage_id\": \"0bq5x26sN9fGuUWa1PM6Pn\",\n" +
        //                        "\t\t\"project_id\": \"8wGyYGbSdtbQtcbRnY6bcC\",\n" +
        //                        "\t\t\"folder_id\": null,\n" +
        //                        "\t\t\"version\": 1,\n" +
        //                        "\t\t\"status\": \"uploading\",\n" +
        //                        "\t\t\"title\": \"New video\",\n" +
        //                        "\t\t\"description\": \"\",\n" +
        //                        "\t\t\"privacy_type\": \"anywhere\",\n" +
        //                        "\t\t\"privacy_domains\": [],\n" +
        //                        "\t\t\"additional_materials_enabled\": false,\n" +
        //                        "\t\t\"subtitles_enabled\": false,\n" +
        //                        "\t\t\"created_at\": \"2020-12-14T13:48:27.134118Z\",\n" +
        //                        "\t\t\"short_id\": 199731197,\n" +
        //                        "\t\t\"play_link\": \"\",\n" +
        //                        "\t\t\"embed_link\": \"\"\n" +
        //                        "\t}\n" +
        //                        "}\n";
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.getBody());
        String videoId = ((JSONObject) json.get("data")).get("id").toString();
        String status = ((JSONObject) json.get("data")).get("status").toString();
        String createdAtStr = ((JSONObject) json.get("data")).get("created_at").toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX");
        Date createdAt = formatter.parse(createdAtStr);
        
        VideoEntity videoEntity;
        
        LessonEntity lessonEntity = getLessonEntityById(lessonId);
        if (lessonEntity.getVideo() != null) {
            videoEntity = lessonEntity.getVideo();
        } else {
            videoEntity = new VideoEntity();
            videoEntity.setLesson(lessonEntity);
        }
        
        videoEntity.setVideoId(videoId);
        videoEntity.setFinished("done".equals(status));
        videoEntity.setCreatedAt(createdAt);
        videoEntity.setCreatedDate(new Date());
        videoRepository.save(videoEntity);
        
        lessonEntity.setVideo(videoEntity);
        LessonEntity uploadedFileLessonEntity = lessonRepository.save(lessonEntity);
        
        return convertEntityToDto(uploadedFileLessonEntity);
    }
    
    @Override
    public List<UserHomeWorkDto> getHomeWorks(int page, int limit, Long lessonId) {
        List<UserHomeWorkDto> returnValue = new ArrayList<>();
        
        if (page > 0) {
            page -= 1;
        }
        
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserHomeWorkEntity> userHomeWorkEntityPage = homeWorkRepository.findAllByLessonId(pageable, lessonId);
        List<UserHomeWorkEntity> userHomeWorkEntities = userHomeWorkEntityPage.getContent();
        
        for (UserHomeWorkEntity entity: userHomeWorkEntities) {
            UserHomeWorkDto dto = new UserHomeWorkDto();
            BeanUtils.copyProperties(entity.getUser(), dto.getUser());
            BeanUtils.copyProperties(entity.getHomeWorkStatus(), dto.getHomeWorkStatus());
            if (entity.getFile() != null) {
                BeanUtils.copyProperties(entity.getFile(), dto.getFile());
            }
            BeanUtils.copyProperties(entity.getHomeWork().getLesson(), dto.getHomeWork());
            BeanUtils.copyProperties(entity, dto);
            returnValue.add(dto);
        }
        
        return returnValue;
    }
    
    @Override
    public void checkLessonId(Long lessonId) {
        Optional<LessonEntity> optional = lessonRepository.findById(lessonId);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        LessonEntity lesson = optional.get();
        UserEntity lessonAuthor = lesson.getChapter().getCourse().getAuthor();
        UserEntity currentUser = userUtils.getCurrentUserEntity();
        if (!lessonAuthor.getId().equals(currentUser.getId())) {
            throw new ServiceException(ErrorMessages.THIS_IS_NOT_YOUR_COURSE.getErrorMessage());
        }
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
            VideoEntity video = savedLesson.getVideo();
            try {
                updateVideo(video);
            } catch (Exception e) {
                throw new ServiceException(ErrorMessages.CAN_NOT_UPDATE_VIDEO.getErrorMessage(), e);
            }
            BeanUtils.copyProperties(savedLesson.getVideo(), ret);
        }
        if (savedLesson.getFile() != null) {
            ret.setFileName(savedLesson.getFile().getName());
            ret.setFileUrl(savedLesson.getFile().getUrl());
        }
        if (savedLesson.getHomeWork() != null) {
            ret.setDescription(savedLesson.getHomeWork().getDescription());
        }
        ret.setId(savedLesson.getId());
        return ret;
    }
    
    private void updateVideo(VideoEntity video) throws Exception {
        URL url = new URL("https://api.kinescope.io/v1/videos/" + video.getVideoId());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "Bearer unmkhw1a3Dwi7QWaibuG9b");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            
            StringBuffer response = new StringBuffer();
            while ((output = in.readLine()) != null) {
                response.append(output);
            }
            //        String ret = "{\t\"data\": {\t\t\"id\": \"vPMUAwFJCALPiL5ELVKWsM\"," +
            //                "\t\t\"workspace_id\": \"7QMYPMq8GhfH9UbyE1W34V\",\t\t\"project_id\": \"8wGyYGbSdtbQtcbRnY6bcC\"," +
            //                "\t\t\"folder_id\": null,\t\t\"storage_id\": \"0bq5x26sN9fGuUWa1PM6Pn\",\t\t\"short_id\": 199731261," +
            //                "\t\t\"version\": 0,\t\t\"title\": \"New video\",\t\t\"description\": \"\",\t\t\"status\": \"error\"," +
            //                "\t\t\"progress\": 0,\t\t\"duration\": 0,\t\t\"assets\": [],\t\t\"chapters\": {\t\t\t\"items\": []," +
            //                "\t\t\t\"enabled\": false,\t\t\t\"show_on_load\": false\t\t},\t\t\"privacy_type\": \"anywhere\"," +
            //                "\t\t\"privacy_domains\": [],\t\t\"poster\": null,\t\t\"additional_materials\": []," +
            //                "\t\t\"additional_materials_enabled\": false,\t\t\"play_link\": \"https://kinescope.io/199731261\"," +
            //                "\t\t\"embed_link\": \"https://kinescope.io/embed/199731261\"," +
            //                "\t\t\"created_at\": \"2020-12-14T17:47:22.206692Z\",\t\t\"updated_at\": null," +
            //                "\t\t\"deleted_at\": null,\t\t\"subtitles\": [],\t\t\"subtitles_enabled\": false\t}}";
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());
            
            String status = ((JSONObject) json.get("data")).get("status").toString();
            video.setFinished("done".equals(status));
            
            int progress = Integer.parseInt(((JSONObject) json.get("data")).get("progress").toString());
            video.setProgress(progress);
            
            Double duration = Double.parseDouble(((JSONObject) json.get("data")).get("duration").toString());
            video.setDuration(duration);
            
            String videoUrl = ((JSONObject) json.get("data")).get("embed_link").toString();
            video.setUrl(videoUrl);
            
            videoRepository.save(video);
        } catch (IOException e) {
            log.error("Unable to update video", e);
        }
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
            if (lessonDto.getVideoCallUrl() != null) {
                lessonEntity.setVideoCallUrl(lessonDto.getVideoCallUrl());
            }
        }
    }
    
}
