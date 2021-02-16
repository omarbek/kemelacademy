package kz.academy.kemelacademy.ui.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "test_statuses")
@EqualsAndHashCode(callSuper = true)
public class TestStatusEntity extends AbstractNameEntity {
}
