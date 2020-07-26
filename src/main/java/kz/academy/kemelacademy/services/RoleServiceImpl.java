package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.repositories.IRoleRepository;
import kz.academy.kemelacademy.ui.dto.RoleDto;
import kz.academy.kemelacademy.ui.entity.RoleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public RoleDto getRoleById(long id) {
        RoleEntity roleEntity = roleRepository.findById(id).get();
        
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
