package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "adoptionrequests")
public class AdoptionRequest extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "owner_id")
	private Owner ownerrequest;
	
	@OneToOne
	@JoinColumn(name = "pet_id")
	private Pet petrequest;
}
