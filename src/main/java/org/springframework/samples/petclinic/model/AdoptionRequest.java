package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private Owner owner;
	
	@ManyToOne
	private Pet pet;
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "adoptionrequest")
	private Set<AdoptionRequestResponse> adoptionRequestResponse;
	
}
