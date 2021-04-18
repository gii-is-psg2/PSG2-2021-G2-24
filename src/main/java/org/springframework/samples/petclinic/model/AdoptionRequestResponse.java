package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "adoptionrequestresponses")
public class AdoptionRequestResponse extends BaseEntity{

	@ManyToOne
	@JoinColumn(name = "adoptionrequest_id")
	private AdoptionRequest adoptionrequest;
	
	@OneToOne
	@JoinColumn(name = "owner_id")
	private Owner ownerresponse;
}
