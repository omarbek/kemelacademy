package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.ILessonTypeRepository;
import kz.academy.kemelacademy.services.ILessonTypeService;
import kz.academy.kemelacademy.ui.dto.LessonTypeDto;
import kz.academy.kemelacademy.ui.entity.LessonTypeEntity;
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

@Service
public class LessonTypeServiceImpl implements ILessonTypeService {

    @Autowired
    ILessonTypeRepository lessonTypeRepository;

    @Override
    public LessonTypeDto createLessonType(LessonTypeDto lessonTypeDto) throws Exception {

        LessonTypeEntity lessonTypeEntity = new LessonTypeEntity();
        BeanUtils.copyProperties(lessonTypeDto, lessonTypeEntity);

        LessonTypeEntity storedLessonType = lessonTypeRepository.save(lessonTypeEntity);

        LessonTypeDto returnVal = new LessonTypeDto();
        BeanUtils.copyProperties(storedLessonType, returnVal);

        return returnVal;
    }

    @Override
    public LessonTypeDto getLessonTypeById(long id) throws Exception {

        Optional<LessonTypeEntity> optional = lessonTypeRepository.findById(id);
        if(!optional.isPresent()){
            throw new ServiceException((ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        }
        LessonTypeEntity lessonTypeEntity = optional.get();
        LessonTypeDto returnVal = new LessonTypeDto();
        BeanUtils.copyProperties(lessonTypeEntity, returnVal);

        return returnVal;

    }

    @Override
    public List<LessonTypeDto> getLessonTypeDtos(int page, int limit) throws Exception {
        List<LessonTypeDto> returnValue = new ArrayList<>();

        if (page > 0) {
            page -= 1;
        }

        Pageable pageable = PageRequest.of(page, limit);
        Page<LessonTypeEntity> lessonTypePage = lessonTypeRepository.findAll(pageable);
        List<LessonTypeEntity> lessonTypes = lessonTypePage.getContent();

        for (LessonTypeEntity lessonTypeEntity: lessonTypes) {
            LessonTypeDto lessonTypeDto = new LessonTypeDto();
            BeanUtils.copyProperties(lessonTypeEntity, lessonTypeDto);
            returnValue.add(lessonTypeDto);
        }

        return returnValue;
    }

}
