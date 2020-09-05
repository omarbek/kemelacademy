package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-05
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "system_parameters")
@EqualsAndHashCode()
public class SystemParameterEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String code;
    
    @Column(nullable = false)
    private String value;
    
    @Override
    public String toString() {
        return value;
    }
    
}
