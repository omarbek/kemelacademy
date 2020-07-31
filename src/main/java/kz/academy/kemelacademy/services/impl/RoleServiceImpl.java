package kz.academy.kemelacademy.services.impl;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.repositories.IRoleRepository;
import kz.academy.kemelacademy.services.IRoleService;
import kz.academy.kemelacademy.ui.dto.RoleDto;
import kz.academy.kemelacademy.ui.entity.RoleEntity;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
@Service
public class RoleServiceImpl implements IRoleService {
    
    @Autowired
    private IRoleRepository roleRepository;
    
    @Override
    public RoleDto getRoleById(long id) throws Exception {
        Optional<RoleEntity> optional = roleRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        RoleEntity roleEntity = optional.get();
        
        RoleDto returnVal = new RoleDto();
        BeanUtils.copyProperties(roleEntity, returnVal);
        
        return returnVal;
    }
    
    @Override
    public List<RoleDto> getRoles() {
        List<RoleDto> returnValue = new ArrayList<>();
        
        List<RoleEntity> roles = roleRepository.findAll();
        
        for (RoleEntity roleEntity: roles) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(roleEntity, roleDto);
            returnValue.add(roleDto);
        }
        
        return returnValue;
    }
    
}
