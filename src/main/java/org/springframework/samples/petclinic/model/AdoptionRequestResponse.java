package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity

@Table(name = "adoptionrequestresponses")
public class AdoptionRequestResponse extends BaseEntity {

	public AdoptionRequest getAdoptionrequest() {
		return adoptionrequest;
	}

	public void setAdoptionrequest(AdoptionRequest adoptionrequest) {
		this.adoptionrequest = adoptionrequest;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

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
