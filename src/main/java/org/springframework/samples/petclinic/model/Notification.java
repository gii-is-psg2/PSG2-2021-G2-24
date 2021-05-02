package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "notifications")

public class Notification extends BaseEntity {

	private String message;

	@OneToOne
	private AdoptionRequestResponse response;
}
