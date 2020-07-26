package kz.academy.kemelacademy.controllers;

import kz.academy.kemelacademy.services.IRoleService;
import kz.academy.kemelacademy.ui.dto.RoleDto;
import kz.academy.kemelacademy.ui.enums.Locales;
import kz.academy.kemelacademy.ui.model.response.RoleRest;
import kz.academy.kemelacademy.utils.LocaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
@RestController
@RequestMapping("roles")
public class RoleController {
    
    @Autowired
    private IRoleService roleService;
    
    @GetMapping(path = "/{id}")
    public RoleRest getRole(@PathVariable("id") long id) {
        RoleDto roleDto = roleService.getRoleById(id);
        
        return getRoleRest(roleDto);
    }
    
    @GetMapping
    public List<RoleRest> getRoles() {
        List<RoleRest> returnVal = new ArrayList<>();
        
        List<RoleDto> roles = roleService.getRoles();
        for (RoleDto roleDto: roles) {
            RoleRest roleRest = getRoleRest(roleDto);
            
            returnVal.add(roleRest);
        }
        
        return returnVal;
    }
    
    private RoleRest getRoleRest(RoleDto roleDto) {
        RoleRest roleRest = new RoleRest();
        roleRest.setId(roleDto.getId());
        
        String name;
        if (LocaleUtils.checkLocale(Locales.KZ.getLocale())) {
            name = roleDto.getNameKz();
        } else if (LocaleUtils.checkLocale(Locales.RU.getLocale())) {
            name = roleDto.getNameRu();
        } else {
            name = roleDto.getNameEn();
        }
        roleRest.setName(name);
        return roleRest;
    }
    
}
