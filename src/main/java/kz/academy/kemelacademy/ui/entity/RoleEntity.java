package kz.academy.kemelacademy.ui.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-26
 * @project kemelacademy
 */
@Entity(name = "roles")
@Data
public class RoleEntity {
    
    @Id
    @GeneratedValue
    private long id;
    
    @Column(nullable = false, length = 20)
    private String nameKz;
    
    @Column(nullable = false, length = 20)
    private String nameRu;
    
    @Column(nullable = false, length = 20)
    private String nameEn;
    
}
