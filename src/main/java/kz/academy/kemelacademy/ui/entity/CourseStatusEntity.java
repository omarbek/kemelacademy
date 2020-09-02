package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Omarbek.Dinassil
 * on 2020-09-03
 * @project kemelacademy
 */
@Data
@Entity
@Table(name = "course_statuses")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CourseStatusEntity extends AbstractNameEntity {
}
