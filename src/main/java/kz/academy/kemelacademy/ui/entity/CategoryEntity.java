package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "categories")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CategoryEntity extends AbstractNameEntity {
}
