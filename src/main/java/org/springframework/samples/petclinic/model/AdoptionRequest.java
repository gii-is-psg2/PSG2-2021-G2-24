package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity

@Table(name = "adoptionrequests")
public class AdoptionRequest extends BaseEntity {

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Set<AdoptionRequestResponse> getAdoptionRequestResponse() {
		return adoptionRequestResponse;
	}

	public void setAdoptionRequestResponse(Set<AdoptionRequestResponse> adoptionRequestResponse) {
		this.adoptionRequestResponse = adoptionRequestResponse;
	}

	@ManyToOne
	private Owner owner;

	@ManyToOne
	private Pet pet;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "adoptionrequest")
	private Set<AdoptionRequestResponse> adoptionRequestResponse;

}
