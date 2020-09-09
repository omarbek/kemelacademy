package kz.academy.kemelacademy.ui.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-05
 * @project kemelacademy
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "system_parameters")
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        SystemParameterEntity that = (SystemParameterEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(value, that.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, code, value);
    }
    
}
