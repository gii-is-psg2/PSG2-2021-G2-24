package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity

@Table(name = "notifications")

public class Notification extends BaseEntity {

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AdoptionRequestResponse getResponse() {
		return response;
	}

	public void setResponse(AdoptionRequestResponse response) {
		this.response = response;
	}

	private String message;

	@OneToOne
	private AdoptionRequestResponse response;
}
