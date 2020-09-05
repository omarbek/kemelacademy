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
@Table(name = "feature_flag")
@EqualsAndHashCode()
public class FeatureFlagEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private String code;
    
    @Column(name = "switch_on", nullable = false)
    private Boolean switchOn;
    
    @Override
    public String toString() {
        return code + ": " + switchOn;
    }
    
}
