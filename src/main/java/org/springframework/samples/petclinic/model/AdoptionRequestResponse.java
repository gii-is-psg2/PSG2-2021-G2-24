package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "adoptionrequestresponses")
public class AdoptionRequestResponse extends BaseEntity {

	@ManyToOne
	private AdoptionRequest adoptionrequest;

	@ManyToOne
	private Owner owner;

	@NotNull
	private String description;

	@Column(name = "isactive")
	private boolean isactive;

	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "response")
	private Notification notification;
}
