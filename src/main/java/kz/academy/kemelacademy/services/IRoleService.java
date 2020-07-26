package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.RoleDto;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
public interface IRoleService {
    
    RoleDto getRoleById(long id);
    
    List<RoleDto> getRoles();
    
}
