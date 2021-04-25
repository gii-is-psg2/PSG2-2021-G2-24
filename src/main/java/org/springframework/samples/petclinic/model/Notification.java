package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "notifications")

public class Notification extends BaseEntity{

	@Column(name = "message")
	private String message;
	
	@OneToOne
	@JoinColumn(name = "response")
	private AdoptionRequestResponse response;
}
