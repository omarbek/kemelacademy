package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "file_type")
@EqualsAndHashCode(callSuper = true)
public class FileTypeEntity extends AbstractNameEntity {
}
