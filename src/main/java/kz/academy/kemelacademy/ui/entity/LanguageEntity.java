package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "languages")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LanguageEntity extends AbstractNameEntity {
}
