package kz.academy.kemelacademy.services;

import kz.academy.kemelacademy.ui.dto.LevelDto;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-04
 * @project kemelacademy
 */
public interface ILevelService {
    
    List<LevelDto> getAll() throws Exception;
    
}
