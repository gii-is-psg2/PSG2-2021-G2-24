package org.springframework.samples.petclinic.model;



import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;



import lombok.Data;



@Entity
@Data
@Table(name = "causas")

public class Causa extends NamedEntity {
	
	
	@NotNull
	@Column(name = "descriptionCausa_id")
	private String descriptionCausa;

	@NotNull
	@Column(name = "budgetTarget_id")
	private Double budgetTarget;
	
	@NotNull
	@Column(name = "ActivenpOrganization_id")
	private String ActivenpOrganization;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "causa")
	private Set<Donation> donations;
	

	public Double totalDonation;


	public String getDescriptionCausa() {
		return descriptionCausa;
	}


	public void setDescriptionCausa(String descriptionCausa) {
		this.descriptionCausa = descriptionCausa;
	}


	public Double getBudgetTarget() {
		return budgetTarget;
	}


	public void setBudgetTarget(Double budgetTarget) {
		this.budgetTarget = budgetTarget;
	}


	public String getActivenpOrganization() {
		return ActivenpOrganization;
	}


	public void setActivenpOrganization(String activenpOrganization) {
		ActivenpOrganization = activenpOrganization;
	}


	public Owner getOwner() {
		return owner;
	}


	public void setOwner(Owner owner) {
		this.owner = owner;
	}


	public Set<Donation> getDonations() {
		return donations;
	}


	public void setDonations(Set<Donation> donations) {
		this.donations = donations;
	}


	public Double getTotalDonation() {
		return totalDonation;
	}


	public void setTotalDonation(Double totalDonation) {
		this.totalDonation = totalDonation;
	}
	
	


	}
	


