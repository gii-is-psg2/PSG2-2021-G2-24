package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;



@Table(name = "rooms")
@Entity
public class Room extends BaseEntity {

}
