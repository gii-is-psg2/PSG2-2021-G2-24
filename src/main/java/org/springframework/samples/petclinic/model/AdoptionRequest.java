package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "adoptionrequests")
public class AdoptionRequest extends BaseEntity {

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@OneToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@OneToMany
	@JoinColumn(name = "setadoptionrequestresponse_id")
	private Set<AdoptionRequestResponse> adoptionRequestResponse;
	
	
}
